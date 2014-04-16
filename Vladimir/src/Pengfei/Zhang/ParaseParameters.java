package Pengfei.Zhang;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import edu.stevens.cs562.Parameters;

public class ParaseParameters {
	public static Parameters ParaseInput(){
	    try {
			Parameters temp = new Parameters();
			System.out.println("SELECT ATTRIBUTE(S):");
		    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));  
			String s = br.readLine();
			temp.setS(new ArrayList<String>(Arrays.asList(s.split(", "))));
			
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
			s = br.readLine();
			ArrayList<String> sigma = new ArrayList<>();
			for(int j = 0; j < temp.getN() - 1; j ++){
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
