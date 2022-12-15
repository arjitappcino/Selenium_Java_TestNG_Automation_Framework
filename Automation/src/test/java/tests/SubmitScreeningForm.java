package tests;

import java.awt.AWTException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
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

public class SubmitScreeningForm extends BaseClass {

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
	public void taskFillScreeningFormATR() throws Exception {
		extent = getExtent();
		String taskName = properties.getProperty("TASK_SUBMIT_SCREENING_ATR");
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
		Thread.sleep(1000);

		logger.log(LogStatus.PASS, "Clicked Accept Button");
		util.takeSnapShot();

		objTaskPage.isCoastalCommitteeRequired("Yes");
		objTaskPage.setNcecDate("12/31/2022");
		objTaskPage.selectValue("Standard", "Construction", "Incomplete", "Complete", "Not Applicable");
		util.takeSnapShot();

		objTaskPage.clickNextBtn();
		logger.log(LogStatus.PASS, "Clicked Next Button");
		Thread.sleep(2000);
		util.takeSnapShot();

		objTaskPage.setActivityName("Dummy Name");
		util.takeSnapShot();

		driver.findElement(By.xpath("//label[text()='Public']")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//label[text()='Residential']")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//label[text()='Existing Facility']")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//label[text()='Expansion']")).click();
		Thread.sleep(1000);
		
		util.takeSnapShot();
		objTaskPage.clickNextBtn();
		logger.log(LogStatus.PASS, "Clicked Next Button");
		Thread.sleep(2000);

		driver.findElement(By.xpath("//textarea")).sendKeys("Activity objectives text");

		driver.findElement(By.xpath("//label[text()='City']/parent::div/following-sibling::div//input"))
				.sendKeys("Tabuk");

		driver.findElement(By.xpath("//label[text()='No']")).click();

		// driver.findElement(By.xpath("//label[text()='Area And
		// Length']/parent::div/following-sibling::div//textarea")).sendKeys("Area and
		// Length");

		util.takeSnapShot();

		objTaskPage.clickNextBtn();
		logger.log(LogStatus.PASS, "Clicked Next Button");
		Thread.sleep(2000);

		List<WebElement> noBtn = driver.findElements(By.xpath("//label[text()='No']"));

		noBtn.get(0).click();
		noBtn.get(1).click();
		noBtn.get(2).click();
		noBtn.get(3).click();

		util.takeSnapShot();

		objTaskPage.clickNextBtn();
		logger.log(LogStatus.PASS, "Clicked Next Button");
		Thread.sleep(2000);

		List<WebElement> textArea = driver.findElements(By.xpath("//textarea"));

		textArea.get(0).sendKeys("Text for Estimated Period Of The Activity Stages");
		util.takeSnapShot();

		WebElement element = driver.findElement(By.xpath("//strong[text()='Remarks*']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		Thread.sleep(2000);

		driver.findElement(
				By.xpath("//strong[text()='Remarks*']/ancestor::div[2]/parent::div/following-sibling::div//textarea"))
				.sendKeys("Remarks entered");
		

		util.takeSnapShot();

		objTaskPage.clickSubmitBtn();
		Thread.sleep(4000);

		objSuccessPage.validateScreeningFormFillTaskCompleted(devTitle);
		logger.log(LogStatus.PASS, "Completed Screening Form by ATR Successfully");

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
