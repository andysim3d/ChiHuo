package SiyuanPeng;

import java.io.BufferedReader;

import Pengfei.Zhang.GenerateNewJAVA;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import Siyuan.Zheng.Parameters;
import Siyuan.Zheng.sBean;

public class ParseParameters {
	public static Parameters ParaseInput(){
	    try {
			Parameters temp = new Parameters();
			System.out.println("SELECT ATTRIBUTE(S):");
		    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));  
			String s = br.readLine();
			s = s.replace(" ", "");
			UtilInfoSchema ut = new UtilInfoSchema();
			ut.UtilGenerator();

			String[] name = null;// = //new ArrayList<>();
			try{
				name = (s.split(","));
			}
			catch(Exception e){
				e.printStackTrace();
			}
			ArrayList<sBean> sb = new ArrayList<>();
			
			for(int i = 0 ; i < name.length; i ++){
				sBean t = new sBean();
				//if it contains *, that must be integer
				if(name[i].contains("_*")){
					t.name = name[i].replace("_*", "_ALL");
					t.type = "integer";
				}
				else if(GenerateNewJAVA.isOperator(name[i]))
				{
					//if we have that column, 
					String [] columnName = name[i].split("_|\\+|-|\\*|/");
					if(ut.list.containsKey(columnName[2])){
					t.name = name[i];
					t.type = "integer";
					}
					else{
						//if we do not have this column
						t.name = name[i];
						t.type = "null";
					}
				}
				else{
					if (name[i].contains("_")) {
						String [] columnName = name[i].split("_");
						t.name = name[i];
						//if name can be split into 2 parts, means it's not a aggreate function
						if(columnName.length == 2){
							t.name = name[i] + "$";
							try{
							t.type = ut.list.get(columnName[1]);
							}
							catch(Exception e){
								t.type = "Null";
							}
						}
						//if contains, set to it's type
						else if(ut.list.containsKey(columnName[2]))
						{
							t.type = ut.list.get(columnName[2]);
						}
						else{
							t.type = "null";
						}
					}
					else{
						t.name = name[i];
						if(ut.list.containsKey(name[i])){
						t.type = ut.list.get(name[i]);
						}
						else
						{
							t.type = "null";
						}
					}
				}
				sb.add(t);
			}
			
			temp.setS(sb);
			
			System.out.println("NUMBER OF GROUPING VARIABLES(n):");
			s = br.readLine();
			s = s.replace(" ", "");
			temp.setN(Integer.parseInt(s));
			
			System.out.println("GROUPING ATTRIBUTES(V):");
			s = br.readLine();
			s = s.replace(" ", "");
			temp.setV(new ArrayList<String>(Arrays.asList(s.split(","))));
			
			System.out.println("F-VECT([F]):");
			s = br.readLine();
			s = s.replace(" ", "");
			
			//grouping variables
			try {
				ArrayList<sBean> tp = new ArrayList<>();
				String [] f = s.split(",");
				for (int i = 0; i < f.length; i++) {
					sBean t = new sBean();
					String [] f0 = f[i].split("_");
					if(f[i].contains("_*")){
						f[i] = f[i].replace("_*", "_ALL");
					}
					t.name = f[i];
					t.type = ut.list.get(f0[2]);
					tp.add(t);
				}
				temp.setF(tp);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			
			//temp.setF(new ArrayList<String>(Arrays.asList(s.split(", "))));
			
			System.out.println("SELECT CONDITION-VECT([]):");
			ArrayList<String> sigma = new ArrayList<>();
			s = br.readLine();
			while(! (s.equals(""))){
				s = s.replace(" ", "");
				sigma.add(s);
				s = br.readLine();
			}
			temp.setSigma(sigma);

			//TreeSet<sBean> set = new TreeSet<>();
		    Hashtable<String, sBean> ta = new Hashtable<>();
		    
			for (sBean smb : temp.getF()) {
				if(smb.name.contains("_*")){
					ta.put(smb.name, smb);
				}
				else if(GenerateNewJAVA.isOperator(smb.name)){
					;
				}
				else{
					ta.put(smb.name, smb);
				}
			}
			for (sBean smb : temp.getS()) {
				if(smb.name.contains("_*")){
					ta.put(smb.name, smb);
				}
				else if(GenerateNewJAVA.isOperator(smb.name)){
					;
				}
				else{
					ta.put(smb.name, smb);
				}
			}
			
			Set<sBean> bens = new HashSet<>();
			for (String key : ta.keySet()) {
				bens.add(ta.get(key));
			}
			
		    //set.
		    temp.setBeanset(bens);
			return temp;//temp.setBeanset(set);;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    
	    return new Parameters();
	}
}
