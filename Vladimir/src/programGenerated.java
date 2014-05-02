import java.sql.*;
import java.util.ArrayList;
import SiyuanPeng.*;

public class programGenerated {
	class mfTableBean{
		int avg_2_quant;
		int sumforavg_2_quant;
		int count_3_;
		int countforavg_2_quant;
		int _1_month;
		String _4_state;
		int sumforavg_1_quant;
		int _2_month;
		int _3_month;
		int avg_1_quant;
		int month;
		int countforavg_1_quant;
		String prod;
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
				boolean existed=false;
				for (int i = 0; i < al.size(); i++) {
					if(rs.getString("prod").equals(al.get(i).prod)){
					if(rs.getInt("month")==al.get(i).month){
						existed=true;
						break;
					}
					}
				}
				if(!existed){
					mfTableBean tempbean=new mfTableBean();
					tempbean.prod=rs.getString("prod");
					tempbean.month=rs.getInt("month");
					al.add(tempbean);
				}
			}
			rs=st.executeQuery("select * from sales;");
			while(rs.next()){
				for (int i = 0; i < al.size(); i++) {
					if(rs.getString("prod").equals(al.get(i).prod)){
					if(rs.getInt("month")==al.get(i).month-1){
						al.get(i)._1_month=rs.getInt("month");
						al.get(i).countforavg_1_quant++;
						al.get(i).sumforavg_1_quant+=rs.getInt("quant");
					}
					}
				}
			}
			for (mfTableBean bean : al) {
				if(bean.countforavg_1_quant!=0)
					bean.avg_1_quant=bean.sumforavg_1_quant/bean.countforavg_1_quant;
				else
					bean.avg_1_quant=0;
			}
			rs=st.executeQuery("select * from sales;");
			while(rs.next()){
				for (int i = 0; i < al.size(); i++) {
					if(rs.getString("prod").equals(al.get(i).prod)){
					if(rs.getInt("month")==al.get(i).month+1){
						al.get(i).countforavg_2_quant++;
						al.get(i).sumforavg_2_quant+=rs.getInt("quant");
						al.get(i)._2_month=rs.getInt("month");
					}
					}
				}
			}
			for (mfTableBean bean : al) {
				if(bean.countforavg_2_quant!=0)
					bean.avg_2_quant=bean.sumforavg_2_quant/bean.countforavg_2_quant;
				else
					bean.avg_2_quant=0;
			}
			rs=st.executeQuery("select * from sales;");
			while(rs.next()){
				for (int i = 0; i < al.size(); i++) {
					if(rs.getString("prod").equals(al.get(i).prod)){
					if(rs.getInt("month")==al.get(i).month){
					if(rs.getInt("quant")>al.get(i).avg_1_quant){
					if(rs.getInt("quant")<al.get(i).avg_2_quant){
						al.get(i).count_3_++;
						al.get(i)._3_month=rs.getInt("month");
					}
					}
					}
					}
				}
			}
			rs=st.executeQuery("select * from sales;");
			while(rs.next()){
				for (int i = 0; i < al.size(); i++) {
						al.get(i)._4_state=rs.getString("state");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void print(){
			System.out.print("		prod");
			System.out.print("		month");
			System.out.print("	_1_month");
			System.out.print("	_2_month");
			System.out.print("	_3_month");
			System.out.print("	count_3_");
			System.out.print("	_4_state");
			System.out.println();
		for (int i = 0; i < al.size(); i++) {
			System.out.print("		"+al.get(i).prod);
			System.out.print("		"+al.get(i).month);
			System.out.print("		"+al.get(i)._1_month);
			System.out.print("		"+al.get(i)._2_month);
			System.out.print("		"+al.get(i)._3_month);
			System.out.print("		"+al.get(i).count_3_);
			System.out.print("		"+al.get(i)._4_state);
			System.out.println();
		}
	}
}
