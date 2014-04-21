/**projectMain.java
 * 
 * author: Pengfei Zhang
 * 
 * Function: test
 */

package SiyuanPeng_program;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Pengfei.Zhang.sBean;
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
		util.UtilGenerator();
		Parameters para=new Parameters();
		//para.parse();
		projectMain pm=new projectMain();
		pm.generateImport();
		pm.generateMain(util.list, para);
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

	public void generateMain(HashMap<String, String> list, Parameters para){
		p("public class programGenerated {");

		//Create bean
		p("	class mfTableBean{");
		for(sBean setstr : para.getBeanset()){//number of projected attributes
			int n;
			if(setstr.type.startsWith("integer")){
				p("\t\t"+"int "+setstr.name+";");
				continue;
			}
			if (setstr.type.startsWith("character")) {
				p("\t\t"+"String "+setstr.name+";");
				continue;
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

		//Define temp0 and temp1
		/*for (int i = 0; i < para.getV().size(); i++) {	//in while loop, define the group by variables 
			int n=0;
			for (int j = 0; j < list.size(); j++) {		//iterate information_schema bean list to match the parameter's type
				if(para.getV().get(i).endsWith(list.get(j).getColumn_name())){
					n=j;
					if(list.get(n).getData_type().startsWith("character"))
						p("				String temp"+i+"=rs.getString(\""+para.getV().get(i)+"\");");
					else if(list.get(n).getData_type().equals("integer"))
						p("				int temp"+i+"=rs.getInt(\""+para.getV().get(i)+"\");");
					break;
				}
			}
		}///*/
		//find group attr and declear them;
		ArrayList<sBean> groupattr = new ArrayList<>(); 
		for (sBean bean : para.getS()) {
			if (bean.name.contains("_")) {
				continue;
			}
			else{
				groupattr.add(bean);
				if(bean.type.contains("character"))
				{
					p("				String temp"+bean.name+"=rs.getString(\""+bean.name+"\");");
				}
				else if(bean.type.contains("integer"))
				{
					p("				int temp"+bean.name+"=rs.getString(\""+bean.name+"\");");
				}
			}
		}
		
		p("				boolean existed=false;");
		p("				for (int i = 0; i < al.size(); i++) {");

		//compare upcoming value and compare them with existing value
		for(sBean key:groupattr){
			if (key.type.contains("character")) {
				p("					if(temp"+key.name+".equals(al.get(i)."+key.name+")){");
			}
			else
			{
				p("					if(temp"+key.name+" == al.get(i)."+key.name+"){");
			}
		}
		
		
		
		//temp0.equals and temp1.equals
		/*for (int i = 0; i < para.getV().size(); i++) {
			int n=0;
			for (int j = 0; j < list.size(); j++) {		//iterate information_schema bean list to match the parameter's type
				if(para.getV().get(i).endsWith(list.get(j).getColumn_name())){
					n=j;
					if(list.get(n).getData_type().startsWith("character"))
						p("					if(temp"+i+".equals(al.get(i)."+para.getV().get(i)+")){");
					else if(list.get(n).getData_type().equals("integer"))
						p("					if(temp"+i+"==al.get(i)."+para.getV().get(i)+"){");
					break;
				}
			}
		}//*/

		//In if condition | Update _0_
		for (sBean f : para.getF()) {
			if(f.name.contains("0_")){
				String[] splitF=f.name.split("_");
				if(f.name.contains("avg")){
					p("						al.get(i).countforavg_0_"+splitF[2]+"++;");
					p("						al.get(i).sumforavg_0_"+splitF[2]+"+=rs.getInt(\""+splitF[2]+"\");");
				}else if(f.name.contains("max")){
					p("						if(al.get(i).max_0_"+splitF[2]+"<rs.getInt(\""+splitF[2]+"\")){");
					p("							al.get(i).max_0_"+splitF[2]+"=rs.getInt(\""+splitF[2]+"\");");
					p("						}");
				}else if(f.name.contains("min")){
					p("						if(al.get(i).min_0_"+splitF[2]+">rs.getInt(\""+splitF[2]+"\")){");
					p("							al.get(i).min_0_"+splitF[2]+"=rs.getInt(\""+splitF[2]+"\");");
					p("						}");
				}else if(f.name.contains("sum")){
					p("						al.get(i).sum_0_"+splitF[2]+"+=rs.getInt(\""+splitF[2]+"\");");
				}else if(f.name.contains("count")){
					p("						al.get(i).count_0_"+splitF[2]+"++;");
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
			
			if(list.get(v).contains("character")){
				p("					tempbean."+v+"=rs.getString(\""+v+"\");");
			}
			else if (list.get(v).contains("integer")) {
				p("					tempbean."+v+"=rs.getInt(\""+v+"\");");
			}
			
		}
		//Initialize "_0_" and "min_?_"
		
		for (sBean bean : para.getBeanset()) {
			if(bean.name.contains("0_")||bean.name.contains("min")){
				String[] temp=bean.name.split("_");
				///misunderstanding
				if(bean.name.contains("countforavg"))
					p("					tempbean."+bean.name+"=1;");
				else if(bean.name.contains("min"))
					p("					tempbean."+bean.name+"=2147483647;");
				else{
					p("					tempbean."+bean.name+"=rs.getInt(\""+temp[2]+"\");");
				}
			}
		}

		p("					al.add(tempbean);");
		p("				}");
		p("			}");

		//Fix avg | avg=sumforavg/countforavg
		for (sBean bean : para.getBeanset()) {
			if(bean.name.contains("0_avg")){
				p("			for (mfTableBean bean : al) {");
				p("				if(bean."+bean.name.replace("avg", "countforavg")+"!=0)");
					p("					bean."+bean.name+"=bean."+bean.name.replace("avg", "sumforavg")+"/bean."+bean.name.replace("avg", "countforavg")+";");
					p("				else");
					p("					bean."+bean.name+"=0;");
				p("			}");
			}
		}


		//2nd, 3rd scans and so on
		//temp0 and temp1
		for (int num = 1; num <= para.getN(); num++) {
			p("			rs=st.executeQuery(\"select * from sales;\");");
			p("			while(rs.next()){");
			for(sBean key : groupattr){
				if (key.type.contains("character")) {
					p("				String temp"+key.name+"=rs.getString(\""+key.name+"\");");
				}
				else if (key.type.contains("integer")) {
					p("				String temp"+key.name+"=rs.getInt(\""+key.name+"\");");
				}
			}
		/*	for (int i = 0; i < para.getV().size(); i++) {	//in while loop, define the group by variables 
				int n=0;
				for (int j = 0; j < list.size(); j++) {		//iterate information_schema bean list to match the parameter's type
					if(para.getV().get(i).endsWith(list.get(j).getColumn_name())){
						n=j;
						if(list.get(n).getData_type().startsWith("character"))
							p("				String temp"+i+"=rs.getString(\""+para.getV().get(i)+"\");");
						else if(list.get(n).getData_type().equals("integer"))
							p("				int temp"+i+"=rs.getInt(\""+para.getV().get(i)+"\");");
						break;
					}
				}	

			}//*/
			//temp0.equals and temp1.equals
			p("				for (int i = 0; i < al.size(); i++) {");
			for (sBean key : groupattr) {
				if (key.type.contains("character")) {
					p("					if(temp"+key.name+".equals(al.get(i)."+key.name+")){");
					}
				else if (key.type.contains("integer")) {
					p("					if(temp"+key.name+"==al.get(i)."+key.name+"){");
				}
			}
			
			
			/*
			int vtime=0;
			for (int i = 0; i < para.getV().size(); i++) {
				int n=0;
				for (int j = 0; j < list.size(); j++) {		//iterate information_schema bean list to match the parameter's type
					if(para.getV().get(i).endsWith(list.get(j).getColumn_name())){
						n=j;
						if(list.get(n).getData_type().startsWith("character"))
							p("					if(temp"+i+".equals(al.get(i)."+para.getV().get(i)+")){");
						else if(list.get(n).getData_type().equals("integer"))
							p("					if(temp"+i+"==al.get(i)."+para.getV().get(i)+"){");
						break;
					}
				}	
				vtime++;
			}//*/

			//In if condition | Using Sigma as the filter
			int sigmatime=0;
			for (String sigma : para.getSigma()) {
				//split with operator
				String[] splitSigma=sigma.split("<=|>=|=|<|>|!=");
				//if num not equal, compare with next sigma
				if(!splitSigma[0].contains(String.valueOf(num))){
					continue;
				}
				String right = null;
				//find the column name
				if(splitSigma[0].contains("_")){
					String [] right_ = splitSigma[0].split("_");
					right = right_[1];
				}
				else{
					String [] right_ = splitSigma[0].split(".");
					right = right_[1];
				}
				//use the regex to pick the parameters and operator;
				String[] left=splitSigma[0].split(".|_");
				Pattern p=Pattern.compile("[>=]{2}|[<=]{2}|=|<|>|[!=]{2}");
				Matcher m=p.matcher(sigma);
				String sign="";
				while(m.find()){
					sign=sigma.substring(m.start(),m.end());
				}
				
				///
				if(sign.startsWith("="))
					sign=sign.replace("=", "==");
				else if(sign.startsWith(">"))
					sign=sign.replace(">", "<");//converse the sign
				else if(sign.startsWith("<"))
					sign=sign.replace("<", ">");//converse the sign

				
				
				
				/*
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
					else if(sign.startsWith(">"))
						sign=sign.replace(">", "<");//converse the sign
					else if(sign.startsWith("<"))
						sign=sign.replace("<", ">");//converse the sign

//					if()
					//*/
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
				if(list.get(left[left.length -1]).contains("character"))
				{
					if(sign.equals("!="))
					{
						p("					if(!\""+splitSigma[1]+"\".equals(rs.getString(\""+left[2]+"\"))){");	
					}
					else{
						p("					if(\""+splitSigma[1]+"\".equals(rs.getString(\""+left[2]+"\"))){");
					}
				}
				else if(list.get(left[left.length -1]).contains("integer")){
					if(splitSigma[1].contains("max")||splitSigma[1].contains("avg")||splitSigma[1].contains("sum")||splitSigma[1].contains("min")||splitSigma[1].contains("sumforavg"))
						p("					if(al.get(i)."+splitSigma[1]+sign+"rs.getInt(\""+left[2]+"\")){");
					else
						p("					if("+splitSigma[1]+sign+"rs.getInt(\""+left[2]+"\")){");

				}
					/*
					int n=0;
					for (int j = 0; j < list.size(); j++) {		//iterate information_schema bean list to match the parameter's type
						
						if(left[2].endsWith(list.get(j).getColumn_name())){
							n=j;
							if(list.get(n).getData_type().startsWith("character"))
								if(sign.equals("!="))
									p("					if(!\""+splitSigma[1]+"\".equals(rs.getString(\""+left[2]+"\"))){");	
								else
									p("					if(\""+splitSigma[1]+"\".equals(rs.getString(\""+left[2]+"\"))){");
							else if(list.get(n).getData_type().equals("integer"))
								if(splitSigma[1].startsWith("max")||splitSigma[1].startsWith("avg")||splitSigma[1].startsWith("sum")||splitSigma[1].startsWith("min")||splitSigma[1].startsWith("sumforavg"))
									p("					if(al.get(i)."+splitSigma[1]+sign+"rs.getInt(\""+left[2]+"\")){");
								else
									p("					if("+splitSigma[1]+sign+"rs.getInt(\""+left[2]+"\")){");
							break;
						}
					}
					sigmatime++;
				}//*/
			}

			//Manipulation in if condition
			for (sBean bean : para.getBeanset()) {
				String[] splitBean=bean.name.split("_");
				if(bean.name.contains(num+"_")&&!bean.name.contains("avg_"+num)&&!bean.name.contains("sum_"+num)&&!bean.name.contains("sumforavg_"+num)&&!bean.name.contains("min_"+num)&&!bean.name.contains("max_"+num)&&!bean.name.contains("countforavg_"+num)&&!bean.name.contains("count_"+num)){
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

			p("						break;");
			for (int i = 0; i < sigmatime; i++) {
				p("					}");
			}
			//for (int i = 0; i < vtime; i++) {
			//	p("					}");
			//}
			p("				}");//for
			p("			}");//while

			//Fix avg | avg=sumforavg/countforavg
			for (sBean bean : para.getBeanset()) {
				if(bean.name.contains(num + "_avg")){
					p("			for (mfTableBean bean : al) {");
					p("				if(bean."+bean.name.replace("avg", "countforavg")+"!=0)");
					p("					bean."+bean.name+"=bean."+bean.name.replace("avg", "sumforavg")+"/bean."+bean.name.replace("avg", "countforavg")+";");
					p("				else");
					p("					bean."+bean.name+"=0;");
					p("			}");
				}
			}
		}

		p("		} catch (SQLException e) {");
		p("			e.printStackTrace();");
		p("		}");
		p("	}");

		p("	public void print(){");
		for (sBean s : para.getS()) { 
			p("			System.out.print(\""+s.name+".....\");");
		}
		p("			System.out.println();");
		p("		for (int i = 0; i < al.size(); i++) {");
		for (sBean s : para.getS()) {
			p("			System.out.print(al.get(i)."+s.name+"+\".....\");");	
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
