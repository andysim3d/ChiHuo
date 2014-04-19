package edu.stevens.cs562;

import java.util.ArrayList;

import Pengfei.Zhang.sBean;

public class Parameters {
	private ArrayList<sBean> S;
	private int N;
	private ArrayList<String> V;
	private ArrayList<String> F;
	private ArrayList<String> Sigma;

	String s="cust, prod, max_0_quant, avg_0_quant, min_0_quant";
	String v="cust, prod";
	String f="max_0_quant, avg_0_quant, min_0_quant";
	String n="0";

	public Parameters(){
		S=new ArrayList<sBean>();
		N=0;
		V=new ArrayList<String>();
		F=new ArrayList<String>();
		Sigma=new ArrayList<String>();
	}
	
	class SS{
		String Sname;
		String Stype;
	}
	
	public ArrayList<sBean> getS() {
		return S;
	}

	public void setS(ArrayList<sBean> s) {
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
