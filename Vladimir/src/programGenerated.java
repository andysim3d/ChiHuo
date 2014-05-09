import java.sql.*;
import java.util.ArrayList;
import SiyuanPeng.*;
import Pengfei.Zhang.*;
import Siyuan.Zheng.*;
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
public int getAvg(){
	int sum = this.Sum;
if(Count == 0){
return 0;
}
 sum = sum/Count;
	return sum;
}
}
class mfTableBean{
public String prod;
public ClassOfAll _0_quant = new ClassOfAll();
public ClassOfAll _2_ALL = new ClassOfAll();
public ClassOfAll _1_ALL = new ClassOfAll();
public String cust;
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
				for(int i = 0; i < al.size(); i++){
					if(ga0.equals(al.get(i).cust)){
					if(ga1.equals(al.get(i).prod)){
						Pos = i;
						exist =true;
					}
					}
				}
				if(exist){
  					al.get(Pos)._0_quant = update(al.get(Pos)._0_quant, + rs.getInt("quant"));
				continue;
				}
				else{
					mfTableBean temp = new mfTableBean();
					temp.cust = ga0;
					temp.prod = ga1;
  					temp._0_quant = update(temp._0_quant, + rs.getInt("quant"));
					al.add(temp);
				}
			}
			rs=st.executeQuery("select * from sales");
			while(rs.next()){
				for(int i = 0; i < al.size(); i++){
					if(rs.getString("cust").equals(al.get(i).cust)){
					if(rs.getString("prod").equals(al.get(i).prod)){
					if(rs.getInt("quant")<al.get(i)._0_quant.getAvg()){
						al.get(i)._1_ALL = update(al.get(i)._1_ALL, 1 );
			}
			}
			}
			}
			}
			rs=st.executeQuery("select * from sales");
			while(rs.next()){
				for(int i = 0; i < al.size(); i++){
					if(rs.getString("cust").equals(al.get(i).cust)){
					if(rs.getString("prod").equals(al.get(i).prod)){
					if(rs.getInt("quant")>al.get(i)._0_quant.getAvg()){
						al.get(i)._2_ALL = update(al.get(i)._2_ALL, 1 );
			}
			}
			}
			}
			}
		} catch ( Exception e ){
			e.printStackTrace();}
finally{
		try{
			rs.close();
			conn.close();
			st.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
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
	System.out.println(outPutFormat.outPutFormats("cust", 8) +" " +outPutFormat.outPutFormats("prod", 8) +" " +outPutFormat.outPutFormats("1_count_ALL", 13) +" " +outPutFormat.outPutFormats("2_count_ALL", 13) +" " + " " );
			for(mfTableBean mfb : al){
				System.out.println(""  + outPutFormat.outPutFormats(mfb.cust,8)+" " + outPutFormat.outPutFormats(mfb.prod,8)+" " + outPutFormat.outPutFormats( mfb._1_ALL.Count,13)+" " + outPutFormat.outPutFormats( mfb._2_ALL.Count,13)+" ");}
}
}
