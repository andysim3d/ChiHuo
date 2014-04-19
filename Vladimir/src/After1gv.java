import java.sql.*;
import java.util.ArrayList;
import SiyuanPeng.*;

public class After1gv {
	Connection conn=null;
	ArrayList<mfBean1> al=null;
	public static void main(String[] args) {
		After1gv af=new After1gv();
		af.mfTableGenerator();
		af.print();
	}

	public void print(){
		for (int i = 0; i < al.size(); i++) {
			System.out.print(al.get(i).cust+"....."+al.get(i).prod+"....."+
					al.get(i).sum_1_quant/al.get(i).count_1_quant+"....."+al.get(i).max_1_quant+"....."+
					al.get(i).sum_2_quant/al.get(i).count_2_quant);
			System.out.println();
		}
	}

	public void mfTableGenerator(){
		al=new ArrayList<mfBean1>();
		conn=DBUtil.getInstance().getConnection();
		Statement st=null;
		ResultSet rs=null;
		try {
			st=conn.createStatement();
			rs=st.executeQuery("select * from sales;");
			while(rs.next()){
				String temp1=rs.getString("cust");
				String temp2=rs.getString("prod");
				boolean existed=false;
				for (int i = 0; i < al.size(); i++) {
					if(temp1.equals(al.get(i).cust)&&temp2.equals(al.get(i).prod)){
						existed=true;
						break;
					}
				}
				if(!existed){
					mfBean1 tempbean=new mfBean1();
					tempbean.prod=rs.getString("prod");
					tempbean.cust=rs.getString("cust");
					al.add(tempbean);
				}
			}
			
			
			rs=st.executeQuery("select * from sales;");
			while(rs.next()){
				String temp1=rs.getString("cust");
				String temp2=rs.getString("prod");
				for (int i = 0; i < al.size(); i++) {
					if(temp1.equals(al.get(i).cust)&&temp2.equals(al.get(i).prod)){
						al.get(i).sum_1_quant+=rs.getInt("quant");
						al.get(i).count_1_quant++;
						if(al.get(i).max_1_quant<rs.getInt("quant")){
							al.get(i).max_1_quant=rs.getInt("quant");
						}
						break;
					}
				}
			}
			
			
			rs=st.executeQuery("select * from sales;");
			while(rs.next()){
				String temp1=rs.getString("cust");
				String temp2=rs.getString("prod");
				for (int i = 0; i < al.size(); i++) {
					if(!temp1.equals(al.get(i).cust)&&temp2.equals(al.get(i).prod)){
						al.get(i).sum_2_quant+=rs.getInt("quant");
						al.get(i).count_2_quant++;
					}
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			conn.close();
		} catch (SQLException e) {
		}
	}
}

class mfBean1{
	public String cust;
	public String prod;
	//	public int avg_0_quant;
	public int sum_1_quant=0;
	public int count_1_quant=0;
	public int max_1_quant=0;
	public int sum_2_quant=0;
	public int count_2_quant=0;
}
