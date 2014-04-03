package SiyuanPeng;

import java.util.HashMap;
import java.util.List;

public class TestClass {
	public static void main(String[] args) {
		Util util=new Util();
		util.mfStructureGenerator();
		List<HashMap<String, String>> tuple=util.mfTable();
//		util.groupBy(list, "email");
//		for (HashMap<String, String> hashMap : tuple) {
//			System.out.println(hashMap.get("prod"));
//		}
	}

	
}

