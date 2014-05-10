import java.sql.*;
import java.util.ArrayList;
import SiyuanPeng.*;
import Pengfei.Zhang.*;
import Siyuan.Zheng.*;

class ClassOfAll {
	public String incase = "";
	public int Max = 0;
	public int Min = 99999;
	public int Count = 0;
	public double Sum = 0;
	public int Sum_of_AVG = 0;
	public int Count_of_AVG = 0;
	public int AVG = 0;

	public ClassOfAll() {
		incase = "";
		Max = 0;
		Min = 99999;
		Count = 0;
		Sum = 0;
		Sum_of_AVG = 0;
		Count_of_AVG = 0;
		AVG = 0;
	}

	public int getAvg() {
		int sum = (int) this.Sum;
		if (Count == 0) {
			return 0;
		}
		sum = sum / Count;
		return sum;
	}
}

class mfTableBean {
	public int month;
	public ClassOfAll _1_quant = new ClassOfAll();
	public ClassOfAll _2_quant = new ClassOfAll();
	public String prod;
}

public class programGenerated {
	Connection conn = null;
	ArrayList<mfTableBean> al = null;

	public static void main(String[] args) {
		programGenerated main = new programGenerated();
		main.mfTableGenerator();
		main.print();
	}

	public void mfTableGenerator() {
		al = new ArrayList<mfTableBean>();
		conn = DBUtil.getInstance().getConnection();
		Statement st = null;
		int Pos = 0;
		ResultSet rs = null;
		try {
			st = conn.createStatement();
			rs = st.executeQuery("select * from sales");
			while (rs.next()) {
				boolean exist = false;
				String ga0 = rs.getString("prod");
				int ga1 = rs.getInt("month");
				for (int i = 0; i < al.size(); i++) {
					if (ga0.equals(al.get(i).prod)) {
						if (ga1 == al.get(i).month) {
							Pos = i;
							exist = true;
						}
					}
				}
				if (exist) {
					continue;
				} else {
					mfTableBean temp = new mfTableBean();
					temp.prod = ga0;
					temp.month = ga1;
					al.add(temp);
				}
			}
			rs = st.executeQuery("select * from sales");
			while (rs.next()) {
				for (int i = 0; i < al.size(); i++) {
					if (rs.getString("prod").equals(al.get(i).prod)) {
						if (rs.getInt("month") == al.get(i).month) {
							al.get(i)._1_quant = update(al.get(i)._1_quant,
									rs.getInt("quant"));
						}
					}
				}
			}
			rs = st.executeQuery("select * from sales");
			while (rs.next()) {
				for (int i = 0; i < al.size(); i++) {
					if (rs.getString("prod").equals(al.get(i).prod)) {
						al.get(i)._2_quant = update(al.get(i)._2_quant,
								rs.getInt("quant"));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				conn.close();
				st.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	
	public ClassOfAll update(ClassOfAll all, int value) {
		if (all.Max < value) {
			all.Max = value;
		}
		if (all.Min > value) {
			all.Min = value;
		}
		all.Sum += value;
		all.Count++;
		return all;
	}

	public void print() {
		System.out.println(outPutFormat.outPutFormats("prod", 8) + " "
				+ outPutFormat.outPutFormats("month", 8) + " "
				+ outPutFormat.outPutFormats("1_sum_quant/2_sum_quant", 25)
				+ " " + " ");
		for (mfTableBean mfb : al) {
			System.out.println(""
					+ outPutFormat.outPutFormats(mfb.prod, 8)
					+ " "
					+ outPutFormat.outPutFormats(mfb.month, 8)
					+ " "
					+ outPutFormat.outPutFormats(mfb._1_quant.Sum
							/ mfb._2_quant.Sum, 25) + " ");
		}
	}
}
