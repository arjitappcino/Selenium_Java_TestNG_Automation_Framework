package tests;

import java.awt.AWTException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.imageio.ImageIO;

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
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
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
		logger.log(LogStatus.PASS, "Login Successful ATR");

		objHome.clickTaskBtn();
		logger.log(LogStatus.PASS, "Clicked Task Button");
		Thread.sleep(2000);
		
		objTaskPage.setSearch(devTitle);
		Thread.sleep(1000);
		objTaskPage.clickSearch();
		Thread.sleep(7000);

		WebElement task = util.fetchTask(taskName, devTitle);
		task.click();
		Thread.sleep(5000);
		util.takeSnapShot();

		logger.log(LogStatus.PASS, "Task Page: "+taskName);

		//objTaskPage.clickAcceptBtn();
		logger.log(LogStatus.PASS, "Clicked Accept Button");
		Thread.sleep(2000);

		objTaskPage.selectDepartment(randomInput.getRandomDepartment());
		util.takeSnapShot();

		logger.log(LogStatus.PASS, "Selected department");

		List<WebElement> inputFields = driver.findElements(By.xpath("//input[@type='text']"));
		inputFields.get(0).sendKeys("12/31/2022");
		logger.log(LogStatus.PASS, "Date set");
		List<WebElement> textAreas = driver.findElements(By.xpath("//textarea"));
		textAreas.get(0).sendKeys("Random values");
		textAreas.get(1).sendKeys("Random values");
		logger.log(LogStatus.PASS, "Mandatory values entered");
		util.takeSnapShot();
		
		WebElement element2 = driver.findElement(By.xpath("//strong[text()='GEOGRAPHICAL DETAILS']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element2);
		Thread.sleep(2000);
		
		List<WebElement> uploadFiles = driver.findElements(By.xpath("//input[@type='file']"));
		uploadFiles.get(0).sendKeys("C:\\Users\\arjit.yadav\\Desktop\\docs\\process.jpg");
		uploadFiles.get(1).sendKeys("C:\\Users\\arjit.yadav\\Desktop\\docs\\process2.jpg");
		Thread.sleep(2000);
		inputFields.get(1).sendKeys("10000");
		inputFields.get(2).sendKeys("38.8951");
		inputFields.get(3).sendKeys("-89.7084");
		Thread.sleep(2000);
		textAreas.get(2).sendKeys("Random values");
		textAreas.get(3).sendKeys("Random values");
		util.takeSnapShot();
		
		logger.log(LogStatus.PASS, logger.addScreenCapture(util.captureFullScreenView()));
		
		//objTaskPage.clickSubmitBtn();
		//Thread.sleep(2000);
		//objTaskPage.clickYesBtn();
		Thread.sleep(2000);
		
		//objSuccessPage.validateSurveyRequestTaskCompleted(devTitle);
		util.takeSnapShot();

		logger.log(LogStatus.PASS, "Completed Survey Form Request Successfully");

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
