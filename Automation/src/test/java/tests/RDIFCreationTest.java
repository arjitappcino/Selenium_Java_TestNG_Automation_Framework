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
import pageFactory.TaskPageElements;
import random.RandomDataInput;
import utils.Utilities;

public class RDIFCreationTest extends BaseClass {

	LoginPageElements objLogin;
	HomePageElements objHome;
	CreateRDIFPageElements objRDIF;
	TaskPageElements objTaskPage;
	WebDriverWait wait;
	JavascriptExecutor je;
	Utilities util;
	String char250, char4k, devTitle;
	String currentDirectory = System.getProperty("user.dir");
	static String file_location = ".\\createRDIF.xlsx";
	File screenshotFolder = new File(".\\screenshots");
	Duration waitDuration = Duration.ofSeconds(60);
	Random random = new Random();
	int x = random.nextInt(10000);
	String id = String.valueOf(x);
	RandomDataInput randomInput;
	Actions action;
	Logger log;
	HTMLReporter reporter;
	ExtentTest logger;
	ITestResult result;
	String finalSCPath, screenshotPath;
	boolean starEntry;

	@BeforeMethod
	public void beforeTest() throws IOException, AWTException, InterruptedException {
		// char250=properties.getProperty("character250");
		// char4k=properties.getProperty("character4000");
		System.out.println("project name going in beforetest " + projectName);
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
		randomInput = new RandomDataInput(driver);
		je = (JavascriptExecutor) driver;
		action = new Actions(driver);

		FileInputStream in = new FileInputStream(util.getConfigPropertyLocation());
		properties = new Properties();
		properties.load(in);
		in.close();
	}

	@Test
	public void createProjectRDIF() throws Exception {
		extent = getExtent();
		devTitle = projectName;
		logger = extent.startTest("Create RDIF Project: " + devTitle);

		System.out.println("Creating New Project: " + devTitle);

		String userName, password;

		driver.get(properties.getProperty("url_original_proponent"));
		userName = properties.getProperty("proponentUser");
		password = properties.getProperty("password");

		objLogin.login(userName, password);
		logger.log(LogStatus.PASS, "Login Successful with Username: " + userName + ", Password: " + password);

		objHome.clickCreateProjectBtn();

		logger.log(LogStatus.PASS, "Clicked Create Project Button");
		Thread.sleep(4000);

		objTaskPage.checkPageProgress("Development Details", "In-Progress");
		objTaskPage.checkPageProgress("Additional Information", "Not-started");
		objTaskPage.checkPageProgress("Development Context & Categorization", "Not-started");
		// page 1 - Development Details

		String setRegion = randomInput.getRandomRegion();
		// objRDIF.selectRegion(setRegion);

		objRDIF.selectRegion(regionSelected);
		// objRDIF.selectRegion("Mobility");

		String setSector = randomInput.getRandomSector();
		objRDIF.selectSector(setSector);

		objRDIF.setDevTitle(devTitle);

		if (environmentSelected.contains("Dev")) {
			objRDIF.addPropKeyContactDetails("arjityada");
			objRDIF.addSusDevContactDetails("Salil");
		} else {
			objRDIF.addPropKeyContactDetails("JhonDo");
			objRDIF.addSusDevContactDetails("Henr");
		}

		logger.log(LogStatus.PASS, "Region selected: " + setRegion + " | Sector Selected: " + setSector
				+ " | Development Title set: " + devTitle + logger.addScreenCapture(util.captureFinalScreenshot()));

		objRDIF.setScopeDefinition();

		objRDIF.setInsertFigure();
		util.takeSnapShot();
		Thread.sleep(1000);

		logger.log(LogStatus.PASS,
				"Scope Definition and Image set" + logger.addScreenCapture(util.captureFinalScreenshot()));

		objRDIF.clickNextBtn();
		Thread.sleep(3000);

		objTaskPage.checkPageProgress("Development Details", "Completed");
		objTaskPage.checkPageProgress("Additional Information", "In-Progress");
		objTaskPage.checkPageProgress("Development Context & Categorization", "Not-started");

		objRDIF.setParagraphQuantityFields();
		util.takeSnapShot();
		logger.log(LogStatus.PASS, "Paragraph fields set" + logger.addScreenCapture(util.captureFinalScreenshot()));

		objRDIF.selectKeyDates();

		objRDIF.setRelatedDevelopment(
				"This is a stand-alone development that is split into 2no. primary phases in terms of development approach. The 1st phase is for consolidation of Palm trees across the NEOM Region that will be displaced due to NEOM wide development proposals and the 2nd phase constitutes the design and construction of an operational nursery to support NEOM wide development proposals. ");
		util.takeSnapShot();
		logger.log(LogStatus.PASS,
				"Dates selected" + logger.addScreenCapture(util.captureFinalScreenshot()) + "Page 1 Filled");

		objRDIF.clickNextBtn();
		Thread.sleep(3000);

		objTaskPage.checkPageProgress("Development Details", "Completed");
		objTaskPage.checkPageProgress("Additional Information", "Completed");
		objTaskPage.checkPageProgress("Development Context & Categorization", "In-Progress");

		// page 2 - Development Context & Categorization

		objRDIF.setStrategicAndPlanningContext();

		objRDIF.setProjectType("Project");
		logger.log(LogStatus.PASS, logger.addScreenCapture(util.captureFinalScreenshot()));
		util.takeSnapShot();

		objRDIF.setProjectTypology();

		logger.log(LogStatus.PASS, logger.addScreenCapture(util.captureFinalScreenshot()));
		util.takeSnapShot();
		Thread.sleep(2000);

		objRDIF.setScaleSensitivity();

		logger.log(LogStatus.PASS, logger.addScreenCapture(util.captureFinalScreenshot()) + "Page 2 Filled");
		Thread.sleep(1000);

		objRDIF.clickSubmit();
		Thread.sleep(2000);
		objTaskPage.clickYesBtn();
		Thread.sleep(2000);

		System.out.println("RDIF Created: " + devTitle + "\n\n\n");

	}

	@AfterMethod
	public void getResult(ITestResult result) throws Exception {
		String screenshotPath = util.captureFinalScreenshot();;
		if (result.getStatus() == ITestResult.FAILURE) {
			logger.log(LogStatus.FAIL, "Test Case Failed is " + result.getName());
			logger.log(LogStatus.FAIL, "Test Case Failed is " + result.getThrowable());
			logger.log(LogStatus.FAIL, logger.addScreenCapture(screenshotPath));
		} else if (result.getStatus() == ITestResult.SKIP) {
			logger.log(LogStatus.SKIP, "Test Case Skipped is " + result.getName());
			logger.log(LogStatus.SKIP, logger.addScreenCapture(screenshotPath));
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			screenshotPath = util.captureFinalScreenshot();
			logger.log(LogStatus.PASS, "RDIF Submitted = " + devTitle + logger.addScreenCapture(screenshotPath));
		}
		driver.quit();
	}

}
