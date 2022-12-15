package tests;

import java.awt.AWTException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

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

public class NcecSubmitScreeningForm extends BaseClass {

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
	RDIFCreationTest rc = new RDIFCreationTest();;

	@BeforeMethod
	public void beforeTest() throws IOException, AWTException, InterruptedException {
		
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
	public void taskNCECSubmitScreeningFormATR() throws Exception {
		extent = getExtent();
		String taskName = properties.getProperty("TASK_NCEC_UPLOAD_SCREENING_ATR");
		logger = extent.startTest(taskName);
		
		// String devTitle = "NEOM ATMN ID 2239";
		driver.get(properties.getProperty("url_proponent"));
		String devTitle = properties.getProperty("currentProject");

		Thread.sleep(5000);
		logger.log(LogStatus.PASS, "URL HIT");
		String userName = properties.getProperty("assRepUser");
		String password = properties.getProperty("password");

		objLogin.login(userName, password);
		Thread.sleep(2000);
		logger.log(LogStatus.PASS, "Login Successful ATR");

		objHome.clickTaskBtn();
		logger.log(LogStatus.PASS, "Clicked Task Button");
		Thread.sleep(4000);

		objTaskPage.setSearch(devTitle);
		Thread.sleep(1000);
		objTaskPage.clickSearch();
		Thread.sleep(4000);

		WebElement task = util.fetchTask(taskName, devTitle);
		task.click();
		util.takeSnapShot();
		logger.log(LogStatus.PASS, "Clicked - " + taskName + " for title - " + devTitle);

		Thread.sleep(4000);

		logger.log(LogStatus.PASS, "Task Page: "+taskName);
		
		objTaskPage.clickAcceptBtn();
		Thread.sleep(1000);

		logger.log(LogStatus.PASS, "Clicked Accept Button");
		util.takeSnapShot();
		
		Random random = new Random();
		int x = random.nextInt(10000);
		String id = String.valueOf(x);
		driver.findElement(By.xpath("//label[text()='NCEC Number']/parent::div/following-sibling::div//input")).sendKeys("12345/44"+id);
		logger.log(LogStatus.PASS, "Set NCEC Number: 12345/44"+id);
		
		objTaskPage.setCommentTextArea("Random comment by ATR");
		logger.log(LogStatus.PASS, "Comment set");
		util.takeSnapShot();
		
		objTaskPage.clickSubmitBtn();
		logger.log(LogStatus.PASS, "Clicked submit button");
		Thread.sleep(4000);
		
		objSuccessPage.validateNCECUploadScreeningFormTaskCompleted(devTitle);
		logger.log(LogStatus.PASS, "Completed Upload Screening Form to NCEC by ATR Successfully");

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
			logger.log(LogStatus.PASS, logger.addScreenCapture(screenshotPath));
		}
		driver.quit();
	}
}
