import java.sql.*;
import java.util.ArrayList;
import SiyuanPeng.*;

class ClassOfAll{
public String incase = "";
public int Max = 0;
public int Min = 99999;
public int Count = 0;
public int Sum = 0;
public int Sum_of_AVG = 0;
public int Count_of_AVG = 0;
public int AVG = 0;
public ClassOfAll(){
incase = "";
Max = 0;
Min = 99999;
Count = 0;
Sum = 0;
Sum_of_AVG = 0;
Count_of_AVG = 0;
AVG = 0;
}
public double getAvg(){
	double sum = this.Sum;
 sum = sum/((double)Count);
	return sum;
}
}
class mfTableBean{
public ClassOfAll _2_avg_quant = new ClassOfAll();
public String prod;
public int year;
public ClassOfAll _1_max_quant = new ClassOfAll();
public String cust;
public ClassOfAll _1_sum_quant = new ClassOfAll();
public ClassOfAll _0_avg_quant = new ClassOfAll();
public ClassOfAll _2_sum_quant = new ClassOfAll();
public ClassOfAll _3_sum_quant = new ClassOfAll();
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
			rs=st.executeQuery("select * from sales");
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
  					al.get(Pos)._0_avg_quant = update(al.get(Pos)._0_avg_quant, + rs.getInt("quant"));
				continue;
				}
				else{
					mfTableBean temp = new mfTableBean();
					temp.cust = ga0;
					temp.prod = ga1;
					temp.year = ga2;
  					temp._0_avg_quant = update(temp._0_avg_quant, + rs.getInt("quant"));
					al.add(temp);
				}
			}
			rs=st.executeQuery("select * from sales");
			while(rs.next()){
				String ga0 = rs.getString("cust");
				String ga1 = rs.getString("prod");
				int ga2 = rs.getInt("year");
				for(int i = 0; i < al.size(); i++){
					if(ga0.equals(al.get(i).cust)){
					if(ga1.equals(al.get(i).prod)){
					if(ga2 == al.get(i).year){
					if(rs.getInt("quant")>al.get(i)._0_avg_quant.getAvg()){
						al.get(i)._1_max_quant = update(al.get(i)._1_max_quant, rs.getInt("quant"));
					}
					}
					}
					}
				}
			}
			rs=st.executeQuery("select * from sales");
			while(rs.next()){
				String ga0 = rs.getString("cust");
				String ga1 = rs.getString("prod");
				int ga2 = rs.getInt("year");
				for(int i = 0; i < al.size(); i++){
					if(ga0.equals(al.get(i).cust)){
					if(ga1.equals(al.get(i).prod)){
					if(ga2 == al.get(i).year){
					if(rs.getInt("day")> 1){
						al.get(i)._2_avg_quant = update(al.get(i)._2_avg_quant, rs.getInt("quant"));
					}
					}
					}
					}
				}
			}
			rs=st.executeQuery("select * from sales");
			while(rs.next()){
				String ga0 = rs.getString("cust");
				String ga1 = rs.getString("prod");
				int ga2 = rs.getInt("year");
				for(int i = 0; i < al.size(); i++){
					if(ga0.equals(al.get(i).cust)){
					if(ga1.equals(al.get(i).prod)){
					if(ga2 == al.get(i).year){
					if(rs.getString("state").equals("CT")){
						al.get(i)._3_sum_quant = update(al.get(i)._3_sum_quant, rs.getInt("quant"));
					}
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
	System.out.println("\t\tcust\t\tprod\t\tyear\t\t1_max_quant\t\t2_avg_quant\t\t3_sum_quant");
			for(mfTableBean mfb : al){
				System.out.println(" "  + "\t\t" + mfb.cust + "\t\t" + mfb.prod + "\t\t" + mfb.year +"\t\t" + mfb._1_max_quant.Max +"\t\t" + mfb._2_avg_quant.getAvg() +"\t\t" + mfb._3_sum_quant.Sum);}
}
}
