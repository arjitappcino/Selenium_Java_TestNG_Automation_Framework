package tests;

import java.awt.AWTException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
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

public class SubmitScopingReportTest extends BaseClass{
	
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
	RDIFCreationTest rc = new RDIFCreationTest();
	WebDriverWait wait;
	
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
		wait = new WebDriverWait(driver,waitDuration);

		FileInputStream in = new FileInputStream(util.getConfigPropertyLocation());
		properties = new Properties();
		properties.load(in);
		in.close();

	}

	@Test
	public void taskSubmitScopingReport() throws Exception {
		extent = getExtent();
		String taskName = properties.getProperty("TASK_SUBMIT_SCOPING_REPORT_ATR");
		logger = extent.startTest(taskName);
		
		// String devTitle = "NEOM ATMN ID 2239";
		driver.get(properties.getProperty("url_proponent"));
		String devTitle = projectName;
		System.out.println("Starting Task: "+taskName);

		logger.log(LogStatus.PASS, "URL HIT");
		System.out.println("Starting Task: "+taskName);
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
		logger.log(LogStatus.PASS, "Task Page: "+taskName+logger.addScreenCapture(util.captureFinalScreenshot()));
		task.click();

		Thread.sleep(5000);
		util.takeSnapShot();
		logger.log(LogStatus.PASS, "Task Page: "+taskName+logger.addScreenCapture(util.captureFinalScreenshot()));
		
		objTaskPage.clickAcceptBtn();
		Thread.sleep(1000);
		logger.log(LogStatus.PASS, "Clicked Accept Button"+logger.addScreenCapture(util.captureFinalScreenshot()));
		util.takeSnapShot();
		
		String strPath = currentDirectory+"\\scoping_sample.pdf";
		driver.findElement(By.xpath("//input[@type='file']")).sendKeys(strPath);
		
		Thread.sleep(2000);
		util.takeSnapShot();
		
		logger.log(LogStatus.PASS, logger.addScreenCapture(util.captureFinalScreenshot()));
		
		objTaskPage.clickSubmitBtn();
		Thread.sleep(4000);
		util.takeSnapShot();
		
		objSuccessPage.validateUploadScopingReportTaskCompleted(devTitle);
		System.out.println("Completed Upload Scoping Report");

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
			logger.log(LogStatus.PASS, "Completed Upload Scoping Report Task by ATR Successfully"+logger.addScreenCapture(screenshotPath));
		}
		driver.quit();
	}
}
