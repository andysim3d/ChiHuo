package SiyuanPeng_EMF;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Parameters {
	private ArrayList<String> S=new ArrayList<>();//just be used for print
	private int N=0;//be used for determining the times of loop
	private ArrayList<String> V=new ArrayList<>();//be used for group by 
	private ArrayList<String> F=new ArrayList<>();
	private ArrayList<String> Sigma=new ArrayList<>();
	
	private Set<String> beanset=new HashSet<>();//be used for creating bean and avgFix

//	String s="cust, prod, avg_1_quant, max_2_quant";
//	String n="2";
//	String v="cust, prod";
//	String f="avg_1_quant, max_2_quant";
//	String sigma="1.month<avg_0_quant";

	String s="prod, month, _1_month, _2_month, _3_month, count_3_, _4_state";
	String n="4";
	String v="prod, month";
	String f="avg_1_quant, avg_2_quant";
	String sigma="_1_prod=prod, _1_month=month-1, _2_prod=prod, _2_month=month+1, _3_prod=prod, _3_month=month, _3_quant>avg_1_quant, _3_quant<avg_2_quant";
//	
//	String s="cust, prod, count_0_, count_1_, count_2_";
//	String n="2";
//	String v="cust, prod";
//	String f="count_0_, count_1_, count_2_";
//	String sigma="_1_cust=cust, _1_prod=prod, _2_cust!=cust, _2_prod=prod";
//	
//	String s="cust, count_0_, count_1_, count_2_";
//	String n="2";
//	String v="cust";
//	String f="count_0_, count_1_, count_2_";
//	String sigma="_1_cust=cust, _2_cust!=cust";
	
//	String s="cust, avg_1_quant, _1_state, avg_2_quant, _2_state, avg_3_quant, _3_state";
//	String n="3";
//	String v="cust";
//	String f="avg_1_quant, avg_2_quant, avg_3_quant";
//	String sigma="_1_cust=cust, _1_state=NY, _2_cust=cust, _2_state=NJ, _3_cust=cust, _3_state=CT";
	
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
				beanset.add(S.get(i).replace("avg", "countforavg"));
			}else
			beanset.add(S.get(i));
		}
		for (int i = 0; i < F.size(); i++) {
			if(F.get(i).startsWith("avg")){
				beanset.add(F.get(i));
				beanset.add(F.get(i).replace("avg", "sumforavg"));
				beanset.add(F.get(i).replace("avg", "countforavg"));
			}else
			beanset.add(F.get(i));
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

	public Set<String> getBeanset() {
		return beanset;
	}

	public void setBeanset(Set<String> beanset) {
		this.beanset = beanset;
	}

}
