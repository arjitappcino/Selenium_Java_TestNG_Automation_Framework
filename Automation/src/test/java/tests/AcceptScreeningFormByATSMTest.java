package tests;

import java.awt.AWTException;
import java.awt.event.KeyEvent;
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

public class AcceptScreeningFormByATSMTest extends BaseClass{
	
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

		FileInputStream in = new FileInputStream(util.getConfigPropertyLocation());
		properties = new Properties();
		properties.load(in);
		in.close();

	}

	@Test
	public void taskAcceptScreeningFormATM() throws Exception {
		extent = getExtent();
		String taskName = properties.getProperty("TASK_ACCEPT_SCREEN_FORM_ATSM");
		logger = extent.startTest(taskName);
		
		// String devTitle = "NEOM ATMN ID 2239";
		driver.get(properties.getProperty("url_proponent"));
		System.out.println("Starting Task: "+taskName);
		String devTitle = projectName;

		logger.log(LogStatus.PASS, "URL HIT");
		String userName = properties.getProperty("assTeamSnrMgr");
		String password = properties.getProperty("password");

		objLogin.login(userName, password);
		Thread.sleep(2000);
		logger.log(LogStatus.PASS, "Login Successful ATSM");

		objHome.clickTaskBtn();
		logger.log(LogStatus.PASS, "Clicked Task Button");
		Thread.sleep(2000);

		objTaskPage.setSearch(devTitle);
		Thread.sleep(1000);
		objTaskPage.clickSearch();
		Thread.sleep(4000);

		WebElement task = util.fetchTask(taskName, devTitle);
		logger.log(LogStatus.PASS, "Clicked - " + taskName + " for title - " + devTitle + logger.addScreenCapture(util.captureFinalScreenshot()));
		task.click();
		util.takeSnapShot();
		

		Thread.sleep(4000);

		logger.log(LogStatus.PASS, "Task Page: "+taskName+logger.addScreenCapture(util.captureFinalScreenshot()));
		
		objTaskPage.clickAcceptBtn();
		Thread.sleep(1000);
		util.takeSnapShot();
		
		logger.log(LogStatus.PASS, "Clicked Accept Button"+logger.addScreenCapture(util.captureFinalScreenshot()));
		
		WebElement element = driver.findElement(By.xpath("//*[text()='SIMILAR ACTIVITIES']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		Thread.sleep(2000);
		util.takeSnapShot();
		
		logger.log(LogStatus.PASS, logger.addScreenCapture(util.captureFinalScreenshot()));
		
		String signee = signBy;
		WebElement element1 = driver.findElement(By.xpath("//*[contains(text(),'Select Role For E-sign')]"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element1);
		Thread.sleep(3000);
		
		driver.findElement(By.xpath("//label[text()='" +signee+ "']")).click();
		Thread.sleep(3000);
		
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[contains(text(),'Select User')]")).click();
		Thread.sleep(5000);

		driver.findElement(By.xpath("//div[@data-tether-id='1']/following::div//*[contains(text(),'Antonio')]"))
				.click();
		Thread.sleep(2000);
		logger.log(LogStatus.PASS, logger.addScreenCapture(util.captureFinalScreenshot()));
		System.out.println("Selected Signee");
		
		objTaskPage.setCommentTextArea("Accepted by ATSM");
		util.takeSnapShot();
		
		objTaskPage.clickAcceptBottomBtn();
		Thread.sleep(4000);
		util.takeSnapShot();
		
		objSuccessPage.validateAcceptScreeningFormTaskCompleted(devTitle);


	}
	
	@AfterMethod
	public void getResult(ITestResult result) throws Exception {
		String screenshotPath;
		if (result.getStatus() == ITestResult.FAILURE) {
			logger.log(LogStatus.FAIL, "Test Case Failed is " + result.getName());
			logger.log(LogStatus.FAIL, "Test Case Failed is " + result.getThrowable());
			screenshotPath = util.captureFinalScreenshot();
			logger.log(LogStatus.FAIL, "Failed"+logger.addScreenCapture(screenshotPath));
		} else if (result.getStatus() == ITestResult.SKIP) {
			logger.log(LogStatus.SKIP, "Test Case Skipped is " + result.getName());
			screenshotPath = util.captureFinalScreenshot();
			logger.log(LogStatus.SKIP, logger.addScreenCapture(screenshotPath));
		}else if(result.getStatus() == ITestResult.SUCCESS) {
			screenshotPath = util.captureFinalScreenshot();
			logger.log(LogStatus.PASS, "Completed Review Screening Form by ATM Successfully"+logger.addScreenCapture(screenshotPath));
		}
		driver.quit();
	}
}
