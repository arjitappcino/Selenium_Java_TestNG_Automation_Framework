package tests;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
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

public class SignScreeningFormTest extends BaseClass {

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
		Duration waitDur = Duration.ofSeconds(120);
		wait = new WebDriverWait(driver, waitDur);

		FileInputStream in = new FileInputStream(util.getConfigPropertyLocation());
		properties = new Properties();
		properties.load(in);
		in.close();

	}
	
	public boolean isAlertPresent() {
	    try {
	        driver.switchTo().alert();
	        return true;
	    } // try
	    catch (Exception e) {
	        return false;
	    } // catch
	}

	@Test
	public void taskSignScreeningFormATSM() throws Exception {
		extent = getExtent();
		String taskName = properties.getProperty("TASK_SIGN_SCREEN_ATSM");
		logger = extent.startTest(taskName);
		String userName;

		// String devTitle = "NEOM ATMN ID 2239";
		driver.get(properties.getProperty("url_proponent"));
		System.out.println("Starting Task: "+taskName);
		String devTitle = projectName;

		logger.log(LogStatus.PASS, "URL HIT");
		System.out.println("signee : "+signBy);
		if (signBy.contains("Executive")) {
			userName = properties.getProperty("assExDirector");
		} else {
			userName = properties.getProperty("assDirector");
		}

		String password = properties.getProperty("password");

		objLogin.login(userName, password);
		Thread.sleep(2000);
		logger.log(LogStatus.PASS, "Login Successful "+userName);

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
		util.takeSnapShot();

		Thread.sleep(4000);

		logger.log(LogStatus.PASS, "Task Page: " + taskName+logger.addScreenCapture(util.captureFinalScreenshot()));

		objTaskPage.clickAcceptBtn();
		Thread.sleep(1000);
		logger.log(LogStatus.PASS, "Clicked Accept Button"+ taskName+logger.addScreenCapture(util.captureFinalScreenshot()));
		util.takeSnapShot();

		WebElement element = driver.findElement(By.xpath("//*[text()='SIMILAR ACTIVITIES']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		Thread.sleep(2000);
		util.takeSnapShot();
		
		logger.log(LogStatus.PASS, logger.addScreenCapture(util.captureFinalScreenshot()));

		WebElement element1 = driver.findElement(By.xpath("//div[text()='Comments Added']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element1);
		Thread.sleep(2000);
		util.takeSnapShot();
		
		logger.log(LogStatus.PASS, logger.addScreenCapture(util.captureFinalScreenshot()));

		objTaskPage.clickAcceptBottomBtn();
		Thread.sleep(4000);
		util.takeSnapShot();
		
		logger.log(LogStatus.PASS, logger.addScreenCapture(util.captureFinalScreenshot()));

		Thread.sleep(200000);

		WebElement iframe = driver.findElement(By.xpath("//iframe[@aria-label='DocuSign']"));
		Thread.sleep(2000);
		driver.switchTo().frame(iframe);
		
		System.out.println("DocuSign open");
		

		Thread.sleep(2000);
		logger.log(LogStatus.PASS, logger.addScreenCapture(util.captureFinalScreenshot()));
		Thread.sleep(1000);
		driver.findElement(By.xpath("//button[text()='Continue']")).click();
		logger.log(LogStatus.PASS, logger.addScreenCapture(util.captureFinalScreenshot()));
		Thread.sleep(1000);
		util.takeSnapShot();
		System.out.println("Continue Clicked");
		driver.findElement(By.xpath("//button[@data-qa='SignHere']//span")).click();
		logger.log(LogStatus.PASS, logger.addScreenCapture(util.captureFinalScreenshot()));
		List<WebElement> pageTabs = driver.findElements(By.xpath("//div[@class='page-tabs']"));

		pageTabs.get(0).click();
		Thread.sleep(1000);
		util.takeSnapShot();
		logger.log(LogStatus.PASS, logger.addScreenCapture(util.captureFinalScreenshot()));
		
		System.out.println("Document Signed");
		Thread.sleep(5000);
		
		if (isAlertPresent()) {
			System.out.println("Alert present");
		    driver.switchTo().alert();
		    driver.switchTo().alert().dismiss();
		    driver.switchTo().defaultContent();
		    Thread.sleep(2000);
		    driver.switchTo().frame(iframe);
		}
		Thread.sleep(5000);
		driver.findElement(By.xpath("//*[@id='finish-button-callout']//button[text()='Finish']")).click();
		Thread.sleep(10000);
		util.takeSnapShot();
		
		System.out.println("Clicked Finish");

		driver.switchTo().defaultContent();
		Thread.sleep(5000);
		logger.log(LogStatus.PASS, logger.addScreenCapture(util.captureFinalScreenshot()));
		Thread.sleep(5000);
//		driver.findElement(By.xpath("//*[text()='Get Signed Document']")).click();
//		Thread.sleep(10000);
		
//		logger.log(LogStatus.PASS, logger.addScreenCapture(util.captureFinalScreenshot()));
//		
//		driver.findElement(By.xpath("//*[text()='Download']")).click();
//		Thread.sleep(3000);
		
//		Robot robot = new Robot();
//		robot.keyPress(KeyEvent.VK_ENTER);
//		Thread.sleep(2000);
//		robot.keyPress(KeyEvent.VK_ESCAPE);
//		Thread.sleep(2000);
		
		driver.findElement(By.xpath("//button[text()='Submit']")).click();
		Thread.sleep(4000);
		util.takeSnapShot();

		objSuccessPage.validateSignScreeningFormTaskCompleted(devTitle);

	}

	@AfterMethod
	public void getResult(ITestResult result) throws Exception {
		String screenshotPath;
		if (result.getStatus() == ITestResult.FAILURE) {
			logger.log(LogStatus.FAIL, "Test Case Failed is " + result.getName());
			logger.log(LogStatus.FAIL, "Test Case Failed is " + result.getThrowable());
			screenshotPath = util.captureFinalScreenshot();
			logger.log(LogStatus.FAIL, logger.addScreenCapture(screenshotPath));
			System.out.println("FAILED: Test Sign Screening Form failed.");
		} else if (result.getStatus() == ITestResult.SKIP) {
			logger.log(LogStatus.SKIP, "Test Case Skipped is " + result.getName());
			screenshotPath = util.captureFinalScreenshot();
			logger.log(LogStatus.SKIP, logger.addScreenCapture(screenshotPath));
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			screenshotPath = util.captureFinalScreenshot();
			logger.log(LogStatus.PASS, "Completed Sign Screening Form Successfully"+logger.addScreenCapture(screenshotPath));
			System.out.println("PASSED: Test Sign Screening Form passed.");
		}
		driver.quit();
	}
}
