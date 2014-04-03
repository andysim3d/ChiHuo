/**projectMain.java
 * 
 * author: Pengfei Zhang
 * 
 * Function: test
 */

package edu.stevens.cs562;

import java.util.List;

import SiyuanPeng.*;

public class projectMain {
	public static void p(String s){
		System.out.println(s);
	}
	
	public static void main(String[] args) {
		projectMain pm=new projectMain();
//		p("import SiyuanPeng.*;");
		Util util=new Util();
		util.mfStructureGenerator();
		pm.beanCreator(util.list);
	}
	
	/**
	 *Create the JavaBean class for the program to be generated. 
	 */
	public void beanCreator(List<InfoSchemaBean> list){
		p("public class mfTableBean{");
		for (InfoSchemaBean bean : list) {
			if(bean.getData_type().startsWith("character"))
				p("\t"+"public String "+bean.getColumn_name()+";");
			else if(bean.getData_type().equals("integer"))
				p("\t"+"public int "+bean.getColumn_name()+";");
		}
		p("}");
	}
	
}
