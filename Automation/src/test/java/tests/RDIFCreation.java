package tests;

import java.awt.AWTException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.Random;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.HTMLReporter;
import com.relevantcodes.extentreports.LogStatus;

import pageFactory.CreateRDIFPageElements;
import pageFactory.HomePageElements;
import pageFactory.LoginPageElements;
import pageFactory.SuccessPageElements;
import pageFactory.TaskPageElements;
import random.RandomDataInput;
import utils.Utilities;

public class RDIFCreation extends BaseClass {
	
	LoginPageElements objLogin;
	HomePageElements objHome;
	CreateRDIFPageElements objRDIF;
	SuccessPageElements objSuccessPage;
	TaskPageElements objTaskPage;
	WebDriverWait wait;
	JavascriptExecutor je;
	Utilities util;
	String char250, char4k, devTitle;
	String currentDirectory = System.getProperty("user.dir");
	static String file_location = ".\\createRDIF.xlsx";
	File screenshotFolder = new File(".\\screenshots");
	Duration waitDuration = Duration.ofSeconds(10);
	Random random = new Random();
	int x = random.nextInt(10000);
	String id = String.valueOf(x);
	RandomDataInput randomInput;
	Actions action;
	Logger log;
	HTMLReporter reporter;
	ExtentTest logger;
	ITestResult result;
	String finalSCPath,screenshotPath;

	@BeforeMethod
	public void beforeTest() throws IOException, AWTException, InterruptedException {
		// char250=properties.getProperty("character250");
		// char4k=properties.getProperty("character4000");
		
		String screenshotPath = ".\\screenshots\\" + projectName;
		File theDir = new File(screenshotPath);
		if (!theDir.exists()) {
			theDir.mkdirs();
		}
		
		this.driver = getDriver();
		
		objLogin = new LoginPageElements(driver);
		objHome = new HomePageElements(driver);
		objRDIF = new CreateRDIFPageElements(driver);
		objTaskPage = new TaskPageElements(driver);
		util = new Utilities(driver);
		objSuccessPage = new SuccessPageElements(driver);
		randomInput = new RandomDataInput(driver);
		je = (JavascriptExecutor) driver;
		action = new Actions(driver);
		
		FileInputStream in = new FileInputStream(util.getPropertyFileLocation());
		properties = new Properties();
		properties.load(in);
		in.close();
	}

	@Test
	public void createProjectRDIF() throws Exception {
		extent = getExtent();
		devTitle = getProjectName();
		logger = extent.startTest("Create RDIF Project: "+devTitle);

		// Login Proponent
		driver.get(properties.getProperty("url_proponent"));
		String userName = properties.getProperty("proponentUser");
		String password = properties.getProperty("password");
		objLogin.login(userName, password);
		logger.log(LogStatus.PASS, "Login Successful with Username: "+userName+", Password: "+password);

		objHome.clickCreateProjectBtn();

		logger.log(LogStatus.PASS, "Clicked Create Project Button");

		// page 1 - Development Details
		
		String setRegion = randomInput.getRandomRegion();
		objRDIF.selectRegion(setRegion);
		logger.log(LogStatus.PASS,"Region selected: "+setRegion);
		
		String setSector =  randomInput.getRandomSector();
		objRDIF.selectSector(setSector);
		logger.log(LogStatus.PASS,"Sector Selected: "+setSector);
		
		objRDIF.setDevTitle(devTitle);
		logger.log(LogStatus.PASS,"Development Title set: "+devTitle);
		
		objRDIF.addPropKeyContactDetails("p1", "p1@abc.com");
		objRDIF.addSusDevContactDetails("s1", "s1@abc.com");
		logger.log(LogStatus.PASS,"Proponent Key and Sus Dev Contact filled");
		
		objRDIF.setScopeDefinition("some random text");
		logger.log(LogStatus.PASS,"Scope Definition set");
		
		objRDIF.setInsertFigure();
		util.takeSnapShot();
		Thread.sleep(1000);
		logger.log(LogStatus.PASS,"Image uploaded");
		
		objRDIF.setParagraphQuantityFields("some random text");
		util.takeSnapShot();
		logger.log(LogStatus.PASS,"Paragraph fields set");
		
		objRDIF.selectKeyDates();
		logger.log(LogStatus.PASS,"Dates selected");
		
		objRDIF.setRelatedDevelopment("some random text");
		util.takeSnapShot();
		logger.log(LogStatus.PASS,"Related Development set");

		objRDIF.clickNextBtn();
		Thread.sleep(3000);
		logger.log(LogStatus.PASS,"Clicked Next Button");
	
		logger.log(LogStatus.PASS, "Page 1 Filled");

		// page 2 - Development Context & Categorization

		objRDIF.setStrategicAndPlanningContext("some random text");
		//String projectTypeRandom = randomInput.getRandomProjectType();
		//objRDIF.setProjectType(projectTypeRandom);
		objRDIF.setProjectType("Asset");
		util.takeSnapShot();

		objRDIF.setProjectTypology();
		util.takeSnapShot();

		objRDIF.clickNextBtn();
		Thread.sleep(4000);

		logger.log(LogStatus.PASS, "Page 2 Filled");

		// page 3 - Environmental Assessment & Approvals
		
		String cat = properties.getProperty("category");

		objRDIF.setAssignmentTimeframe("PAA name", cat, "Master ESMP and SEA","12/15/2022", "Comments added");
		util.takeSnapShot();
		
		objRDIF.setUpload();
		util.takeSnapShot();
		
		objRDIF.setRemainTextArea();
		Thread.sleep(2000);

		objRDIF.clickSubmit();

		// objRDIF.clickCancelBtn();
		Thread.sleep(2000);
		util.takeSnapShot();
		Thread.sleep(2000);

		logger.log(LogStatus.PASS, "Page 3 Filled");

		// Validate RDIF created
		objSuccessPage.validateRDIFCreationCompleted(devTitle);

		logger.log(LogStatus.PASS, "RDIF Submitted = " + devTitle);

	}
	
	@AfterMethod
	public void getResult(ITestResult result) throws Exception {
		String screenshotPath;
		if (result.getStatus() == ITestResult.FAILURE) {
			logger.log(LogStatus.FAIL, "Test Case Failed is " + result.getName());
			logger.log(LogStatus.FAIL, "Test Case Failed is " + result.getThrowable());
			screenshotPath = util.takeSnapShot();
			logger.log(LogStatus.FAIL, logger.addScreenCapture(screenshotPath));
		} else if (result.getStatus() == ITestResult.SKIP) {
			logger.log(LogStatus.SKIP, "Test Case Skipped is " + result.getName());
			screenshotPath = util.takeSnapShot();
			logger.log(LogStatus.SKIP, logger.addScreenCapture(screenshotPath));
		}
		driver.quit();
	}
	
}
