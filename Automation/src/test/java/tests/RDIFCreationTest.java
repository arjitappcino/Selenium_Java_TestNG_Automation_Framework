package tests;

import java.awt.AWTException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
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

public class RDIFCreationTest extends BaseClass {
	
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
	String finalSCPath,screenshotPath;
	boolean starEntry;

	@BeforeMethod
	public void beforeTest() throws IOException, AWTException, InterruptedException {
		// char250=properties.getProperty("character250");
		// char4k=properties.getProperty("character4000");
		System.out.println("project name going in beforetest "+projectName);
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
		
		FileInputStream in = new FileInputStream(util.getConfigPropertyLocation());
		properties = new Properties();
		properties.load(in);
		in.close();
	}

	@Test
	public void createProjectRDIF() throws Exception {
		extent = getExtent();
		devTitle = projectName;
		logger = extent.startTest("Create RDIF Project: "+devTitle);
		
		System.out.println("Creating New Project: "+devTitle);

		// Login Proponent
		driver.get(properties.getProperty("url_original_proponent"));
		String userName = properties.getProperty("proponentUser");
		String password = properties.getProperty("password");
		objLogin.login(userName, password);
		logger.log(LogStatus.PASS, "Login Successful with Username: "+userName+", Password: "+password);

		objHome.clickCreateProjectBtn();

		logger.log(LogStatus.PASS, "Clicked Create Project Button");
		Thread.sleep(4000);
		
		objTaskPage.checkPageProgress("Development Details","In-Progress");
		objTaskPage.checkPageProgress("Development Context & Categorization","Not-started");
		// page 1 - Development Details
		
		String setRegion = randomInput.getRandomRegion();
		objRDIF.selectRegion(setRegion);
		
		String setSector =  randomInput.getRandomSector();
		objRDIF.selectSector(setSector);
		
		objRDIF.setDevTitle(devTitle);
		
		objRDIF.addPropKeyContactDetails("p1", "p1@abc.com");
		objRDIF.addSusDevContactDetails("s1", "s1@abc.com");
		
		logger.log(LogStatus.PASS,"Region selected: "+setRegion + " | Sector Selected: "+setSector +" | Development Title set: "+devTitle + logger.addScreenCapture(util.captureFinalScreenshot()));
		
		objRDIF.setScopeDefinition("some random text");
		
		logger.log(LogStatus.PASS, "Scope Definition set"+logger.addScreenCapture(util.captureFinalScreenshot()));
		
		objRDIF.setInsertFigure();
		util.takeSnapShot();
		Thread.sleep(1000);
		
		logger.log(LogStatus.PASS, logger.addScreenCapture(util.captureFinalScreenshot()));
		
		objRDIF.setParagraphQuantityFields("some random text");
		util.takeSnapShot();
		logger.log(LogStatus.PASS,"Paragraph fields set" +logger.addScreenCapture(util.captureFinalScreenshot()));
		
		objRDIF.selectKeyDates();
		
		objRDIF.setRelatedDevelopment("some random text");
		util.takeSnapShot();
		logger.log(LogStatus.PASS, "Dates selected" +logger.addScreenCapture(util.captureFinalScreenshot())+"Page 1 Filled");

		objRDIF.clickNextBtn();
		Thread.sleep(3000);
		
		objTaskPage.checkPageProgress("Development Details","Completed");
		objTaskPage.checkPageProgress("Development Context & Categorization","In-Progress");

		// page 2 - Development Context & Categorization

		objRDIF.setStrategicAndPlanningContext("some random text");
		//String projectTypeRandom = randomInput.getRandomProjectType();
		//objRDIF.setProjectType(projectTypeRandom);
		objRDIF.setProjectType("Project");
		logger.log(LogStatus.PASS, logger.addScreenCapture(util.captureFinalScreenshot()));
		util.takeSnapShot();

		starEntry = objRDIF.setProjectTypology();
		logger.log(LogStatus.PASS, logger.addScreenCapture(util.captureFinalScreenshot()));
		util.takeSnapShot();
		Thread.sleep(2000);
		
		objRDIF.setScaleSensitivity("Random text");
		
		logger.log(LogStatus.PASS, logger.addScreenCapture(util.captureFinalScreenshot())+"Page 2 Filled");
		Thread.sleep(1000);

		objRDIF.clickSubmit();
		Thread.sleep(4000);

		// Validate RDIF created
		objSuccessPage.validateRDIFCreationCompleted(devTitle);

	}
	
	@AfterMethod
	public void getResult(ITestResult result) throws Exception {
		String screenshotPath;
		if (result.getStatus() == ITestResult.FAILURE) {
			logger.log(LogStatus.FAIL, "Test Case Failed is " + result.getName());
			logger.log(LogStatus.FAIL, "Test Case Failed is " + result.getThrowable());
			screenshotPath = util.captureFinalScreenshot();
			logger.log(LogStatus.FAIL, logger.addScreenCapture(screenshotPath));
		} else if (result.getStatus() == ITestResult.SKIP) {
			logger.log(LogStatus.SKIP, "Test Case Skipped is " + result.getName());
			screenshotPath = util.captureFinalScreenshot();
			logger.log(LogStatus.SKIP, logger.addScreenCapture(screenshotPath));
		}else if(result.getStatus() == ITestResult.SUCCESS) {
			screenshotPath = util.captureFinalScreenshot();
			logger.log(LogStatus.PASS, "RDIF Submitted = " +devTitle+logger.addScreenCapture(screenshotPath));
		}
		driver.quit();
	}
	
}
