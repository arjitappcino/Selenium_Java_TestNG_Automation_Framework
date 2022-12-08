package tests;

import java.awt.AWTException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
	Properties properties;
	RandomDataInput randomInput;
	Actions action;
	Logger log;
	HTMLReporter reporter;
	ExtentTest logger;

	@BeforeMethod
	public void beforeTest() throws IOException, AWTException, InterruptedException {
		// char250=properties.getProperty("character250");
		// char4k=properties.getProperty("character4000");
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

	public static String getScreenshot(WebDriver driver, String screenshotName) throws Exception {
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir") + "/FailedTestsScreenshots/" + screenshotName + dateName
				+ ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}

	@Test
	public void createProjectRDIF() throws Exception {
		extent = getExtent();
		logger = extent.startTest("Create RDIF Project");

		// Login Proponent
		driver.get(properties.getProperty("url_proponent"));
		String userName = properties.getProperty("proponentUser");
		String password = properties.getProperty("password");
		objLogin.login(userName, password);
		logger.log(LogStatus.PASS, "Login Successful");

		objHome.clickCreateProjectBtn();

		logger.log(LogStatus.PASS, "Click create Project Button");

		// page 1 - Development Details

		objRDIF.selectRegion(randomInput.getRandomRegion());
		objRDIF.selectSector(randomInput.getRandomSector());
		devTitle = "NEOM ATMN ID " + id;
		objRDIF.setDevTitle(devTitle);
		objRDIF.addPropKeyContactDetails("p1", "p1@abc.com");
		objRDIF.addSusDevContactDetails("s1", "s1@abc.com");
		objRDIF.setScopeDefinition("some random text");
		util.takeSnapShot();

		objRDIF.setParagraphQuantityFields("some random text");
		util.takeSnapShot();

		objRDIF.selectKeyDates();
		objRDIF.setRelatedDevelopment("some random text");
		util.takeSnapShot();

		objRDIF.clickNextBtn();
		Thread.sleep(3000);

		logger.log(LogStatus.PASS, "Page 1 Filled");

		// page 2 - Development Context & Categorization

		objRDIF.setStrategicAndPlanningContext("some random text");
		String projectTypeRandom = randomInput.getRandomProjectType();
		objRDIF.setProjectType(projectTypeRandom);
		util.takeSnapShot();

		objRDIF.setProjectTypology();
		util.takeSnapShot();

		objRDIF.clickNextBtn();
		Thread.sleep(4000);

		logger.log(LogStatus.PASS, "Page 2 Filled");

		// page 3 - Environmental Assessment & Approvals

		objRDIF.setAssignmentTimeframe("PAA name", randomInput.getRandomCategoryType(), "Master ESMP and SEA",
				"12/15/2022", "Comments added");
		util.takeSnapShot();

		objRDIF.clickSubmit();

		// objRDIF.clickCancelBtn();
		Thread.sleep(2000);
		util.takeSnapShot();
		Thread.sleep(2000);

		logger.log(LogStatus.PASS, "Page 3 Filled");

		// Validate RDIF created
		objSuccessPage.validateRDIFCreationCompleted(devTitle);

		logger.log(LogStatus.PASS, "RDIF Submitted = " + devTitle);

		FileOutputStream out = new FileOutputStream(util.getPropertyFileLocation());
		properties.setProperty("currentProject", devTitle);
		properties.store(out, null);
		out.close();

	}

	// @Test
	public void taskRDIFReviewATR() throws Exception {

		logger = extent.startTest("Review and Accept RDIF");

		String taskName = properties.getProperty("TASK_RDIF_REVIEW_ATR");

		String userName = properties.getProperty("assRepUser");
		String password = properties.getProperty("password");

		objLogin.login(userName, password);
		Thread.sleep(2000);

		objHome.clickTaskBtn();

		WebElement task = util.fetchTask(taskName, this.devTitle);
		task.click();
		Thread.sleep(2000);
		util.takeSnapShot();
		logger.log(LogStatus.PASS, "RDIF Submitted");

	}

//	@AfterMethod
//	public void getResult(ITestResult result) throws Exception {
//		if (result.getStatus() == ITestResult.FAILURE) {
//			logger.log(LogStatus.FAIL, "Test Case Failed is " + result.getName());
//			logger.log(LogStatus.FAIL, "Test Case Failed is " + result.getThrowable());
//			String screenshotPath = NEOMBusinessFlow.getScreenshot(driver, result.getName());
//			// To add it in the extent report
//			logger.log(LogStatus.FAIL, logger.addScreenCapture(screenshotPath));
//		} else if (result.getStatus() == ITestResult.SKIP) {
//			logger.log(LogStatus.SKIP, "Test Case Skipped is " + result.getName());
//			String screenshotPath = NEOMBusinessFlow.getScreenshot(driver, result.getName());
//			logger.log(LogStatus.SKIP, logger.addScreenCapture(screenshotPath));
//		}else if (result.getStatus() == ITestResult.SUCCESS) {
//			logger.log(LogStatus.PASS, "Test Case Passes is " + result.getName());
//			String screenshotPath = NEOMBusinessFlow.getScreenshot(driver, result.getName());
//			logger.log(LogStatus.PASS, logger.addScreenCapture(screenshotPath));
//		}
//		// ending test
//		// endTest(logger) : It ends the current test and prepares to create HTML report
//		extent.endTest(logger);
//	}

	@AfterMethod
	public void endTest() {
		driver.close();
	}

//	@DataProvider
//	public Object[][] registerData() throws Exception {
//
//		Object[][] testObjArray = DataFetch.readExcel(file_location, SheetName);
//
//		return (testObjArray);
//
//	}
}
