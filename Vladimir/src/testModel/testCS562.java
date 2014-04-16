package testModel;

import static org.junit.Assert.*;
import Pengfei.Zhang.ParaseParameters;
import edu.stevens.cs562.Parameters;
import org.junit.Test;

public class testCS562 {

	@Test
	public void test() {
		//fail("Not yet implemented");
	}

	@Test
	public void TestInputParase(){
		Parameters pa = ParaseParameters.ParaseInput();
		assertTrue(pa.getN() == 3);
	}
	
}
