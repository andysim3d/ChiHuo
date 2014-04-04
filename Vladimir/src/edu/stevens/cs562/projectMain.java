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
		pm.beanCreator(util.list, para);
		
		if(fw!=null){
			try {
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 *Create a JavaBean class for the program to be generated. 
	 */
	public void beanCreator(List<InfoSchemaBean> list, Parameters para){
		p("class mfTableBean{");
		for (int i = 0; i < para.getS().size(); i++) {
			int n;
			for (int j = 0; j < list.size(); j++) {
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
		p("}");
		

	}
	
	/**
	 *print the strings to the file and console 
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
