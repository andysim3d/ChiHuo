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
public String cust;
public int _1_quant$;
public ClassOfAll _0_quant = new ClassOfAll();
public String _name = "Null";
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
				for(int i = 0; i < al.size(); i++){
					if(ga0.equals(al.get(i).cust)){
						Pos = i;
						exist =true;
					}
				}
				if(exist){
  					al.get(Pos)._0_quant = update(al.get(Pos)._0_quant, + rs.getInt("quant"));
				continue;
				}
				else{
					mfTableBean temp = new mfTableBean();
					temp.cust = ga0;
  					temp._0_quant = update(temp._0_quant, + rs.getInt("quant"));
					al.add(temp);
				}
			}
			rs=st.executeQuery("select * from sales");
			while(rs.next()){
				for(int i = 0; i < al.size(); i++){
					if(rs.getInt("quant") == al.get(i)._0_quant.getAvg()){
			if(al.get(i)._0_quant.getAvg() == rs.getInt("quant")){
al.get(i)._1_quant$= rs.getInt("quant");
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
	System.out.println(outPutFormat.outPutFormats("cust", 8) +" " +outPutFormat.outPutFormats("name", 8) +" " +outPutFormat.outPutFormats("0_avg_quant", 13) +" " +outPutFormat.outPutFormats("1_quant$", 10) +" " + " " );
			for(mfTableBean mfb : al){
				System.out.println(""  + outPutFormat.outPutFormats(mfb.cust,8)+" " + outPutFormat.outPutFormats(mfb._name,8)+" " + outPutFormat.outPutFormats( mfb._0_quant.getAvg(),13)+" " + outPutFormat.outPutFormats( mfb._1_quant$,8)+" ");}
}
}
