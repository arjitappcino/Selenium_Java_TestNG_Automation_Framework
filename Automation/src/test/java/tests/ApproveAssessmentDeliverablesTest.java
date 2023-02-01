package tests;

import java.awt.AWTException;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
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

public class ApproveAssessmentDeliverablesTest extends BaseClass {

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
	LocalDate dateObj;
	List<WebElement> dates;

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
	public void taskApproveAssessmentDeliverablesATR() throws Exception {
		extent = getExtent();
		String taskName = "Approve Uploaded Deliverables before NCEC Submission";
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
		
		objHome.clickProjectsBtn();
		Thread.sleep(3000);
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'EXPORT')]")));
		
		logger.log(LogStatus.PASS, logger.addScreenCapture(util.captureFinalScreenshot()));
		
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[text()='PROJECTS']")));
		
		driver.findElement(By.xpath("//input[contains(@placeholder,'Search')]")).sendKeys(devTitle);
		driver.findElement(By.xpath("//button[text()='Search']")).click();
		Thread.sleep(5000);
		
		System.out.println("Searched project");
		
		logger.log(LogStatus.PASS, "Searched for project: "+devTitle+logger.addScreenCapture(util.captureFinalScreenshot()));
		
		driver.findElement(By.xpath("//a[text()='"+devTitle+"']")).click();
		
		dateObj = LocalDate.now().plusDays(45);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		String date = dateObj.format(formatter);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Request Information']")));
		
		driver.findElement(By.xpath("//*[contains(text(),'NCEC DETAILS')]")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='NCEC Reviewer']")));
		
		logger.log(LogStatus.PASS, "On Update Status Page"+logger.addScreenCapture(util.captureFinalScreenshot()));
		driver.findElement(By.xpath("//*[contains(text(),'Update Status')]")).click();
		Thread.sleep(3000);
		logger.log(LogStatus.PASS,logger.addScreenCapture(util.captureFinalScreenshot()));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[contains(text(),'CATEGORIZATION NCEC ACCEPTANCE')])[2]")));
		
		dates = driver.findElements(By.xpath("//*[@placeholder='mm/dd/yyyy']"));
		
		dates.get(0).sendKeys(date);
		
		driver.findElement(By.xpath("//label[text()='Approved']")).click();
		Thread.sleep(2000);
		
		logger.log(LogStatus.PASS, "CATEGORIZATION Approval Completed"+logger.addScreenCapture(util.captureFinalScreenshot()));
		objTaskPage.clickSubmitBtn();
		
		Thread.sleep(5000);
		driver.findElement(By.xpath("//*[contains(text(),'NCEC DETAILS')]")).click();
		
		if(category.contains("III")==true || category.contains("IV")==true ) {
			dateObj = LocalDate.now().plusDays(60);
			DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("MM/dd/yyyy");
			String date1 = dateObj.format(formatter1);
			driver.findElement(By.xpath("//*[contains(text(),'NCEC DETAILS')]")).click();
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='NCEC Reviewer']")));
			
			logger.log(LogStatus.PASS, "On Update Status Page"+logger.addScreenCapture(util.captureFinalScreenshot()));
			driver.findElement(By.xpath("(//button[contains(text(),'Update Status')])[2]")).click();
			Thread.sleep(3000);
			logger.log(LogStatus.PASS,logger.addScreenCapture(util.captureFinalScreenshot()));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[contains(text(),'CATEGORIZATION NCEC ACCEPTANCE')])[2]")));
			
			dates.get(0).sendKeys(date1);
			
			driver.findElement(By.xpath("//label[text()='Approved']")).click();
			Thread.sleep(2000);
			
			logger.log(LogStatus.PASS, "Scoping Acceptance Done" +logger.addScreenCapture(util.captureFinalScreenshot()));
			objTaskPage.clickSubmitBtn();
			Thread.sleep(5000);
			driver.findElement(By.xpath("//*[contains(text(),'NCEC DETAILS')]")).click();
		}
		
		Random random = new Random();
		int x = random.nextInt(10000);
		int y = random.nextInt(10000);
		String id1 = String.valueOf(x);
		String id2 = String.valueOf(y);
		
		driver.findElement(By.xpath("//*[contains(text(),'PROJECT DETAILS')]")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'BASIC DETAILS')]")));
		
		driver.findElement(By.xpath("//*[contains(text(),'Edit Project Details')]/parent::a")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[contains(@placeholder,'Accord')]")));
		
		driver.findElement(By.xpath("//input[contains(@placeholder,'Accord')]")).sendKeys(id1+"/"+id2);
	
		util.takeSnapShot();
		logger.log(LogStatus.PASS, "Set Accord Number: "+id1+"/"+id2+logger.addScreenCapture(util.captureFinalScreenshot()));
		objTaskPage.clickSubmitBtn();
		
		Thread.sleep(5000);
	}
	

	@AfterMethod
	public void getResult(ITestResult result) throws Exception {
		String screenshotPath;
		if (result.getStatus() == ITestResult.FAILURE) {
			logger.log(LogStatus.FAIL, "Test Case Failed is " + result.getName());
			logger.log(LogStatus.FAIL, "Test Case Failed is " + result.getThrowable());
			screenshotPath = util.captureFinalScreenshot();
			logger.log(LogStatus.FAIL, "Failed Approval NCEC"+logger.addScreenCapture(screenshotPath));
		} else if (result.getStatus() == ITestResult.SKIP) {
			logger.log(LogStatus.SKIP, "Test Case Skipped is " + result.getName());
			screenshotPath = util.captureFinalScreenshot();
			logger.log(LogStatus.SKIP, logger.addScreenCapture(screenshotPath));
		}else if(result.getStatus() == ITestResult.SUCCESS) {
			screenshotPath = util.captureFinalScreenshot();
			logger.log(LogStatus.PASS, "Approved NCEC Acceptance"+logger.addScreenCapture(screenshotPath));
		}
		driver.quit();
	}
}
