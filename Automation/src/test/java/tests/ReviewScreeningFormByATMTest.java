package tests;

import java.awt.AWTException;
import java.awt.Robot;
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

public class ReviewScreeningFormByATMTest extends BaseClass {

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
	public void taskReviewApproveScreeningFormATM() throws Exception {
		extent = getExtent();
		String taskName = properties.getProperty("TASK_SCREEN_APPROVE_ATM");
		logger = extent.startTest(taskName);

		Thread.sleep(10000);

		// String devTitle = "NEOM ATMN ID 2239";
		driver.get(properties.getProperty("url_proponent"));
		String devTitle = projectName;

		logger.log(LogStatus.PASS, "URL HIT");
		System.out.println("Starting Task: " + taskName);
		String userName = properties.getProperty("assTeamMgr");
		String password = properties.getProperty("password");

		objLogin.login(userName, password);
		Thread.sleep(2000);
		logger.log(LogStatus.PASS, "Login Successful ATM");

		objHome.clickTaskBtn();
		Thread.sleep(2000);

		objTaskPage.setSearch(devTitle);
		Thread.sleep(1000);
		objTaskPage.clickSearch();
		Thread.sleep(4000);

		WebElement task = util.fetchTask(taskName, devTitle);
		logger.log(LogStatus.PASS, logger.addScreenCapture(util.captureFinalScreenshot()) + "Clicked - " + taskName
				+ " for title - " + devTitle);
		task.click();
		util.takeSnapShot();

		Thread.sleep(5000);

		logger.log(LogStatus.PASS, logger.addScreenCapture(util.captureFinalScreenshot()));

		objTaskPage.clickAcceptBtn();
		Thread.sleep(1000);
		logger.log(LogStatus.PASS, "Clicked Accept Button");
		util.takeSnapShot();

		WebElement element = driver.findElement(By.xpath("//*[text()='SIMILAR ACTIVITIES']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		Thread.sleep(2000);
		util.takeSnapShot();

		System.out.println("Scrolled to Similar Activities");

		logger.log(LogStatus.PASS, logger.addScreenCapture(util.captureFinalScreenshot()));
		System.out.println("CATEGORY: " + category);
		if (category.contains("III") || category.contains("IV")) {
			System.out.println("Manager will only review in cat 3 or 4 projects");
		} else {
			String signee = signBy;
			WebElement element1 = driver.findElement(By.xpath("//span[contains(text(),'Select Role For E-sign')]"));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element1);
			Thread.sleep(3000);
			driver.findElement(By.xpath("//label[text()='" +signee+ "']")).click();
			Thread.sleep(3000);
			driver.findElement(By.xpath("//div[contains(text(),'Select User')]")).click();
			Thread.sleep(5000);
//			driver.findElement(By.xpath("//input[@placeholder='Select Director']")).sendKeys("d");
//			driver.findElement(By.xpath("//input[@placeholder='Select Director']")).sendKeys("i");
//			driver.findElement(By.xpath("//input[@placeholder='Select Director']")).sendKeys("r");
			
			//Thread.sleep(5000);
			// logger.log(LogStatus.PASS,
			// logger.addScreenCapture(util.captureFinalScreenshot()));
			driver.findElement(By.xpath("//div[@data-tether-id='1']/following::div//*[contains(text(),'Antonio')]"))
					.click();
			Thread.sleep(3000);
			logger.log(LogStatus.PASS, logger.addScreenCapture(util.captureFinalScreenshot()));
			System.out.println("Selected Signee");
		}

		objTaskPage.setCommentTextArea("Reviewed by ATM");
		util.takeSnapShot();

		logger.log(LogStatus.PASS, logger.addScreenCapture(util.captureFinalScreenshot()));

		objTaskPage.clickAcceptBottomBtn();
		Thread.sleep(5000);
		util.takeSnapShot();

		//objSuccessPage.validateReviewScreeningFormTaskCompleted(devTitle);
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
			logger.log(LogStatus.PASS,
					"Completed Review Screening Form by ATM Successfully" + logger.addScreenCapture(screenshotPath));
		}
		driver.quit();
	}
}
