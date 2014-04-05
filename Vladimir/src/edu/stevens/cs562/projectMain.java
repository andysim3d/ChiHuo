/**projectMain.java
 * 
 * author: Pengfei Zhang
 * 
 * Function: test
 */

package edu.stevens.cs562;


import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.List;

import SiyuanPeng.*;

public class projectMain {

	static FileWriter fw=null;
	public static void main(String[] args) {
		try {
			fw=new FileWriter("./src/programGenerated.java");
		} catch (IOException e) {
			e.printStackTrace();
		}

		Util util=new Util();
		util.mfStructureGenerator();
		Parameters para=new Parameters();
		para.parse();
		projectMain pm=new projectMain();

		pm.generateImport();
		pm.generateBean(util.list, para);
		pm.generateMain(util.list, para);

		if(fw!=null){
			try {
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 *Print the class imported
	 */
	public void generateImport(){
		p("import java.sql.*;"+"\n"+"import java.util.ArrayList;"+"\n"+"import SiyuanPeng.*;"+"\n");
	}

	/**
	 *Create a JavaBean class for the program to be generated. 
	 */
	public void generateBean(List<InfoSchemaBean> list, Parameters para){
		p("class mfTableBean{");
		for (int i = 0; i < para.getS().size(); i++) {	//number of projected attributes
			int n;
			if(para.getS().get(i).startsWith("max")||para.getS().get(i).startsWith("sum")||para.getS().get(i).startsWith("count")){
				p("\t"+"public int "+para.getS().get(i)+";");
				//			}else if(para.getS().get(i).startsWith("avg")){
				//				p("\t"+"public int "+para.getS().get(i).replace("avg", "sum")+";");
				//				p("\t"+"public int "+para.getS().get(i).replace("avg", "count")+";");
				//			}else if(para.getS().get(i).startsWith("count")){
			}else{
				for (int j = 0; j < list.size(); j++) {		//iterate information_schema bean list to match the parameter's type
					if(para.getS().get(i).endsWith(list.get(j).getColumn_name())){
						n=j;
						if(list.get(n).getData_type().startsWith("character"))
							p("\t"+"public String "+para.getS().get(i)+";");
						else if(list.get(n).getData_type().equals("integer"))
							p("\t"+"public int "+para.getS().get(i)+";");
						break;
					}
				}
			}
		}
		p("}");
	}

	public void generateMain(List<InfoSchemaBean> list, Parameters para){
		p("public class programGenerated {");
		p("	Connection conn=null;");
		p("	ArrayList<mfTableBean> al=null;");

		p("	public static void main(String[] args) {");
		p("		programGenerated main=new programGenerated();");
		p("		main.mfTableGenerator();");
		p("		main.print();");
		p("	}");

		p("	public void print(){");
		p("		for (int i = 0; i < al.size(); i++) {");
		p("			System.out.print(al.get(i)."+para.getS().get(0)
				+"+\".....\"+al.get(i)."+para.getS().get(1)
				+"+\".....\"+"+"al.get(i)."+para.getS().get(2)+"/"+"al.get(i)."+para.getS().get(3)
				+"+\".....\"+al.get(i)."+para.getS().get(4)+");");
		p("			System.out.println();");
		p("		}");
		p("	}");

		p("	public void mfTableGenerator(){");
		p("		al=new ArrayList<mfTableBean>();");
		p("		conn=DBUtil.getInstance().getConnection();");
		p("		Statement st=null;");
		p("		ResultSet rs=null;");
		p("		try {");
		p("			st=conn.createStatement();");
		p("			rs=st.executeQuery(\"select * from sales;\");");
		p("			while(rs.next()){");
		p("				String temp1=rs.getString(\""+para.getS().get(0)+"\");");
		p("				String temp2=rs.getString(\""+para.getS().get(1)+"\");");
		p("				boolean existed=false;");
		p("				for (int i = 0; i < al.size(); i++) {");
		p("					if(temp1.equals(al.get(i)."+para.getS().get(0)+")&&temp2.equals(al.get(i)."+para.getS().get(1)+")){");
		p("						al.get(i).sum_0_quant+=rs.getInt(\"quant\");");
		p("						al.get(i).count_0_quant++;");
		p("						if(al.get(i).max_0_quant<rs.getInt(\"quant\")){");
		p("							al.get(i).max_0_quant=rs.getInt(\"quant\");");
		p("						}");
		p("						existed=true;");
		p("						break;");
		p("					}");
		p("				}");
		p("				if(!existed){");
		p("					mfTableBean tempbean=new mfTableBean();");
		p("					tempbean.prod=rs.getString(\"prod\");");//doesn't finish dynamic here
		p("					tempbean.cust=rs.getString(\"cust\");");
		p("					tempbean.sum_0_quant=rs.getInt(\"quant\");");
		p("					tempbean.count_0_quant++;");
		p("					tempbean.max_0_quant=rs.getInt(\"quant\");");
		p("					al.add(tempbean);");
		p("				}");
		p("			}");

		p("		} catch (SQLException e) {");
		p("			e.printStackTrace();");
		p("		}");

		p("		try {");
		p("			conn.close();");
		p("		} catch (SQLException e) {");
		p("		}");
		p("	}");
		p("}");
	}

	/**
	 *print method. print the strings to the file and the console. 
	 */
	public static void p(String s){
		try {
			fw.append(s);
			fw.append("\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(s);
	}


}
