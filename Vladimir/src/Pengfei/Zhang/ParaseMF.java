package Pengfei.Zhang;

import java.util.ArrayList;
import java.util.Arrays;

import edu.stevens.cs562.Parameters;

public class ParaseMF {
	public static MF_Structure mfParase(Parameters pa){
		MF_Structure temp = new MF_Structure();
		//have n tuples
		//MF_tuple[] temp_tuples = new MF_tuple[pa.getN()];
		ArrayList<MF_tuple> temp_tuples = new ArrayList<>();
		try{
			for (int i = 0; i < pa.getN(); i++) {
				MF_tuple t = new MF_tuple();
				//temp_tuples[i].grouping_variable = (String)pa.getV().get(0);
				String tem = pa.getV().get(0);
				t.grouping_variable = tem;
				String [] sp = pa.getF().get(i).split("_");
				t.aggregate_function = sp[1]+"("+sp[2]+")";
				temp_tuples.add(t);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		temp.tuples.addAll(temp_tuples);
		return temp;	
	}
}
