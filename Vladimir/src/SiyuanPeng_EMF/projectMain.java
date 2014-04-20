/**projectMain.java
 * 
 * author: Pengfei Zhang
 * 
 * Function: test
 */

package SiyuanPeng_EMF;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import SiyuanPeng.*;

public class projectMain {

	static FileWriter fw=null;
	public static void main(String[] args) {
		try {
			fw=new FileWriter("./src/programGenerated.java");
		} catch (IOException e) {
			e.printStackTrace();
		}

		Util_list util=new Util_list();
		util.mfStructureGenerator();
		Parameters para=new Parameters();
		para.parse();
		projectMain pm=new projectMain();
		pm.generateImport();
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

	public void generateMain(List<InfoSchemaBean> list, Parameters para){
		p("public class programGenerated {");

		//Create bean
		p("	class mfTableBean{");
		for(String setstr : para.getBeanset()){//number of projected attributes
			int n;
			if(setstr.startsWith("count")){
				p("\t\t"+"int "+setstr+";");
				continue;
			}
			for (int j = 0; j < list.size(); j++) {		//iterate information_schema bean list to match the parameter's type
				if(setstr.endsWith(list.get(j).getColumn_name())){
					n=j;//return the index of the parameter in util.list
					if(list.get(n).getData_type().startsWith("character"))
						p("\t\t"+"String "+setstr+";");
					else if(list.get(n).getData_type().equals("integer"))
						p("\t\t"+"int "+setstr+";");
					break;
				}
			}
		}
		p("	}");

		p("	ArrayList<mfTableBean> al=new ArrayList<>();");

		p("	public static void main(String[] args) {");
		p("		programGenerated main=new programGenerated();");
		p("		main.mfTableGenerator();");
		p("		main.print();");
		p("	}");

		p("	public void mfTableGenerator(){");
		p("		ResultSet rs=null;");
		p("		try(Connection conn=DBUtil.getInstance().getConnection();\n\t\t\t Statement st=conn.createStatement();) {");
		//all the statements until here are stable.	

		//First scan to build mfStructure
		p("			rs=st.executeQuery(\"select * from sales;\");");
		p("			while(rs.next()){");
		p("				boolean existed=false;");
		p("				for (int i = 0; i < al.size(); i++) {");

		//temp0.equals and temp1.equals
		for (int i = 0; i < para.getV().size(); i++) {
			int n=0;
			for (int j = 0; j < list.size(); j++) {		//iterate information_schema bean list to match the parameter's type
				if(para.getV().get(i).endsWith(list.get(j).getColumn_name())){
					n=j;
					if(list.get(n).getData_type().startsWith("character"))
						p("					if(rs.getString(\""+para.getV().get(i)+"\").equals(al.get(i)."+para.getV().get(i)+")){");
					else if(list.get(n).getData_type().equals("integer"))
						p("					if(rs.getInt(\""+para.getV().get(i)+"\")==al.get(i)."+para.getV().get(i)+"){");
					break;
				}
			}
		}

		//In if condition | Update _0_
		for (String f : para.getF()) {
			if(f.contains("_0_")){
				String[] splitF=f.split("_");
				if(f.startsWith("avg_0")){
					p("						al.get(i).countforavg_0_"+splitF[2]+"++;");
					p("						al.get(i).sumforavg_0_"+splitF[2]+"+=rs.getInt(\""+splitF[2]+"\");");
				}else if(f.startsWith("max_0")){
					p("						if(al.get(i).max_0_"+splitF[2]+"<rs.getInt(\""+splitF[2]+"\")){");
					p("							al.get(i).max_0_"+splitF[2]+"=rs.getInt(\""+splitF[2]+"\");");
					p("						}");
				}else if(f.startsWith("min_0")){
					p("						if(al.get(i).min_0_"+splitF[2]+">rs.getInt(\""+splitF[2]+"\")){");
					p("							al.get(i).min_0_"+splitF[2]+"=rs.getInt(\""+splitF[2]+"\");");
					p("						}");
				}else if(f.startsWith("sum_0")){
					p("						al.get(i).sum_0_"+splitF[2]+"+=rs.getInt(\""+splitF[2]+"\");");
				}else if(f.startsWith("count_0")){
					p("						al.get(i).count_0_++;");
				}

			}
		}

		p("						existed=true;");
		p("						break;");

		for (int i = 0; i < para.getV().size(); i++) { //brackets
			p("					}");
		}
		p("				}");

		//New a bean | Using group variables
		p("				if(!existed){");
		p("					mfTableBean tempbean=new mfTableBean();");
		for (String v : para.getV()) {	//number of projected attributes
			int n=0;
			for (int j = 0; j < list.size(); j++) {		//iterate information_schema v list to match the parameter's type
				if(v.endsWith(list.get(j).getColumn_name())){
					n=j;
					if(list.get(n).getData_type().startsWith("character"))
						p("					tempbean."+v+"=rs.getString(\""+list.get(n).getColumn_name()+"\");");
					else if(list.get(n).getData_type().equals("integer"))
						p("					tempbean."+v+"=rs.getInt(\""+list.get(n).getColumn_name()+"\");");
					break;
				}
			}
		}
		//Initialize "_0_" and "min_?_"
		for (String bean : para.getBeanset()) {
			if(bean.contains("_0_")||bean.startsWith("min")){
				String[] temp=bean.split("_");
				if(bean.startsWith("countforavg")||bean.startsWith("count"))
					p("					tempbean."+bean+"=1;");
				else if(bean.startsWith("min"))
					p("					tempbean."+bean+"=2147483647;");
				else{
					p("					tempbean."+bean+"=rs.getInt(\""+temp[2]+"\");");
				}
			}
		}

		p("					al.add(tempbean);");
		p("				}");
		p("			}");

		//Fix avg | avg=sumforavg/countforavg
		for (String bean : para.getBeanset()) {
			if(bean.startsWith("avg_0_")){
				p("			for (mfTableBean bean : al) {");
				p("				if(bean."+bean.replace("avg", "countforavg")+"!=0)");
				p("					bean."+bean+"=bean."+bean.replace("avg", "sumforavg")+"/bean."+bean.replace("avg", "countforavg")+";");
				p("				else");
				p("					bean."+bean+"=0;");
				p("			}");
			}
		}


		//2nd, 3rd scans and so on
		//temp0 and temp1
		for (int num = 1; num <= para.getN(); num++) {
			p("			rs=st.executeQuery(\"select * from sales;\");");
			p("			while(rs.next()){");
			p("				for (int i = 0; i < al.size(); i++) {");

			//In if condition | Using Sigma as the filter
			int sigmatime=0;
			for (String sigma : para.getSigma()) {
				String[] splitSigma=sigma.split(">=|<=|=|<|>|!=");
				if(splitSigma[0].contains("_"+String.valueOf(num)+"_")){
					String[] left=splitSigma[0].split("_");
					Pattern p=Pattern.compile("[>=]{2}|[<=]{2}|=|<|>|[!=]{2}");
					Matcher m=p.matcher(sigma);
					String sign="";
					while(m.find()){
						sign=sigma.substring(m.start(),m.end());
					}

					if(sign.startsWith("="))
						sign=sign.replace("=", "==");
					int n=0;
					for (int j = 0; j < list.size(); j++) {		//iterate information_schema bean list to match the parameter's type
						if(left[2].endsWith(list.get(j).getColumn_name())){
							n=j;
							if(list.get(n).getData_type().startsWith("character")){
								if(splitSigma[1].startsWith(left[2])){
									if(sign.equals("!="))
										p("					if(!rs.getString(\""+left[2]+"\").equals(al.get(i)."+splitSigma[1]+")){");
									else
										p("					if(rs.getString(\""+left[2]+"\").equals(al.get(i)."+splitSigma[1]+")){");
								}else{
									if(sign.equals("!="))
										p("					if(!rs.getString(\""+left[2]+"\").equals(\""+splitSigma[1]+"\")){");
									else
										p("					if(rs.getString(\""+left[2]+"\").equals(\""+splitSigma[1]+"\")){");
								}
							}else if(list.get(n).getData_type().equals("integer")){
								if(splitSigma[1].startsWith(left[2])||splitSigma[1].startsWith("max")||splitSigma[1].startsWith("avg")||splitSigma[1].startsWith("sum")||splitSigma[1].startsWith("min")||splitSigma[1].startsWith("sumforavg"))
									p("					if(rs.getInt(\""+left[2]+"\")"+sign+"al.get(i)."+splitSigma[1]+"){");
								else
									p("					if(rs.getInt(\""+left[2]+"\")"+sign+splitSigma[1]+"){");
							}
							break;
						}
					}
					sigmatime++;
				}
			}

			//Manipulation in if condition
			for (String bean : para.getBeanset()) {
				String[] splitBean=bean.split("_");
				if(bean.contains("_"+num+"_")&&!bean.startsWith("avg_"+num)&&!bean.startsWith("sum_"+num)&&!bean.startsWith("sumforavg_"+num)&&!bean.startsWith("min_"+num)&&!bean.startsWith("max_"+num)&&!bean.startsWith("countforavg_"+num)&&!bean.startsWith("count_"+num)){
					for (int i = 0; i < list.size(); i++) {
						int n=0;
						if(splitBean[2].equals(list.get(i).getColumn_name())){
							n=i;
							if(list.get(n).getData_type().startsWith("character"))
								p("						al.get(i)."+bean+"=rs.getString(\""+splitBean[2]+"\");");
							else if(list.get(n).getData_type().equals("integer"))
								p("						al.get(i)."+bean+"=rs.getInt(\""+splitBean[2]+"\");");
						}
					}
				}else if(bean.startsWith("avg_"+num)){
					p("						al.get(i).countforavg_"+num+"_"+splitBean[2]+"++;");
					p("						al.get(i).sumforavg_"+num+"_"+splitBean[2]+"+=rs.getInt(\""+splitBean[2]+"\");");
				}else if(bean.startsWith("max_"+num)){
					p("						if(al.get(i).max_"+num+"_"+splitBean[2]+"<rs.getInt(\""+splitBean[2]+"\")){");
					p("							al.get(i).max_"+num+"_"+splitBean[2]+"=rs.getInt(\""+splitBean[2]+"\");");
					p("						}");
				}else if(bean.startsWith("min_"+num)){
					p("						if(al.get(i).min_"+num+"_"+splitBean[2]+">rs.getInt(\""+splitBean[2]+"\")){");
					p("							al.get(i).min_"+num+"_"+splitBean[2]+"=rs.getInt(\""+splitBean[2]+"\");");
					p("						}");
				}else if(bean.startsWith("sum_"+num)){
					p("						al.get(i).sum_"+num+"_"+splitBean[2]+"+=rs.getInt(\""+splitBean[2]+"\");");
				}else if(bean.startsWith("count_"+num)){
					p("						al.get(i).count_"+num+"_++;");
				}

			}

			for (int i = 0; i < sigmatime; i++) {
				p("					}");
			}
			p("				}");//for
			p("			}");//while

			//Fix avg | avg=sumforavg/countforavg
			for (String bean : para.getBeanset()) {
				if(bean.startsWith("avg_"+num)){
					p("			for (mfTableBean bean : al) {");
					p("				if(bean."+bean.replace("avg", "countforavg")+"!=0)");
					p("					bean."+bean+"=bean."+bean.replace("avg", "sumforavg")+"/bean."+bean.replace("avg", "countforavg")+";");
					p("				else");
					p("					bean."+bean+"=0;");
					p("			}");
				}
			}
		}

		p("		} catch (SQLException e) {");
		p("			e.printStackTrace();");
		p("		}");
		p("	}");

		p("	public void print(){");
		for (String s : para.getS()) {
			p("			System.out.print(\""+s+".....\");");
		}
		p("			System.out.println();");
		p("		for (int i = 0; i < al.size(); i++) {");
		for (String s : para.getS()) {
			p("			System.out.print(al.get(i)."+s+"+\".....\");");	
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
	}
}
