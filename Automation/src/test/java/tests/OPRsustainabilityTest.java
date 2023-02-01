package tests;

import java.awt.AWTException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
import tests.BaseClass;
import tests.RDIFCreationTest;
import utils.Utilities;

public class OPRsustainabilityTest extends BaseClass{
	
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

		FileInputStream in = new FileInputStream(util.getConfigPropertyLocation());
		properties = new Properties();
		properties.load(in);
		in.close();

	}

	@Test
	public void taskAcceptScopingReportATSM() throws Exception {
		extent = getExtent();
		String taskName = properties.getProperty("TASK_OPR");
		logger = extent.startTest(taskName);
		
		// String devTitle = "NEOM ATMN ID 2239";
		driver.get(properties.getProperty("url_original_proponent"));
		System.out.println("Starting Task: "+taskName);
		String devTitle = projectName;

		logger.log(LogStatus.PASS, "URL HIT");
		//String userName = properties.getProperty("assTeamSnrMgr");
		//String password = properties.getProperty("password");

		objLogin.login("Sustainibility_user", "appian@123");
		Thread.sleep(2000);
		logger.log(LogStatus.PASS, "Login Successful Sustainability");
			
		driver.findElement(By.xpath("//div[text()='PROJECT']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Welcome')]")));
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("//input[@placeholder='Enter Project Name']")).sendKeys(devTitle);
		Thread.sleep(1000);
		objTaskPage.clickSearch();
		Thread.sleep(4000);
		
		driver.findElement(By.xpath("//a[contains(text(),'"+devTitle+"')]")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='Score Card']")));
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("//div[text()='Project Details']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='PROJECT ACTIVITIES']")));
		
		driver.findElement(By.xpath("//a[contains(text(),'Owner Project Requirement')]")).click();
		logger.log(LogStatus.PASS, "Clicked Task Button");
		Thread.sleep(2000);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'OWNER PROJECT REQUIREMENTS')]")));

		logger.log(LogStatus.PASS, "Task Page: "+taskName+logger.addScreenCapture(util.captureFinalScreenshot()));
		
		objTaskPage.clickAcceptBtn();
		Thread.sleep(1000);
		logger.log(LogStatus.PASS, "Clicked Accept Button"+logger.addScreenCapture(util.captureFinalScreenshot()));
		util.takeSnapShot();
		
		
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
			logger.log(LogStatus.PASS, "Completed Accept Scoping Report Task by ATSM Successfully"+logger.addScreenCapture(screenshotPath));
		}
		driver.quit();
	}
}
