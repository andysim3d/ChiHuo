import java.sql.*;
import java.util.ArrayList;
import SiyuanPeng.*;

public class programGenerated {
	class mfTableBean{
		int countforsum_3_quant;
		int sum_2_quant;
		int max_0_quant;
		int count_3_;
		int avg_3_quant;
		int sumforavg_3_quant;
		int sum_3_quant;
		int sum_1_quant;
		String cust;
	}
	ArrayList<mfTableBean> al=new ArrayList<>();
	public static void main(String[] args) {
		programGenerated main=new programGenerated();
		main.mfTableGenerator();
		main.print();
	}
	public void mfTableGenerator(){
		ResultSet rs=null;
		try(Connection conn=DBUtil.getInstance().getConnection();
			 Statement st=conn.createStatement();) {
			rs=st.executeQuery("select * from sales;");
			while(rs.next()){
				String temp0=rs.getString("cust");
				boolean existed=false;
				for (int i = 0; i < al.size(); i++) {
					if(temp0.equals(al.get(i).cust)){
						existed=true;
						break;
					}
				}
				if(!existed){
					mfTableBean tempbean=new mfTableBean();
					tempbean.cust=rs.getString("cust");
					tempbean.max_0_quant=rs.getInt("quant");
					al.add(tempbean);
				}
			}
			rs=st.executeQuery("select * from sales;");
			while(rs.next()){
				String temp0=rs.getString("cust");
				for (int i = 0; i < al.size(); i++) {
					if(temp0.equals(al.get(i).cust)){
					if("NY".equals(rs.getString("state"))){
						al.get(i).sum_1_quant+=rs.getInt("quant");
						break;
					}
					}
				}
			}
			rs=st.executeQuery("select * from sales;");
			while(rs.next()){
				String temp0=rs.getString("cust");
				for (int i = 0; i < al.size(); i++) {
					if(temp0.equals(al.get(i).cust)){
					if("NJ".equals(rs.getString("state"))){
						al.get(i).sum_2_quant+=rs.getInt("quant");
						break;
					}
					}
				}
			}
			rs=st.executeQuery("select * from sales;");
			while(rs.next()){
				String temp0=rs.getString("cust");
				for (int i = 0; i < al.size(); i++) {
					if(temp0.equals(al.get(i).cust)){
					if("NJ".equals(rs.getString("state"))){
						al.get(i).count_3_++;
						al.get(i).countforsum_3_quant++;
						al.get(i).sumforavg_3_quant+=rs.getInt("quant");
						al.get(i).sum_3_quant+=rs.getInt("quant");
						break;
					}
					}
				}
			}
			for (mfTableBean bean : al) {
				if(bean.countforsum_3_quant!=0)
					bean.avg_3_quant=bean.sumforavg_3_quant/bean.countforsum_3_quant;
				else
					bean.avg_3_quant=0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void print(){
			System.out.print("cust.....");
			System.out.print("max_0_quant.....");
			System.out.print("sum_1_quant.....");
			System.out.print("sum_2_quant.....");
			System.out.print("count_3_.....");
			System.out.print("avg_3_quant.....");
			System.out.println();
		for (int i = 0; i < al.size(); i++) {
			System.out.print(al.get(i).cust+".....");
			System.out.print(al.get(i).max_0_quant+".....");
			System.out.print(al.get(i).sum_1_quant+".....");
			System.out.print(al.get(i).sum_2_quant+".....");
			System.out.print(al.get(i).count_3_+".....");
			System.out.print(al.get(i).avg_3_quant+".....");
			System.out.println();
		}
	}
}
