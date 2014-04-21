package Pengfei.Zhang;


import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import SiyuanPeng.Util;
import SiyuanPeng_program.Parameters;

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
		pm.generateGeneralDS();
		//generate data-structure;
		pm.generateBean(util.list, para);
		pm.generateMain(util.list, para);
		pm.generateMfTable(util.list, para , mf_s);
		pm.generateUpdate();
		pm.generatePrint(para);
		p("}");
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
	public void generateBean(HashMap<String, String> list, Parameters para){
		p("class mfTableBean{");
		for(sBean k : para.getBeanset()){
			if(k.name.contains("_")){
				p("public ClassOfAll _" + k.name + " = new ClassOfAll();");
			}
			else{
				if(k.type.contains("char")){
					p("public String "+ k.name+ ";");
				}
				else
				{
					p("public int "+ k.name+ ";");
				}
			}
		}
		p("}");
	}
	
	public void generateGeneralDS(){
		p("class ClassOfAll{");
		p("public String incase = \"\";");
		p("public int Max = 0;");
		p("public int Min = 99999;");
		p("public int Count = 0;");
		p("public int Sum = 0;");
		p("public int Sum_of_AVG = 0;");
		p("public int Count_of_AVG = 0;");
		p("public int AVG = 0;");
		p("public ClassOfAll(){");
		p("incase = \"\";");
		p("Max = 0;");
		p("Min = 99999;");
		p("Count = 0;");
		p("Sum = 0;");
		p("Sum_of_AVG = 0;");
		p("Count_of_AVG = 0;");
		p("AVG = 0;");
		p("}");
		p("}");
	}
	

	public void generateMain(HashMap<String, String> list, Parameters para){
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
	}

	
	public void generateMfTable(HashMap<String, String> list, Parameters para , MF_Structure mf){
		p("public void mfTableGenerator(){");
		p(" al = new ArrayList<mfTableBean>(); ");
		//connect database
		p("    	conn = DBUtil.getInstance().getConnection();");
		p("		Statement st = null;");
		p("		int Pos = 0;");
		p("		ResultSet rs=null;");
		p("		try {");
		p("			st=conn.createStatement();");
		p("			rs=st.executeQuery(\"select * from sales\");");
		//loop 0, generate table and title;
		p("			while(rs.next()){");
		p("				boolean exist = false;");	
		for (int i = 0; i < para.getV().size(); i++) {
			String type = list.get(para.getV().get(i));
			if (type.contains("chara")) {
				p("				String ga"+String.valueOf(i)+ " = rs.getString(\""+para.getV().get(i)+"\");");	
			}
			else{
				p("				int ga"+String.valueOf(i)+ " = rs.getInt(\""+para.getV().get(i)+"\");");	
			}
		}
		//find if it exist
		p("				for(int i = 0; i < al.size(); i++){");
		for( int i = 0; i < para.getV().size(); i++){
			if(list.get(para.getV().get(i)).contains("char")){
				p("					if(ga"+String.valueOf(i)+".equals(al.get(i)."+para.getV().get(i)+")){");
			}
			else{
				p("					if(ga"+String.valueOf(i)+" == al.get(i)."+para.getV().get(i)+"){");
			}
		}
		p("						Pos = i;");
		p("						exist =true;");
		for( int i = 0; i < para.getV().size(); i++){
			p("					}");
		}
		//for loop end
		p("				}");
		//if exist == true, if no 0 exist, continue;
		//if exist == true and have _0_, update it;
		p("				if(exist){");
		for(sBean ben : para.getF()){
			//need update
			if(ben.name.contains("0_")){
				String [] s = ben.name.split("_");
				p("  					al.get(Pos)._"+ben.name+" = update(al.get(Pos)._"+ben.name+", + rs.getInt(\""+s[s.length -1]+"\"));");
				break;
			}
		}
		p("				continue;");
		
		
		
		p("				}");
		p("				else{");
		p("					mfTableBean temp = new mfTableBean();");
		for( int i = 0; i < para.getV().size(); i++){
			p("					temp."+ para.getV().get(i)+" = ga"+String.valueOf(i) + ";");
		}
		for(sBean ben : para.getF()){
			//need update
			if(ben.name.contains("0_")){
				String [] s = ben.name.split("_");
				p("  					temp._"+ben.name+" = update(temp._"+ben.name+", + rs.getInt(\""+s[s.length -1]+"\"));");
				break;
			}
		}
		p("					al.add(temp);");	
		p("				}");
		
		
		p("			}");
		//loop 1~n
		for(int lop = 1; lop <= para.getN(); lop ++){
			p("			rs=st.executeQuery(\"select * from sales\");");
			p("			while(rs.next()){");
			for (int i = 0; i < para.getV().size(); i++) {
				String type = list.get(para.getV().get(i));
				if (type.contains("chara")) {
					p("				String ga"+String.valueOf(i)+ " = rs.getString(\""+para.getV().get(i)+"\");");	
				}
				else{
					p("				int ga"+String.valueOf(i)+ " = rs.getInt(\""+para.getV().get(i)+"\");");	
				}
			}
			
			///EMF & MF
			for( String sig : para.getSigma()){
				if(sig.startsWith(String.valueOf(lop))){
					//get operator
					String [] con = sig.split(">=|<=|!=|=|>|<");
					String [] left=con[0].split("\\.|_");
					Pattern p=Pattern.compile("[>=]{2}|[<=]{2}|=|<|>|[!=]{2}");
					Matcher m=p.matcher(sig);
					String sign="";
					while(m.find()){
						
						sign=sig.substring(m.start(),m.end());
					}
					if(list.get(left[left.length -1]).contains("charac")){
						p("					if(rs.getString(\""+left[left.length -1]+"\").equals(\""+con[1].replace("'", "")+"\")){");
					}
					else{
						if(sign.equals("=")){
							p("					if(rs.getInt(\""+left[left.length -1]+"\") == "+con[1]+"){");
						}
						else{
							p("					if(rs.getInt(\""+left[left.length -1]+"\")"+ sign +" "+con[1]+"){");
						}
					}

				}
			}
			//find if it exist
			p("				for(int i = 0; i < al.size(); i++){");
			for( int i = 0; i < para.getV().size(); i++){
				if(list.get(para.getV().get(i)).contains("charac")){
					p("					if(ga"+String.valueOf(i)+".equals(al.get(i)."+para.getV().get(i)+")){");
				}
				else{
					p("					if(ga"+String.valueOf(i)+" == al.get(i)."+para.getV().get(i)+"){");
				}
			}
			for(sBean sb : para.getS()){
				if(sb.name.startsWith(String.valueOf(lop))){
					String[] strs = sb.name.split("_");
					p("						al.get(i)._"+sb.name +" = update(al.get(i)._"+sb.name +", rs.getInt(\""+ strs[2]+ "\"));");
				}
			}
			///
			//p("						Pos = i;");
			for( String sig : para.getSigma()){
				if(sig.startsWith(String.valueOf(lop))){
					p("					}");
				}
			}
			for( int i = 0; i < para.getV().size(); i++){
				p("					}");
			}
			//for loop end
			p("				}");
			p("			}");
		}
		
		
		
		
		
		
		
		
		
		
		
		
		p("		} catch ( Exception e ){");
		p("			e.printStackTrace();}");
		
		
		
		
		
		
		
		
		p("}");
	}
	
	
	/*public void generateMfTable(HashMap<String, String> list, Parameters para){		
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
//*/
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
	

	public void generatePrint(Parameters pa){
		p("public void print(){");
		pl("\tSystem.out.println(\"");// + pa.getS().get(0).name);
		for(int i = 0; i < pa.getS().size(); i++){
			pl("\\t\\t"+ pa.getS().get(i).name);
		}
		pl("\");");
		p("");

		p("			for(mfTableBean mfb : al){");
		pl("				System.out.println(\" \" ");
		for(sBean sb: pa.getS()){
			if(sb.name.contains("_")){
				String [] psk = sb.name.split("_");
				pl(" +\"\\t\\t\" + mfb._" + sb.name + ".");
				switch(psk[1]){
				case "min":
					pl("Min");
					break;
				case "max":
					pl("Max");
					break;
				case "count":
					pl("Count");
					break;
				case "sum":
					pl("Sum");
					break;
				case "avg":
					pl("Avg");
					break;
				}
			}
			else
			{
				pl(" + \"\\t\\t\" + mfb."+sb.name);
			}
		}
		pl(");");
		p("}");
		
		p("}");
	}
	
	
	public void generateUpdate(){
		p("	public ClassOfAll update(ClassOfAll all, int value){");
		p("		if(all.Max < value){");
		p("			all.Max = value;");
		p("		}");
		p("		if(all.Min > value){");
		p("			all.Min = value;");
		p("		}");
		p("		all.Sum += value;");
		p("		all.Count++;");
		p("		return all;");
		p("	}");
	}
	public static void pl(String s){
		try {
			fw.append(s);
			//fw.append("\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(s);
	}
	

}
