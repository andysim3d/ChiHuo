package SiyuanPeng_program;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import Siyuan.Zheng.sBean;

public class Parameters {
	private ArrayList<sBean> S=new ArrayList<>();//just be used for print
	private int N=0;//be used for determining the times of loop
	private ArrayList<String> V=new ArrayList<>();//be used for group by 
	private ArrayList<sBean> F=new ArrayList<>();
	private ArrayList<String> Sigma=new ArrayList<>();
	private boolean EMF = true;
	private Set<sBean> beanset=new HashSet<>();//be used for creating bean and avgFix

	
	public boolean getEMF(){
		return this.EMF;
	}
	public void setEMF(boolean emf){
		this.EMF = emf;
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

	public ArrayList<sBean> getF() {
		return F;
	}

	public void setF(ArrayList<sBean> f) {
		F = f;
	}

	public ArrayList<String> getSigma() {
		return Sigma;
	}

	public void setSigma(ArrayList<String> sigma) {
		Sigma = sigma;
	}

	public Set<sBean> getBeanset() {
		return beanset;
	}

	public void setBeanset(Set<sBean> beanset) {
		this.beanset = beanset;
	}
}
