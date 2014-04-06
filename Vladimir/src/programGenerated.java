import java.sql.*;
import java.util.ArrayList;
import SiyuanPeng.*;

class mfTableBean{
	public String cust;
	public String prod;
	public int max_0_quant;
	public int sum_0_quant;
	public int count_0_quant;
	public int min_0_quant;
}
public class programGenerated {
	Connection conn=null;
	ArrayList<mfTableBean> al=null;
	public static void main(String[] args) {
		programGenerated main=new programGenerated();
		main.mfTableGenerator();
		main.print();
	}
	public void mfTableGenerator(){
		al=new ArrayList<mfTableBean>();
		conn=DBUtil.getInstance().getConnection();
		Statement st=null;
		ResultSet rs=null;
		try {
			st=conn.createStatement();
			rs=st.executeQuery("select * from sales;");
			while(rs.next()){
				String temp0=rs.getString("cust");
				String temp1=rs.getString("prod");
				boolean existed=false;
				for (int i = 0; i < al.size(); i++) {
					if(temp0.equals(al.get(i).cust)){
					if(temp1.equals(al.get(i).prod)){
						if(al.get(i).max_0_quant<rs.getInt("quant")){
							al.get(i).max_0_quant=rs.getInt("quant");
						}
						al.get(i).sum_0_quant+=rs.getInt("quant");
						al.get(i).count_0_quant++;
						if(al.get(i).min_0_quant>rs.getInt("quant")){
							al.get(i).min_0_quant=rs.getInt("quant");
						}
						existed=true;
						break;
					}
					}
				}
				if(!existed){
					mfTableBean tempbean=new mfTableBean();
					tempbean.cust=rs.getString("cust");
					tempbean.prod=rs.getString("prod");
					tempbean.max_0_quant=rs.getInt("quant");
					tempbean.sum_0_quant=rs.getInt("quant");
					tempbean.count_0_quant++;
					tempbean.min_0_quant=rs.getInt("quant");
					al.add(tempbean);
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
	public void print(){
			System.out.print("cust.....");
			System.out.print("prod.....");
			System.out.print("max_0_quant.....");
			System.out.print("sum_0_quant.....");
			System.out.print("count_0_quant.....");
			System.out.print("min_0_quant.....");
			System.out.println();
		for (int i = 0; i < al.size(); i++) {
			System.out.print(al.get(i).cust+".....");
			System.out.print(al.get(i).prod+".....");
			System.out.print(al.get(i).max_0_quant+".....");
			System.out.print(al.get(i).sum_0_quant+".....");
			System.out.print(al.get(i).count_0_quant+".....");
			System.out.print(al.get(i).min_0_quant+".....");
			System.out.println();
		}
	}
}
