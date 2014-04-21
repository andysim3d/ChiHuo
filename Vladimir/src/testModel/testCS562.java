package testModel;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import Pengfei.Zhang.GenerateNewJAVA;
import Pengfei.Zhang.MF_Structure;
import Pengfei.Zhang.AggrateFunction;
import Pengfei.Zhang.ParaseMF;
import Pengfei.Zhang.ParaseParameters;
import SiyuanPeng.Util;

import org.junit.Test;

import edu.stevens.cs562.Parameters;

public class testCS562 {

	private SiyuanPeng_program.Parameters pa ;
	
//	public testCS562(){

//		pa = ParaseParameters.ParaseInput();
//	}
	
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
		//pa = ParaseParameters.ParaseInput();
		//MF_Structure mf = ParaseMF.mfParase(pa);
		//Util ut = new Util();
		//ut.UtilGenerator();
		GenerateNewJAVA g = new GenerateNewJAVA();
		g.main(null);
		//ArrayList<AggrateFunction> aggrateFuntion = new ArrayList<>();
		ArrayList<String> groupingAttribute = new ArrayList<>();
		AggrateFunction s = new AggrateFunction();
		s.func = "sum";
		s.funcColum = "quant";
		s.Label = "1";
		s.funcName = "sum(quant)";
		//aggrateFuntion.add("sum(quant)");
		//assertTrue(mf.aggrate_function.get(0).equals(s));
		//assertTrue(mf.grouping_attributes.equals(Arrays.asList("cust")));
	}
}
