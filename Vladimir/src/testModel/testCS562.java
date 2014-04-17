package testModel;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import Pengfei.Zhang.MF_Structure;
import Pengfei.Zhang.MF_tuple;
import Pengfei.Zhang.ParaseMF;
import Pengfei.Zhang.ParaseParameters;
import edu.stevens.cs562.Parameters;

import org.junit.Test;

public class testCS562 {

	private Parameters pa ;
	
	public testCS562(){

		pa = ParaseParameters.ParaseInput();
	}
	
	/*@Test
	public void TestInputParase(){

		ArrayList<String>s = new ArrayList<>();
		s.add("cust");
		s.add("1_sum_quant");
		s.add("2_sum_quant");
		s.add("3_sum_quant");
		assertTrue(pa.getS().equals(s));
		assertTrue(pa.getN() == 3);
		String[] F = {"1_sum_quant","2_sum_quant","3_sum_quant"};
		assertTrue(pa.getF().equals(Arrays.asList(F)));
		assertTrue(pa.getV().equals(Arrays.asList("cust")));
		String[]sigma = {"1.state='NY'","2.state='NJ'","3.state='CT'"};
		//assertTrue(pa.getSigma().equals(Arrays.asList(sigma)));
	}//*/
	@Test
	public void TestParase(){
		MF_Structure mf = ParaseMF.mfParase(pa);
		Pengfei.Zhang.MF_tuple[] m= new Pengfei.Zhang.MF_tuple[mf.tuples.size()];
		m[0] = new MF_tuple();
		m[0].aggregate_function = "sum(quant)";
		m[0].grouping_variable = "cust";
		m[1] = new MF_tuple();
		m[2] = new MF_tuple();
		m[1].grouping_variable = "cust";
		m[2].grouping_variable = "cust";
		m[1].aggregate_function = "sum(quant)";
		m[2].aggregate_function = "sum(quant)";
		ArrayList<Pengfei.Zhang.MF_tuple> l = new ArrayList<>();
		l.addAll(Arrays.asList(m));
		System.out.println(mf.tuples.size());
		assertTrue(m[0].equals(mf.tuples.get(0)));
	}
}
