package tests;

import java.awt.AWTException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

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

public class SurveyRequestForm extends BaseClass {
	
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
	public void taskSurveyRequest() throws Exception {
		extent = getExtent();
		String taskName = properties.getProperty("TASK_SURVEY_REQUEST_ATR");
		logger = extent.startTest(taskName);
		// devTitle = "NEOM ATMN ID 5765";
		driver.get(properties.getProperty("url_proponent"));
		String devTitle = properties.getProperty("currentProject");

		logger.log(LogStatus.PASS, "URL HIT");
		String userName = properties.getProperty("assRepUser");
		String password = properties.getProperty("password");

		objLogin.login(userName, password);
		Thread.sleep(2000);
		logger.log(LogStatus.PASS, "Login Successful");

		objHome.clickTaskBtn();
		logger.log(LogStatus.PASS, "Clicked Task Button");
		Thread.sleep(2000);

		WebElement task = util.fetchTask(taskName, devTitle);
		task.click();
		Thread.sleep(2000);
		util.takeSnapShot();

		logger.log(LogStatus.PASS, "Task Page: "+taskName);

		objTaskPage.clickAcceptBtn();

		logger.log(LogStatus.PASS, "Clicked Accept Button");

		objTaskPage.selectDepartment(randomInput.getRandomDepartment());
		util.takeSnapShot();

		logger.log(LogStatus.PASS, "Selected department");

		objTaskPage.clickNextBtn();

		objTaskPage.clickTerrestialScope("Bats");
		logger.log(LogStatus.PASS, "Terrestial Scope Selected");
		util.takeSnapShot();

		objTaskPage.setDataDate("12/15/2022");
		logger.log(LogStatus.PASS, "Date Selected");
		util.takeSnapShot();

		objTaskPage.clickSubmitBtn();

		Thread.sleep(1000);

		util.takeSnapShot();
		objTaskPage.clickYesBtn();

		Thread.sleep(1000);
		objTaskPage.clickSubmitBtn();
		Thread.sleep(1000);
		objTaskPage.clickYesBtn();
		Thread.sleep(5000);
		util.takeSnapShot();
		objSuccessPage.validateSurveyRequestTaskCompleted(devTitle);

		logger.log(LogStatus.PASS, "Completed Survey Form Request Successfully");

	}

	@AfterMethod
	public void getResult(ITestResult result) throws Exception {
		String screenshotPath;
		if (result.getStatus() == ITestResult.FAILURE) {
			logger.log(LogStatus.FAIL, "Test Case Failed is " + result.getName());
			logger.log(LogStatus.FAIL, "Test Case Failed is " + result.getThrowable());
			screenshotPath = util.takeSnapShot();
			logger.log(LogStatus.FAIL, logger.addScreenCapture(screenshotPath));
		} else if (result.getStatus() == ITestResult.SKIP) {
			logger.log(LogStatus.SKIP, "Test Case Skipped is " + result.getName());
			screenshotPath = util.takeSnapShot();
			logger.log(LogStatus.SKIP, logger.addScreenCapture(screenshotPath));
		}
		driver.quit();
	}
}
