package Pengfei.Zhang;

import java.util.ArrayList;
import java.util.Arrays;


public class ParaseMF {
	public static MF_Structure mfParase(SiyuanPeng_program.Parameters pa){
		MF_Structure temp = new MF_Structure();
		//have n tuples
		//MF_tuple[] temp_tuples = new MF_tuple[pa.getN()];
		ArrayList<AggrateFunction> temp_tuples = new ArrayList<>();
		temp.grouping_attributes = pa.getV();
		try{
			for (sBean k : pa.getF()) {
				
				
				AggrateFunction t = new AggrateFunction();
				//temp_tuples[i].grouping_variable = (String)pa.getV().get(0);
				
				String [] sp = k.name.split("_");
				t.funcName = sp[1]+"("+sp[2]+")";
				t.Label = sp[0];
				t.func = sp[1];
				t.funcColum = sp[2];
				temp_tuples.add(t);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		temp.aggrate_function.addAll(temp_tuples);
		return temp;	
	}
}
