package Pengfei.Zhang;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import SiyuanPeng.Util;
import SiyuanPeng_program.Parameters;

public class ParaseParameters {
	public static Parameters ParaseInput(){
	    try {
			Parameters temp = new Parameters();
			System.out.println("SELECT ATTRIBUTE(S):");
		    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));  
			String s = br.readLine();
			s = s.replace(" ", "");
			Util ut = new Util();
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
				
				if (name[i].contains("_")) {
					String [] st = name[i].split("_");
					t.name = name[i];
					t.type = ut.list.get(st[2]);
				}
				else{
					t.name = name[i];
					t.type = ut.list.get(name[i]);
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
			
			
			try {
				ArrayList<sBean> tp = new ArrayList<>();
				String [] f = s.split(",");
				for (int i = 0; i < f.length; i++) {
					sBean t = new sBean();
					String [] f0 = f[i].split("_");
					t.name = f[i];
					t.type = ut.list.get(f0[2]);
					tp.add(t);
				}
				temp.setF(tp);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			
			
			//temp.setF(new ArrayList<String>(Arrays.asList(s.split(", "))));
			
			System.out.println("SELECT CONDITION-VECT([]):");
			ArrayList<String> sigma = new ArrayList<>();
			for(int j = 0; j < temp.getN(); j ++){
				s = br.readLine();
				s = s.replace(" ", "");
				sigma.add(s);
			}
			temp.setSigma(sigma);

			//TreeSet<sBean> set = new TreeSet<>();
		    Hashtable<String, sBean> ta = new Hashtable<>();
		    
			for (sBean smb : temp.getF()) {
				ta.put(smb.name, smb);
			}
			for (sBean smb : temp.getS()) {
				ta.put(smb.name, smb);
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
