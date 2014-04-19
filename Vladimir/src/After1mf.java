import java.sql.*;
import java.util.ArrayList;
import SiyuanPeng.*;

public class After1mf {
	Connection conn=null;
	ArrayList<mfBean1mfHidden> al=null;
	public static void main(String[] args) {
		After1mf af=new After1mf();
		af.mfTableGenerator();
		af.print();
	}

	public void print(){
		for (int i = 0; i < al.size(); i++) {
			System.out.print(al.get(i).cust+"....."+al.get(i).prod+"....."+al.get(i).sum_0_quant/al.get(i).count_0_quant+"....."+
					al.get(i).single_1_quant+"....."+al.get(i).single_1_state);
			System.out.println();
		}
	}

	public void mfTableGenerator(){
		al=new ArrayList<mfBean1mfHidden>();
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
						
						al.get(i).sum_0_quant+=rs.getInt("quant");
						al.get(i).count_0_quant++;
						
						if(al.get(i).max_0_quant<rs.getInt("quant")){
							al.get(i).max_0_quant=rs.getInt("quant");
						}
						existed=true;
						break;
					}
				}
				if(!existed){
					mfBean1mfHidden tempbean=new mfBean1mfHidden();
					tempbean.prod=rs.getString("prod");
					tempbean.cust=rs.getString("cust");
					tempbean.max_0_quant=rs.getInt("quant");
					tempbean.sum_0_quant=rs.getInt("quant");
					tempbean.count_0_quant=1;
					al.add(tempbean);
				}
			}


			rs=st.executeQuery("select * from sales;");
			while(rs.next()){
				String temp1=rs.getString("cust");
				String temp2=rs.getString("prod");
				for (int i = 0; i < al.size(); i++) {
					if(temp1.equals(al.get(i).cust)&&temp2.equals(al.get(i).prod)){
						if(al.get(i).max_0_quant==rs.getInt("quant")){
							al.get(i).single_1_quant=rs.getInt("quant");
							al.get(i).single_1_state=rs.getString("state");
						}
						break;
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


class mfBean1mfHidden{
	public String cust;
	public String prod;
	public int sum_0_quant=0;
	public int count_0_quant=0;
	public int single_1_quant=0;
	public String single_1_state;
	public int max_0_quant;
}