import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;

public class testrun {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for (int i = 0; i < 3; i++) {
			List<String> suites = new ArrayList<String>();
			suites.add("testng.xml"); 

			TestNG tng = new TestNG();
			tng.setTestSuites(suites);

			tng.run(); // run test suite
		}
	}

}
