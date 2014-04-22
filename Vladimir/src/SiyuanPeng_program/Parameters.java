package SiyuanPeng_program;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import Pengfei.Zhang.sBean;

public class Parameters {
	private ArrayList<sBean> S=new ArrayList<>();//just be used for print
	private int N=0;//be used for determining the times of loop
	private ArrayList<String> V=new ArrayList<>();//be used for group by 
	private ArrayList<sBean> F=new ArrayList<>();
	private ArrayList<String> Sigma=new ArrayList<>();
	
	private Set<sBean> beanset=new HashSet<>();//be used for creating bean and avgFix

//	String s="cust, prod, avg_1_quant, max_2_quant";
//	String n="2";
//	String v="cust, prod";
//	String f="avg_1_quant, max_2_quant";
//	String sigma="1.month<avg_0_quant";

	String s="cust, max_0_quant, sum_1_quant, sum_2_quant, count_3_, avg_3_quant";
	String n="3";
	String v="cust";
	String f="sum_1_quant, sum_2_quant, sum_3_quant, count_3_";
	String sigma="_1_state=NY, _2_state=NJ, _3_state=NJ";
/*	
	public void parse(){
		if(s.contains(",")){
			String[] parseS=s.split(", ");
			for (int i = 0; i < parseS.length; i++) {
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
			V.add(v);
		}

		if(f.contains(",")){
			String[] parseF=f.split(", ");
			for (int i = 0; i < parseF.length; i++) {
				F.add(parseF[i]);
			}
		}else{
			F.add(f);
		}

		if(sigma.contains(",")){
			String[] parseSigma=sigma.split(", ");
			for (int i = 0; i < parseSigma.length; i++) {
				Sigma.add(parseSigma[i]);
			}		
		}else{
			Sigma.add(sigma);
		}
		
		//put S and F to beanset in order to create bean
		for (int i = 0; i < S.size(); i++) {
			if(S.get(i).startsWith("avg")){
				beanset.add(S.get(i));
				beanset.add(S.get(i).replace("avg", "sumforavg"));
				beanset.add(S.get(i).replace("avg", "countforsum"));
			}else
			beanset.add(S.get(i));
		}
		for (int i = 0; i < F.size(); i++) {
			if(F.get(i).startsWith("avg")){
				beanset.add(F.get(i));
				beanset.add(F.get(i).replace("avg", "sumforavg"));
				beanset.add(F.get(i).replace("avg", "countforsum"));
			}else
			beanset.add(F.get(i));
		}
	}
*/
	
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
