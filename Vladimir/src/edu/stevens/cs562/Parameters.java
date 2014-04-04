package edu.stevens.cs562;

import java.util.ArrayList;

public class Parameters {
	private ArrayList<String> S;
	private int N;
	private ArrayList<String> V;
	private ArrayList<String> F;
	private ArrayList<String> Sigma;

	String s="cust, prod, avg_0_quant, max_0_quant";
	String v="cust, prod";
	String f="avg_0_quant, max_0_quant";
	String n="0";

	public Parameters(){
		S=new ArrayList<String>();
		N=0;
		V=new ArrayList<String>();
		F=new ArrayList<String>();
		Sigma=new ArrayList<String>();
	}

	public void parse(){

		if(s.contains(",")){
			String[] parseS=s.split(", ");
			for (int i = 0; i < parseS.length; i++) {
				if(parseS[i].startsWith("avg")){
					S.add(parseS[i].replace("avg", "sum"));
					S.add(parseS[i].replace("avg", "count"));
				}else
				S.add(parseS[i]);
			}
		}else{
			S.add(s);
		}

		N=Integer.parseInt(n);

		if(v.contains(",")){
			String[] parseV=v.split(", ");
			for (int i = 0; i < parseV.length; i++) {
				V.add(parseV[i]);
			}
		}else{
			V.add(s);
		}

		if(f.contains(",")){
			String[] parseF=f.split(", ");
			for (int i = 0; i < parseF.length; i++) {
				F.add(parseF[i]);
			}
		}else{
			F.add(s);
		}

	}



	public ArrayList<String> getS() {
		return S;
	}

	public void setS(ArrayList<String> s) {
		S = s;
	}

	public int getN() {
		return N;
	}

	public void setN(int n) {
		N = n;
	}

	public ArrayList<String> getV() {
		return V;
	}

	public void setV(ArrayList<String> v) {
		V = v;
	}

	public ArrayList<String> getF() {
		return F;
	}

	public void setF(ArrayList<String> f) {
		F = f;
	}

	public ArrayList<String> getSigma() {
		return Sigma;
	}

	public void setSigma(ArrayList<String> sigma) {
		Sigma = sigma;
	}

}
