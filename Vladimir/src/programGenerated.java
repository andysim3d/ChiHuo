import java.sql.*;
import java.util.ArrayList;
import SiyuanPeng.*;

class ClassOfAll{
public String incase = "";
public int Max = 99999;
public int Min = 0;
public int Count = 0;
public int Sum = 0;
public int Sum_of_AVG = 0;
public int Count_of_AVG = 0;
public int AVG = 0;
public ClassOfAll(){
incase = "";
Max = 99999;
Min = 0;
Count = 0;
Sum = 0;
Sum_of_AVG = 0;
Count_of_AVG = 0;
AVG = 0;
}
}
class mfTableBean{
public String cust;
public int year;
public String prod;
public ClassOfAll _0_sum_day = new ClassOfAll();
public ClassOfAll _3_sum_quant = new ClassOfAll();
public ClassOfAll _1_sum_quant = new ClassOfAll();
public ClassOfAll _2_sum_quant = new ClassOfAll();
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
 al = new ArrayList<mfTableBean>(); 
    	conn = DBUtil.getInstance().getConnection();
		Statement st = null;
		int Pos = 0;
		ResultSet rs=null;
		try {
			st=conn.createStatement();
			rs=st.executeQuery("select * from sales;");
			while(rs.next()){
				boolean exist = false;
				String ga0 = rs.getString("cust");
				String ga1 = rs.getString("prod");
				int ga2 = rs.getInt("year");
				for(int i = 0; i < al.size(); i++){
					if(ga0.equals(al.get(i).cust)){
					if(ga1.equals(al.get(i).prod)){
					if(ga2 == al.get(i).year){
						Pos = i;
						exist =true;
					}
					}
					}
				}
				if(exist){
  					al.get(Pos)._0_sum_day = update(al.get(Pos)._0_sum_day, + rs.getInt("day"));
				continue;
				}
				else{
					mfTableBean temp = new mfTableBean();
					temp.cust = ga0;
					temp.prod = ga1;
					temp.year = ga2;
  					temp._0_sum_day = update(temp._0_sum_day, + rs.getInt("day"));
					al.add(temp);
				}
			}
			rs=st.executeQuery("select * from sales;");
			while(rs.next()){
				String ga0 = rs.getString("cust");
				String ga1 = rs.getString("prod");
				int ga2 = rs.getInt("year");
				for(int i = 0; i < al.size(); i++){
					if(ga0 == al.get(i).cust){
					if(ga1 == al.get(i).prod){
					if(ga2 == al.get(i).year){
						Pos = i;
					}
					}
					}
				}
			}
			rs=st.executeQuery("select * from sales;");
			while(rs.next()){
				String ga0 = rs.getString("cust");
				String ga1 = rs.getString("prod");
				int ga2 = rs.getInt("year");
				for(int i = 0; i < al.size(); i++){
					if(ga0 == al.get(i).cust){
					if(ga1 == al.get(i).prod){
					if(ga2 == al.get(i).year){
						Pos = i;
					}
					}
					}
				}
			}
			rs=st.executeQuery("select * from sales;");
			while(rs.next()){
				String ga0 = rs.getString("cust");
				String ga1 = rs.getString("prod");
				int ga2 = rs.getInt("year");
				for(int i = 0; i < al.size(); i++){
					if(ga0 == al.get(i).cust){
					if(ga1 == al.get(i).prod){
					if(ga2 == al.get(i).year){
						Pos = i;
					}
					}
					}
				}
			}
		} catch ( Exception e ){
			e.printStackTrace();}
}
	public ClassOfAll update(ClassOfAll all, int value){
		if(all.Max < value){
			all.Max = value;
		}
		if(all.Min > value){
			all.Min = value;
		}
		all.Sum += value;
		all.Count++;
		return all;
	}
public void print(){
	System.out.print("cust\t\tprod\t\tyear\t\t1_sum_quant\t\t2_sum_quant\t\t3_sum_quant");}
}
