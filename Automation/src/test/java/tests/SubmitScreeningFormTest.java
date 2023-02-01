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

public class SubmitScreeningFormTest extends BaseClass {

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
	public void taskFillScreeningFormATR() throws Exception {
		extent = getExtent();
		String taskName = properties.getProperty("TASK_SUBMIT_SCREENING_ATR");
		logger = extent.startTest(taskName);

		// String devTitle = "NEOM ATMN ID 2239";
		driver.get(properties.getProperty("url_proponent"));
		String devTitle = projectName;

		logger.log(LogStatus.PASS, "URL HIT");
		System.out.println("Starting Task: "+taskName);
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
		logger.log(LogStatus.PASS, logger.addScreenCapture(util.captureFinalScreenshot()));
		task.click();
		util.takeSnapShot();
		logger.log(LogStatus.PASS, "Clicked - " + taskName + " for title - " + devTitle);

		Thread.sleep(5000);

		logger.log(LogStatus.PASS, "Task Page: " + taskName +logger.addScreenCapture(util.captureFinalScreenshot()));
		
		objTaskPage.clickAcceptBtn();
		Thread.sleep(1000);

		logger.log(LogStatus.PASS, "Clicked Accept Button");
		util.takeSnapShot();

		objTaskPage.isCoastalCommitteeRequired("Yes");
		//objTaskPage.setNcecDate("02/15/2023");
		objTaskPage.selectValue(randomInput.getRandomApprovalType(), "Construction", "Incomplete", "Complete", "Not Applicable");
		util.takeSnapShot();
		
		Thread.sleep(2000);
		
		logger.log(LogStatus.PASS, logger.addScreenCapture(util.captureFinalScreenshot())+"Clicked Next Button");

		objTaskPage.clickNextBtn();
		Thread.sleep(2000);
		util.takeSnapShot();

		objTaskPage.setActivityName("Dummy Name");
		util.takeSnapShot();

		driver.findElement(By.xpath("//label[text()='Public']")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//label[text()='Residential']")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//label[text()='Existing Facility']")).click();
		Thread.sleep(4000);
		driver.findElement(By.xpath("//label[text()='Expansion']")).click();
		Thread.sleep(1000);
		logger.log(LogStatus.PASS, logger.addScreenCapture(util.captureFinalScreenshot())+"Clicked Next Button");
		util.takeSnapShot();
		
		objTaskPage.clickNextBtn();
		Thread.sleep(2000);

		driver.findElement(By.xpath("//textarea")).sendKeys("NEOM requires early works ground and earth works for NIC Area 3. Area 3 has been identified within the NIC Masterplan as the planned location for the Food and Water Innovation Hub. Main components of the Early Works project:"
				+ "Temporary site access road running from a junction with the main Sharma-Duba Highway 5 to the site in area A."
				+ "Site preparation (removal of top 150-200 mm of surface material/vegetation in areas B, C, D and E)"
				+ "Cut (removal) of some high ground to form a development platform"
				+ "Vehicle movements will be associated with delivery and removal of fill material and transport of workers.");

		driver.findElement(By.xpath("//label[text()='Yes']")).click();
		Thread.sleep(5000);
		
		driver.findElement(By.xpath("//label[contains(text(),'Classification')]/parent::div/following-sibling::div//input")).sendKeys("Industrial");
		driver.findElement(By.xpath("//label[contains(text(),'Area')]/parent::div/following::div//input")).sendKeys("2,350,000 sqm");
		driver.findElement(By.xpath("//label[contains(text(),'Length')]/parent::div/following::div//textarea")).sendKeys("Latitude: 23.023234 Longitude: 45.234123");
		objRDIF.setUpload();
		

		logger.log(LogStatus.PASS, logger.addScreenCapture(util.captureFinalScreenshot())+"Clicked Next Button");
		util.takeSnapShot();

		objTaskPage.clickNextBtn();
		Thread.sleep(5000);
		
		List<WebElement> noBtn = driver.findElements(By.xpath("//label[text()='No']"));
		WebElement element = driver.findElement(By.xpath("//*[text()='SIMILAR ACTIVITIES']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		Thread.sleep(2000);
		
		noBtn.get(0).click();
		Thread.sleep(1000);
		noBtn.get(1).click();
		Thread.sleep(1000);
		noBtn.get(2).click();
		Thread.sleep(1000);
		noBtn.get(3).click();
		Thread.sleep(1000);

		util.takeSnapShot();
		
		logger.log(LogStatus.PASS, logger.addScreenCapture(util.captureFinalScreenshot())+"Clicked Next Button");

		objTaskPage.clickNextBtn();
		Thread.sleep(2000);
		
		logger.log(LogStatus.PASS, logger.addScreenCapture(util.captureFinalScreenshot()));
		
		List<WebElement> textArea = driver.findElements(By.xpath("//textarea"));
		
		WebElement element1 = driver.findElement(By.xpath("//*[contains(text(),'Estimated Period of the')]"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element1);
		Thread.sleep(2000);
		
		logger.log(LogStatus.PASS, logger.addScreenCapture(util.captureFinalScreenshot()));

		textArea.get(5).sendKeys("12 months");
		util.takeSnapShot();

		WebElement element2 = driver.findElement(By.xpath("//*[text()='Remarks*']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element2);
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("//label[text()='License']")).click();
		Thread.sleep(2000);

		driver.findElement(By.xpath("//label[text()='No']")).click();
		Thread.sleep(2000);
		
		driver.findElement(
				By.xpath("//*[text()='Remarks*']/ancestor::div[2]/parent::div/following-sibling::div//textarea"))
				.sendKeys("Screening form to be reviewed.");
		
		

		util.takeSnapShot();
		logger.log(LogStatus.PASS, logger.addScreenCapture(util.captureFinalScreenshot()));
		objTaskPage.clickSubmitBtn();
		Thread.sleep(2000);

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
			logger.log(LogStatus.PASS, "Completed Screening Form by ATR Successfully"+logger.addScreenCapture(screenshotPath));
		}
		driver.quit();
	}
}
