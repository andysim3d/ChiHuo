package Pengfei.Zhang;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Siyuan.Zheng.MF_Structure;
import Siyuan.Zheng.Parameters;
import Siyuan.Zheng.ParaseMF;
import Siyuan.Zheng.ParaseParameters;
import Siyuan.Zheng.sBean;
import SiyuanPeng.Util;

public class GenerateNewJAVA {

	// file writer
	static FileWriter fw = null;
	public boolean EMF;

	// main//
	public static void main(String[] args) {
		try {
			// init the file writer
			fw = new FileWriter("./src/programGenerated.java");
		} catch (IOException e) {
			e.printStackTrace();
		}

		// paramters & mf_structures
		Parameters para = new Parameters();
		para = ParaseParameters.ParaseInput();
		MF_Structure mf_s = new MF_Structure();
		mf_s = ParaseMF.mfParase(para);
		//
		// get information_schema
		Util util = new Util();
		util.UtilGenerator();
		// build a instance of GenerateNewJAVA class

		GenerateNewJAVA pm = new GenerateNewJAVA();

		// generate property import lib;
		pm.generateImport();
		pm.generateGeneralDS();
		// generate data-structure;
		pm.generateBean(util.list, para);
		pm.generateMain(util.list, para);
		pm.generateMfTable(util.list, para, mf_s);
		pm.generateUpdate();
		pm.generatePrint(para);
		p("}");
		if (fw != null) {
			try {
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Print the class imported
	 */
	public void generateImport() {
		p("import java.sql.*;");  
		p("import java.util.ArrayList;");
		p("import SiyuanPeng.*;");
		p("import Pengfei.Zhang.*;");
	}

	/**
	 * Create a JavaBean class for the program to be generated.
	 */
	public void generateBean(HashMap<String, String> list, Parameters para) {
		p("class mfTableBean{");
		for (sBean k : para.getBeanset()) {
			String[] afterSplit = k.name.split("_");
			if (k.type.equals("null")) {
				p("public String _" + k.name + " = \"Null\";");
			} else if (k.name.contains("_")) {
				p("public ClassOfAll _" + afterSplit[0] + "_" + afterSplit[2]
						+ " = new ClassOfAll();");
			} else {
				if (k.type.contains("char")) {
					p("public String " + k.name + ";");
				} else {
					p("public int " + k.name + ";");
				}
			}
		}
		p("}");
	}

	public void generateGeneralDS() {
		p("class ClassOfAll{");
		p("public String incase = \"\";");
		p("public int Max = 0;");
		p("public int Min = 99999;");
		p("public int Count = 0;");
		p("public double Sum = 0;");
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

		p("public double getAvg(){");
		p("	double sum = this.Sum;");
		p("if(Count == 0){");
		p("return 0;");
		p("}");
		p(" sum = sum/Count;");
		p("	return sum;");
		p("}");
		p("}");
	}

	public void generateMain(HashMap<String, String> list, Parameters para) {
		// generate connection and arraylist to store data;
		p("public class programGenerated {");
		p("	Connection conn=null;");
		p("	ArrayList<mfTableBean> al=null;");

		//
		p("	public static void main(String[] args) {");
		p("		programGenerated main=new programGenerated();");
		// used to generate the table
		p("		main.mfTableGenerator();");
		// generate out put;
		p("		main.print();");
		p("	}");
	}

	public void generateMfTable(HashMap<String, String> list, Parameters para,
			MF_Structure mf) {
		p("public void mfTableGenerator(){");
		p(" al = new ArrayList<mfTableBean>(); ");
		// connect database
		p("    	conn = DBUtil.getInstance().getConnection();");
		p("		Statement st = null;");
		p("		int Pos = 0;");
		p("		ResultSet rs=null;");
		p("		try {");
		p("			st=conn.createStatement();");
		p("			rs=st.executeQuery(\"select * from sales\");");
		// loop 0, generate table and title;
		p("			while(rs.next()){");
		p("				boolean exist = false;");
		for (int i = 0; i < para.getV().size(); i++) {
			String type = list.get(para.getV().get(i));
			if (type.contains("chara")) {
				p("				String ga" + String.valueOf(i) + " = rs.getString(\""
						+ para.getV().get(i) + "\");");
			} else {
				p("				int ga" + String.valueOf(i) + " = rs.getInt(\""
						+ para.getV().get(i) + "\");");
			}
		}
		// find if it exist
		p("				for(int i = 0; i < al.size(); i++){");
		for (int i = 0; i < para.getV().size(); i++) {
			if (list.get(para.getV().get(i)).contains("char")) {
				p("					if(ga" + String.valueOf(i) + ".equals(al.get(i)."
						+ para.getV().get(i) + ")){");
			} else {
				p("					if(ga" + String.valueOf(i) + " == al.get(i)."
						+ para.getV().get(i) + "){");
			}
		}
		p("						Pos = i;");
		p("						exist =true;");
		for (int i = 0; i < para.getV().size(); i++) {
			p("					}");
		}
		// for loop end
		p("				}");
		// if exist == true, if no 0 exist, continue;
		// if exist == true and have _0_, update it;
		p("				if(exist){");
		for (sBean ben : para.getF()) {
			// need update
			if (ben.name.contains("0_")) {
				String[] s = ben.name.split("_");
				p("  					al.get(Pos)._" + parseName(ben.name) + " = update(al.get(Pos)._"
						+ parseName(ben.name) + ", + rs.getInt(\"" + s[s.length - 1]
						+ "\"));");
				break;
			}
		}
		p("				continue;");

		p("				}");
		p("				else{");
		p("					mfTableBean temp = new mfTableBean();");
		for (int i = 0; i < para.getV().size(); i++) {
			p("					temp." + para.getV().get(i) + " = ga" + String.valueOf(i)
					+ ";");
		}
		for (sBean ben : para.getF()) {
			// need update
			if (ben.name.contains("0_")) {
				String[] s = ben.name.split("_");
				p("  					temp._" + parseName(ben.name) + " = update(temp._" + parseName(ben.name)
						+ ", + rs.getInt(\"" + s[s.length - 1] + "\"));");
				break;
			}
		}
		p("					al.add(temp);");
		p("				}");

		p("			}");
		// loop 1~n
		//used to count number of left brace
		int leftbrace = 0;
		for (int lop = 1; lop <= para.getN(); lop++) {
			p("			rs=st.executeQuery(\"select * from sales\");");
			p("			while(rs.next()){");
			leftbrace++;
			if (!para.getEMF()) {
				for (int i = 0; i < para.getV().size(); i++) {
					String type = list.get(para.getV().get(i));
					if (type.contains("chara")) {
						p("				String ga" + String.valueOf(i)
								+ " = rs.getString(\"" + para.getV().get(i)
								+ "\");");
					} else {
						p("				int ga" + String.valueOf(i) + " = rs.getInt(\""
								+ para.getV().get(i) + "\");");
					}
				}
			}

			p("				for(int i = 0; i < al.size(); i++){");
			// find if it exist in MF
			leftbrace++;
			if (!para.getEMF()) {
				for (int i = 0; i < para.getV().size(); i++) {
					if (list.get(para.getV().get(i)).contains("charac")) {
						p("					if(ga" + String.valueOf(i)
								+ ".equals(al.get(i)." + para.getV().get(i)
								+ ")){");
						leftbrace++;
					} else {
						p("					if(ga" + String.valueOf(i) + " == al.get(i)."
								+ para.getV().get(i) + "){");
						leftbrace++;
					}
				}
			}
			// /EMF & MF
			for (String sig : para.getSigma()) {
				if (sig.startsWith(String.valueOf(lop))) {

					// get operator
					String[] con = sig.split("<>|>=|<=|!=|=|>|<");
					String[] left = con[0].split("\\.|_");
					Pattern p = Pattern
							.compile("[>=]{2}|[<=]{2}|=|<|>|[!=]{2}");
					Matcher m = p.matcher(sig);
					String sign = "";
					while (m.find()) {
						sign = sig.substring(m.start(), m.end());
					}

					// if left is string, right should be string
					// get the data type
					String dataType = list.get(left[left.length - 1].replace(
							" ", ""));
					// if left operand type is string, right operand should be
					// string, too.

					if (dataType.contains("charac")) {
						// if operator is =, look right poerand is a string or a
						// column
						if (sign.equals("=")) {

							// if right operand has ', means right is a string
							if (con[1].contains("'")) {
								p("					if(rs.getString(\""
										+ left[left.length - 1]
										+ "\").equals(\""
										+ con[1].replace(".", "").replace("'",
												"") + "\")){");
								leftbrace++;
							}

							else {
								// else, compare it with member
								p("					if(rs.getString(\""
										+ left[left.length - 1]
										+ "\").equals(al.get(i)."
										+ con[1].replace(".", "").replace("'",
												"") + ")){");
								leftbrace++;
							}
						} else {
							// if operator is not =, operator should be <>.
							if (con[1].contains("'")) {
								// if right operand has ', means right is a
								// string
								p("					if(!rs.getString(\""
										+ left[left.length - 1]
										+ "\").equals(\""
										+ con[1].replace(".", "").replace("'",
												"") + "\")){");
								leftbrace++;
							}
							// else, compare it with member
							else {
								p("					if(!rs.getString(\""
										+ left[left.length - 1]
										+ "\").equals(al.get(i)."
										+ con[1].replace(".", "") + ")){");
								leftbrace++;
							}

						}
					}

					else {
						// else, if left is integer, right could be _0_
						// if right has ., it has _
						if (con[1].contains(".")) {
							// split it with .
							String[] cons = con[1].split("\\.");

							// cons should be like 1 avg_quant
							for (sBean sbs : para.getF()) {
								if (sbs.name.contains(cons[0])) {
									cons[0] = sbs.name;
								}
							}
							// split with _
							// conss should like avg, quant
							String[] conss = cons[0].split("_");
							// select the aggregate function
							switch (conss[1]) {
							case "avg":
								cons[0] += ".getAvg()";
								break;
							case "max":
								cons[0] += ".Max";
								break;

							case "min":
								cons[0] += "Min";
								break;

							case "count":
								cons[0] += "Count";
								break;

							case "sum":
								cons[0] += "Sum";
								break;
							}
							if (isOperator(con[1])) {
								String op = "";
								if (con[1].contains("+")) {
									op = "+";
								} else if (con[1].contains("-")) {
									op = "-";
								} else if (con[1].contains("*")) {
									op = "*";
								} else if (con[1].contains("/")) {
									op = "/";
								}
								// and split string with operators
								String[] rightPart = con[1]
										.split("\\+|\\*|-|/");
								String[] rightCol = rightPart[0].split("_");
								// here, con[1] should be XXXX - 1
								if (sign.equals("=")) {
									p("					if" + "(rs.getInt(\"" + rightCol[1]
											+ "\") " + "" + " == "
											+ "(al.get(i)._" + parseName(cons[0]) + ")"
											+ op + rightPart[1].toString()
											+ "){");
									leftbrace++;
								} else {
									p("					if" + "(rs.getInt(\"" + rightCol[1]
											+ "\") " + "" + sign
											+ "(al.get(i)._" + parseName(cons[0]) + ")"
											+ op + rightPart[1].toString()
											+ "){");
									leftbrace++;
								}
							} else {
								// if sign is =, change it to ==
								if (sign.equals("=")) {
									p("					if(rs.getInt(\""
											+ left[left.length - 1]
											+ "\") == al.get(i)._" + parseName(cons[0])
											+ "){");
									leftbrace++;
								}
								// else, compare them
								else {
									p("					if(rs.getInt(\""
											+ left[left.length - 1] + "\")"
											+ sign + "al.get(i)._" + parseName(cons[0])
											+ "){");
									leftbrace++;
								}
							}
						}
						// else, should detect if right operand has operator or
						// not
						else if (sign.equals("=")) {

							if (isOperator(con[1])) {
								String op = "";
								if (con[1].contains("+")) {
									op = "+";
								} else if (con[1].contains("-")) {
									op = "-";
								} else if (con[1].contains("*")) {
									op = "*";
								} else if (con[1].contains("/")) {
									op = "/";
								}
								// and split string with operators
								String[] rightPart = con[1]
										.split("\\+|\\*|-|/");
								// here, con[1] should be XXXX - 1
								p("					if((rs.getInt(\"" + rightPart[0]
										+ "\") " + " == " + "(al.get(i)."
										+ parseName(left[1]) + op
										+ rightPart[1].toString() + "))){");
								leftbrace++;

							}
							// if right opertend is int, just compare it

							// if operater
							else {
								if (isNumeric(con[1])) {
									p("					if(rs.getInt(\""
											+ left[left.length - 1] + "\")"
											+ "==" + " " + con[1] + "){");
								} else {
									p("					if(rs.getInt(\""
											+ left[left.length - 1] + "\")"
											+ "==" + "al.get(i)." + con[1]
											+ "){");
									leftbrace++;
								}
							}
						}
					}

				}
			}

			for (sBean sb : para.getF()) {
				if (sb.name.startsWith(String.valueOf(lop))) {
					String[] strs = sb.name.split("_");
					if (sb.name.contains("ALL")) {
						p("						al.get(i)._" + parseName(sb.name)
								+ " = update(al.get(i)._" + parseName(sb.name) + ", 1 );");
					} else {
						p("						al.get(i)._" + parseName(sb.name)
								+ " = update(al.get(i)._" + parseName(sb.name)
								+ ", rs.getInt(\"" + strs[2] + "\"));");
					}
				}
			}
			// /
			// p("						Pos = i;");
//			for (String sig1 : para.getSigma()) {
//				if (sig1.startsWith(String.valueOf(lop))) {
//					p("					}");
//				}
//			}
//			if (!para.getEMF()) {
//				for (int i = 0; i < para.getV().size(); i++) {
//					p("					}");
//				}
//			}
//			// for loop end
//			//p("				}");
			for(int i = 0; i < leftbrace; i ++){
				p("			}");
			}
			leftbrace = 0;
			//p("			}");
		}

		p("		} catch ( Exception e ){");
		p("			e.printStackTrace();}");

		p("finally{");
		p("		try{");
		p("			rs.close();");
		p("			conn.close();");
		p("			st.close();");
		p("		}catch(Exception e){");
		p("			e.printStackTrace();");
		p("		}");
		p("	}");
		p("}");

		// //lost a brace here
	}

	/**
	 * print method. print the strings to the file and the console.
	 */
	public static void p(String s) {
		try {
			fw.append(s);
			fw.append("\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(s);
	}

	public void generatePrint(Parameters pa) {
		p("public void print(){");
		pl("\tSystem.out.println(");// + pa.getS().get(0).name);
		for (int i = 0; i < pa.getS().size(); i++) {
			if(pa.getS().get(i).name.length()< 8){
				pl("outPutFormat.outPutFormats(\"" + pa.getS().get(i).name+"\", 8) +");
			}
			else
			{
				pl("outPutFormat.outPutFormats(\"" + pa.getS().get(i).name+"\", "+(pa.getS().get(i).name.length() + 2) + ") +");
			}
		}
		pl(" \" \" );");
		p("");

		p("			for(mfTableBean mfb : al){");
		pl("				System.out.println(\"\" ");
		for (sBean sb : pa.getS()) {
			if (sb.type.equals("null")) {
				if(sb.name.length() <=8){
					pl(" + " + "outPutFormat.outPutFormats(mfb._" + sb.name+",8)");
				}
				else{
					pl(" + " + "outPutFormat.outPutFormats(mfb._" + sb.name+","+ (sb.name.length()+2) +")");
				}
			} else if ((GenerateNewJAVA.isOperator(sb.name))
					&& (!sb.name.contains("_*"))) {
				String sss[] = sb.name.split("\\+|-|\\*|/");
				String op = "";
				if (sb.name.contains("+")) {
					op = "+";
				} else if (sb.name.contains("-")) {
					op = "-";
				} else if (sb.name.contains("*")) {
					op = "*";
				} else if (sb.name.contains("/")) {
					op = "/";
				}

				String[] psk = sss[0].split("_");
				pl(" + " + "outPutFormat.outPutFormats(mfb._" + parseName(sss[0]) +".");
				//pl(" +\"\\t\\t\" + mfb._" + parseName(sss[0]) + ".");
				switch (psk[1]) {
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
					pl("getAvg()");
					break;
				}
				pl(op);
				String[] psk2 = sss[1].split("_");
				pl("mfb._" + parseName(sss[1]) + ".");
				switch (psk[1]) {
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
					pl("getAvg()");
					break;
				}
				if(sb.name.length()<=8 ){
					pl(",8)");
				}
				else{
					pl("," + (sb.name.length()+2)+ ")");
				}
			} else if (sb.name.contains("_")) {
				String[] psk = sb.name.split("_");
				pl(" + outPutFormat.outPutFormats( mfb._" + parseName(sb.name) + ".");
				switch (psk[1]) {
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
					pl("getAvg()");
					break;
				}
				if(sb.name.length() <= 8){
					pl(",8)");
				}
				else{
					pl("," +(sb.name.length() + 2)+")");
				}
			} else {
				if(sb.name.length()<=8){
					pl(" + outPutFormat.outPutFormats(mfb." + sb.name + ",8)");
				}
				else{
					pl(" + outPutFormat.outPutFormats(mfb." + sb.name + "," +(sb.name.length()+2) + ")");
				}
			}
		}
		pl(");");
		p("}");

		p("}");
	}

	public static boolean isNumeric(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public void generateUpdate() {
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

	public static boolean isOperator(String str) {
		if ((str.contains("+")) || (str.contains("-")) || (str.contains("*"))
				|| (str.contains("/"))) {
			return true;
		}
		return false;
	}

	public static String parseName(String src) {
		
		String[] afterSplit = src.split("_");
		if(afterSplit.length <= 2){
			return src;
		}
		return afterSplit[0] + "_" + afterSplit[2];
	}

	public static void pl(String s) {
		try {
			fw.append(s);
			// fw.append("\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(s);
	}

}
