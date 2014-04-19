package Pengfei.Zhang;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

import SiyuanPeng.InfoSchemaBean;
import SiyuanPeng.Util;
import edu.stevens.cs562.Parameters;
import edu.stevens.cs562.projectMain;

public class GenerateNewJAVA {

	//file writer
	static FileWriter fw=null;
	
	//main//
	public static void main(String[] args) {
		try {
			//init the file writer
			fw=new FileWriter("./src/programGenerated.java");
		} catch (IOException e) {
			e.printStackTrace();
		}

		//paramters & mf_structures
		Parameters para = new Parameters();
		para = ParaseParameters.ParaseInput();
		MF_Structure mf_s =  new MF_Structure();
		mf_s = ParaseMF.mfParase(para);
		//
		//get information_schema
		Util util=new Util();
		util.UtilGenerator();
		//build a instance of GenerateNewJAVA class
		GenerateNewJAVA pm=new GenerateNewJAVA();

		//generate property import lib;
		pm.generateImport();
		
		//generate data-structure;
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
	public void generateBean(Hashtable<String, String> list, Parameters para){
		p("class mfTableBean{");
		for(sBean k : para.getS()){
			if(k.name.contains("_")){
				p("piblic ClassOfALL " + k.name);
			}
			else{
				p("public "+k.type + " "+ k.name);
			}
		}
		p("}");
		//for()
		//p(para.getS().get(0).name)
	}
	
	public void generateGeneralDS(){
		p("class ClassOfAll{");
		p("public int Max;");
		p("public int Min;");
		p("public int Count;");
		p("public int Sum");
		p("public int Sum_of_AVG");
		p("public int Count_of_AVG");
		p("public int AVG");
		p("}");
	}
	

	public void generateMain(Hashtable<String, String> list, Parameters para){
		//generate connection and arraylist to store data;
		p("public class programGenerated {");
		p("	Connection conn=null;");
		p("	ArrayList<mfTableBean> al=null;");

		//
		p("	public static void main(String[] args) {");
		p("		programGenerated main=new programGenerated();");
		//used to generate the table
		p("		main.mfTableGenerator();");
		//generate out put;
		p("		main.print();");
		p("	}");

		
		//generate table
		p("	public void mfTableGenerator(){");
		p("		al=new ArrayList<mfTableBean>();");
		//connect database;
		p("		conn=DBUtil.getInstance().getConnection();");
		p("		Statement st=null;");
		p("		ResultSet rs=null;");
		p("		try {");
		p("			st=conn.createStatement();");
		p("			rs=st.executeQuery(\"select * from sales;\");");
		p("			while(rs.next()){");

		//all the statements until here are stable.	

		for (int i = 0; i < para.getV().size(); i++) {	//in while loop, define the group by variables 
			p("				String temp"+i+"=rs.getString(\""+para.getV().get(i)+"\");");
		}
		p("				boolean existed=false;");
		p("				for (int i = 0; i < al.size(); i++) {");
		for (int i = 0; i < para.getV().size(); i++) {
			p("					if(temp"+i+".equals(al.get(i)."+para.getV().get(i)+")){");
		}

		//if clause (update)

		for (int i = 0; i < para.getF().size(); i++) {
			if(para.getF().get(i).startsWith("avg")){
				for (int h = 0; h < para.getS().size(); h++) {	//number of projected attributes
					int n=0;
					if(para.getS().get(h).name.contains("max")){
						n=h;
						String[] splitF=para.getS().get(h).name.split("_");
						p("						al.get(i).sum_0_"+splitF[2]+"+=rs.getInt(\""+splitF[2]+"\");");
						p("						al.get(i).count_0_"+splitF[2]+"++;");
					}
				}
			}else if(para.getF().get(i).startsWith("max")){
				for (int h = 0; h < para.getS().size(); h++) {	//number of projected attributes
					int n=0;
					if(para.getS().get(h).startsWith("max")){
						n=h;
						String[] splitF=para.getS().get(h).split("_");
						p("						if(al.get(i).max_0_"+splitF[2]+"<rs.getInt(\""+splitF[2]+"\")){");
						p("							al.get(i).max_0_"+splitF[2]+"=rs.getInt(\""+splitF[2]+"\");");
						p("						}");
					}
				}
			}else if(para.getF().get(i).startsWith("min")){
				for (int h = 0; h < para.getS().size(); h++) {	//number of projected attributes
					int n=0;
					if(para.getS().get(h).startsWith("min")){
						n=h;
						String[] splitF=para.getS().get(h).split("_");
						p("						if(al.get(i).min_0_"+splitF[2]+">rs.getInt(\""+splitF[2]+"\")){");
						p("							al.get(i).min_0_"+splitF[2]+"=rs.getInt(\""+splitF[2]+"\");");
						p("						}");
					}
				}
			}
		}
		p("						existed=true;");
		p("						break;");


		//if clause end here

		for (int i = 0; i < para.getV().size(); i++) {
			p("					}");
		}
		p("				}");

		//insert

		p("				if(!existed){");
		p("					mfTableBean tempbean=new mfTableBean();");
		for (int i = 0; i < para.getS().size(); i++) {	//number of projected attributes
			if(para.getS().get(i).startsWith("count")){
				p("					tempbean."+para.getS().get(i)+"++;");//if it starts with "count", count++
			}else{
				int n=0;
				for (int j = 0; j < list.size(); j++) {		//iterate information_schema bean list to match the parameter's type
					if(para.getS().get(i).endsWith(list.get(j).getColumn_name())){
						n=j;
						if(list.get(n).getData_type().startsWith("character"))
							p("					tempbean."+para.getS().get(i)+"=rs.getString(\""+list.get(n).getColumn_name()+"\");");
						else if(list.get(n).getData_type().equals("integer"))
							p("					tempbean."+para.getS().get(i)+"=rs.getInt(\""+list.get(n).getColumn_name()+"\");");
						break;
					}
				}
			}
		}

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



		p("	public void print(){");
		for (int i = 0; i < para.getS().size(); i++) {
			p("			System.out.print(\""+para.getS().get(i)+".....\");");
		}
		p("			System.out.println();");
		p("		for (int i = 0; i < al.size(); i++) {");
		for (int i = 0; i < para.getS().size(); i++) {
			p("			System.out.print(al.get(i)."+para.getS().get(i)+"+\".....\");");
		}
		p("			System.out.println();");
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
