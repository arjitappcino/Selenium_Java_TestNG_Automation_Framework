package tests;

import java.awt.AWTException;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
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

public class UploadAssessmentDeliverablesTest extends BaseClass {

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
		Duration waitDur = Duration.ofSeconds(60);
		wait = new WebDriverWait(driver, waitDur);

		FileInputStream in = new FileInputStream(util.getConfigPropertyLocation());
		properties = new Properties();
		properties.load(in);
		in.close();

	}

	@Test
	public void taskUploadAssessmentDeliverablesATR() throws Exception {
		extent = getExtent();
		String taskName = properties.getProperty("TASK_UPLOAD_ASSESSMENT_DELIVERABLES");
		logger = extent.startTest(taskName);
		
		// String devTitle = "NEOM ATMN ID 2239";
		driver.get(properties.getProperty("url_proponent"));
		String devTitle = projectName;

		Thread.sleep(5000);
		logger.log(LogStatus.PASS, "URL HIT");
		System.out.println("Starting Task: "+taskName);
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
		String checkTaskName = task.getText();
		System.out.println("Task name: "+checkTaskName);
		logger.log(LogStatus.PASS, "Clicked - " + taskName + " for title - " + devTitle +logger.addScreenCapture(util.captureFinalScreenshot()));
		if(category.contains("IV")) {
			Assert.assertEquals(checkTaskName.contains("Develop and Submit Master"),true);
			driver.findElement(By.xpath("//span[text()='\" + projectName + \"']/ancestor::td/parent::tr//a[contains(text(),'Develop and Submit Master')]")).click();
		}else if(category.contains("III")){
			Assert.assertEquals(checkTaskName.contains("Develop and Submit Cat III"),true);
			task.click();
		}else if(category.contains("II")){
			Assert.assertEquals(checkTaskName.contains("Develop and Submit Cat II"),true);
			task.click();
		}else {
			Assert.assertEquals(checkTaskName.contains("Develop and Submit Cat I"),true);
			task.click();
		}
		util.takeSnapShot();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'group meeting')]")));

		logger.log(LogStatus.PASS, "Task Page: "+taskName+logger.addScreenCapture(util.captureFinalScreenshot()));
		
		objTaskPage.clickAcceptBtn();
		Thread.sleep(1000);

		logger.log(LogStatus.PASS, "Clicked Accept Button"+logger.addScreenCapture(util.captureFinalScreenshot()));
		util.takeSnapShot();
		
		List<WebElement> uploadAssessments = driver.findElements(By.xpath("//input[@type='file']"));
		
		if(category.contains("IV") || category.contains("II")) {
			uploadAssessments.get(0).sendKeys("C:\\Users\\arjit.yadav\\Desktop\\docs\\Upload1.pdf");
			uploadAssessments.get(1).sendKeys("C:\\Users\\arjit.yadav\\Desktop\\docs\\Upload2.pdf");
			uploadAssessments.get(2).sendKeys("C:\\Users\\arjit.yadav\\Desktop\\docs\\Upload3.pdf");
			uploadAssessments.get(3).sendKeys("C:\\Users\\arjit.yadav\\Desktop\\docs\\Upload4.pdf");
			Thread.sleep(2000);
		}
		else {
			uploadAssessments.get(0).sendKeys("C:\\Users\\arjit.yadav\\Desktop\\docs\\Upload1.pdf");
			uploadAssessments.get(1).sendKeys("C:\\Users\\arjit.yadav\\Desktop\\docs\\Upload2.pdf");
			Thread.sleep(2000);
		}
		
		util.takeSnapShot();
		logger.log(LogStatus.PASS, "Files Upload "+logger.addScreenCapture(util.captureFinalScreenshot()));
		objTaskPage.clickSubmitBtn();
		Thread.sleep(2000);

		util.takeSnapShot();

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
			logger.log(LogStatus.PASS, "Completed Upload Assessment Deliverables by ATR Successfully"+logger.addScreenCapture(screenshotPath));
		}
		driver.quit();
	}
}
