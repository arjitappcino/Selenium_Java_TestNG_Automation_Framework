package tests;

import java.awt.AWTException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import pageFactory.CreateRDIFPageElements;
import pageFactory.HomePageElements;
import pageFactory.LoginPageElements;
import pageFactory.SuccessPageElements;
import pageFactory.TaskPageElements;
import random.RandomDataInput;
import utils.Utilities;

public class ReviewRDIF extends BaseClass {

	BaseClass bs;
	Properties properties;
	LoginPageElements objLogin;
	HomePageElements objHome;
	CreateRDIFPageElements objRDIF;
	SuccessPageElements objSuccessPage;
	TaskPageElements objTaskPage;
	Utilities util;
	RandomDataInput randomInput;
	Actions action;
	JavascriptExecutor je;
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

	@Test
	public void taskReviewRDIF() throws Exception {
		extent = getExtent();
		String taskName = properties.getProperty("TASK_RDIF_REVIEW_ATR");
		logger = extent.startTest(taskName);
		// String devTitle = "NEOM ATMN ID 2239";
		driver.get(properties.getProperty("url_proponent"));
		String devTitle = properties.getProperty("currentProject");

		logger.log(LogStatus.PASS, "URL HIT");
		String userName = properties.getProperty("assRepUser");
		String password = properties.getProperty("password");

		objLogin.login(userName, password);
		Thread.sleep(2000);
		logger.log(LogStatus.PASS, "Login Successful ATR");

		objHome.clickTaskBtn();
		logger.log(LogStatus.PASS, "Clicked Task Button");
		Thread.sleep(2000);

		objTaskPage.setSearch(devTitle);
		Thread.sleep(1000);
		objTaskPage.clickSearch();
		Thread.sleep(4000);

		WebElement task = util.fetchTask(taskName, devTitle);
		task.click();
		util.takeSnapShot();
		logger.log(LogStatus.PASS, "Clicked - " + taskName + " for title - " + devTitle);

		Thread.sleep(4000);

		logger.log(LogStatus.PASS, "Task Page: " + taskName);

		objTaskPage.clickAcceptBtn();

		logger.log(LogStatus.PASS, "Clicked Accept Button");
		util.takeSnapShot();
		
		objTaskPage.checkPageProgress("Development Details","Completed");
		objTaskPage.checkPageProgress("Development Context & Categorization","Completed");
		objTaskPage.checkPageProgress("Environmental Assessment & Approvals","Not-started");
		
		//objRDIF.setCommentReviewRdif("Comments by ATR");
		objTaskPage.clickNextBtn();
		
		objTaskPage.checkPageProgress("Development Details","Completed");
		objTaskPage.checkPageProgress("Development Context & Categorization","Completed");
		objTaskPage.checkPageProgress("Environmental Assessment & Approvals","Not-started");
		
		//objRDIF.setCommentReviewRdif("Comments by ATR");
		objTaskPage.clickNextBtn();

		// page 3 - Environmental Assessment & Approvals

		String cat = properties.getProperty("category");

		objRDIF.setAssignmentTimeframe("PAA name", cat, "Scoping, ENVID, ESIA Category III and ESMP Report", "12/15/2022", "Comments added");
		util.takeSnapShot();

		objRDIF.setUpload();
		util.takeSnapShot();

		objRDIF.setRemainTextArea();
		Thread.sleep(2000);
		
		objRDIF.setCommentReviewRdif("Comments by ATR");
		logger.log(LogStatus.PASS, "Comments entered");
		
		objTaskPage.clickAcceptBottomBtn();
		logger.log(LogStatus.PASS, "Clicked Accept Button");

		util.takeSnapShot();
		Thread.sleep(4000);
		util.takeSnapShot();
		objSuccessPage.validateReviewRDIFTaskCompleted(devTitle);

		logger.log(LogStatus.PASS, "Completed Review RDIF by ATR Successfully");

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
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			screenshotPath = util.captureFinalScreenshot();
			logger.log(LogStatus.PASS, logger.addScreenCapture(screenshotPath));
		}
		driver.quit();
	}
}
