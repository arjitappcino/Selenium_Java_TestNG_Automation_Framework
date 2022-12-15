import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.TestNG;

public class CreateRDIFProject {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
			List<String> suites = new ArrayList<String>();
			suites.add(".\\src\\test\\java\\createRDIF.xml");
			TestNG tng = new TestNG();
			tng.setTestSuites(suites);
			tng.run(); // run test suite
	}

}
