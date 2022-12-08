package tests;

import java.awt.AWTException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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

public class ReviewRDIF extends BaseClass {
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
	public void taskReviewRDIF() throws Exception {
		extent = getExtent();
		ExtentTest logger = extent.startTest("Review and Accept RDIF");
		//String devTitle = "NEOM ATMN ID 2239";
		driver.get(properties.getProperty("url_proponent"));
		String devTitle = properties.getProperty("currentProject");

		String taskName = properties.getProperty("TASK_RDIF_REVIEW_ATR");
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
		Thread.sleep(2000);
		util.takeSnapShot();
		logger.log(LogStatus.PASS, "Clicked - "+taskName+" for title - "+devTitle);

		logger.log(LogStatus.PASS, "Task Page: Review and Accept RDIF");

		objTaskPage.clickAcceptBtn();

		logger.log(LogStatus.PASS, "Clicked Accept Button");
		util.takeSnapShot();
		
		WebElement element0 = driver.findElement(By.xpath("//strong[text()='DEVELOPMENT CONTEXT']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element0);
		Thread.sleep(2000);
		
		util.takeSnapShot();
		
		//span[text()='COMMENTS']
		WebElement element1 = driver.findElement(By.xpath("//span[text()='COMMENTS']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element1);
		Thread.sleep(2000);
		
		objTaskPage.setComment("Comment by ATR");
		logger.log(LogStatus.PASS, "Comments entered");

		objTaskPage.clickAcceptBtn();
		logger.log(LogStatus.PASS, "Clicked Accept Button");

		util.takeSnapShot();
//		objTaskPage.clickYesBtn();

//		Thread.sleep(1000);
//		objTaskPage.clickSubmitBtn();
//		Thread.sleep(1000);
//		objTaskPage.clickYesBtn();
		Thread.sleep(4000);
		util.takeSnapShot();
		objSuccessPage.validateReviewRDIFTaskCompleted(devTitle);

		logger.log(LogStatus.PASS, "Completed Review RDIF by ATR Successfully");

	}

	@AfterMethod
	public void endTest() {
		driver.close();
	}
}
