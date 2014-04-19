package Pengfei.Zhang;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import SiyuanPeng.Util;
import edu.stevens.cs562.Parameters;

public class ParaseParameters {
	public static Parameters ParaseInput(){
	    try {
			Parameters temp = new Parameters();
			System.out.println("SELECT ATTRIBUTE(S):");
		    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));  
			String s = br.readLine();
			
			Util ut = new Util();
			ut.UtilGenerator();

			String[] name = null;// = //new ArrayList<>();
			try{
				name = (s.split(", "));
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
			temp.setN(Integer.parseInt(s));
			
			System.out.println("GROUPING ATTRIBUTES(V):");
			s = br.readLine();
			temp.setV(new ArrayList<String>(Arrays.asList(s.split(", "))));
			
			System.out.println("F-VECT([F]):");
			s = br.readLine();
			temp.setF(new ArrayList<String>(Arrays.asList(s.split(", "))));
			
			System.out.println("SELECT CONDITION-VECT([]):");
			ArrayList<String> sigma = new ArrayList<>();
			for(int j = 0; j < temp.getN(); j ++){
				s = br.readLine();
				sigma.add(s);
			}
			temp.setSigma(sigma);
			return temp;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return new Parameters();
	}
}
