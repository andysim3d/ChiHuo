/**projectMain.java
 * 
 * author: Pengfei Zhang
 * 
 * Function: test
 */

package edu.stevens.cs562;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import SiyuanPeng.*;

public class projectMain {

	static FileWriter fw=null;
	public static void main(String[] args) {
		try {
			fw=new FileWriter("./generatedProgram.java");
		} catch (IOException e) {
			e.printStackTrace();
		}

		Util util=new Util();
		util.mfStructureGenerator();
		//		p("import SiyuanPeng.*;");
		Parameters para=new Parameters();
		para.parse();
		projectMain pm=new projectMain();
		pm.generateImport();
		pm.generateBean(util.list, para);

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

	public void generateMain(){
		
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
