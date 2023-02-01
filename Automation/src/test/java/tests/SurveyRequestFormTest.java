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

public class SurveyRequestFormTest extends BaseClass {
	
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
	String taskName;

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

		FileInputStream in = new FileInputStream(util.getConfigPropertyLocation());
		properties = new Properties();
		properties.load(in);
		in.close();

	}

	@Test
	public void taskSurveyRequest() throws Exception {
		extent = getExtent();
		String devTitle = projectName;
		String taskName = properties.getProperty("TASK_SURVEY_REQUEST_ATR");
		logger = extent.startTest(taskName);
		// devTitle = "NEOM ATMN ID 5765";
		driver.get(properties.getProperty("url_proponent"));

		logger.log(LogStatus.PASS, "URL HIT");
		System.out.println("Starting Task: "+taskName);
		String userName = properties.getProperty("assRepUser");
		String password = properties.getProperty("password");

		objLogin.login(userName, password);
		Thread.sleep(2000);
		logger.log(LogStatus.PASS, "Login Successful ATR: "+devTitle);

		objHome.clickTaskBtn();
		Thread.sleep(2000);
		
		objTaskPage.setSearch(devTitle);
		Thread.sleep(1000);
		objTaskPage.clickSearch();
		Thread.sleep(7000);

		WebElement task = util.fetchTask(taskName, devTitle);
		logger.log(LogStatus.PASS, "Task Page: "+taskName+logger.addScreenCapture(util.captureFinalScreenshot()));
		task.click();
		Thread.sleep(5000);
		util.takeSnapShot();
		
		logger.log(LogStatus.PASS, logger.addScreenCapture(util.captureFinalScreenshot()));
		
		objTaskPage.clickAcceptBtn();
		Thread.sleep(2000);

		//objTaskPage.selectDepartment(randomInput.getRandomDepartment());
		
//		String department = driver.findElement(By.xpath("//span[contains(text(),'Sector/')]/parent::div/following-sibling::div//p")).getText();
//		logger.log(LogStatus.PASS, "Department/Sector: "+department);
//		System.out.println("Department: "+department);
		util.takeSnapShot();

		List<WebElement> inputFields = driver.findElements(By.xpath("//input[@type='text']"));
		inputFields.get(0).sendKeys("01/31/2023");
		
		List<WebElement> textAreas = driver.findElements(By.xpath("//textarea"));
		textAreas.get(0).sendKeys("Random values");
		textAreas.get(1).sendKeys("Random values");
		driver.findElement(By.xpath("//textarea[contains(@placeholder,'Wind farm')]")).sendKeys("Random values");
		logger.log(LogStatus.PASS, logger.addScreenCapture(util.captureFinalScreenshot()));
		util.takeSnapShot();
		
		objTaskPage.clickNextBtn();
		Thread.sleep(2000);
		
//		WebElement element2 = driver.findElement(By.xpath("//*[text()='GEOGRAPHICAL DETAILS']"));
//		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element2);
//		Thread.sleep(2000);
		
		List<WebElement> uploadFiles = driver.findElements(By.xpath("//input[@type='file']"));
		uploadFiles.get(0).sendKeys("C:\\Users\\arjit.yadav\\Desktop\\docs\\process.jpg");
		uploadFiles.get(1).sendKeys("C:\\Users\\arjit.yadav\\Desktop\\docs\\process2.jpg");
		Thread.sleep(2000);
		driver.findElement(By.xpath("//label[contains(text(),'calculated')]/parent::div/following-sibling::div//input")).sendKeys("10000");
		Thread.sleep(2000);
		List<WebElement> textAreasGD = driver.findElements(By.xpath("//textarea"));
		textAreasGD.get(0).sendKeys("Random values");
		textAreasGD.get(1).sendKeys("Random values");
		
		util.takeSnapShot();
		
		//logger.log(LogStatus.PASS, logger.addScreenCapture(util.captureFinalScreenshot()));
		
//		WebElement element = driver.findElement(By.xpath("//label[text()='Comments']"));
//		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
//		Thread.sleep(2000);
		//driver.findElement(By.xpath("//label[text()='Comments']/parent::div/following-sibling::div//textarea")).sendKeys("Comments by ATR");
		
		logger.log(LogStatus.PASS, logger.addScreenCapture(util.captureFinalScreenshot()));

		
		
		List<WebElement> latitudeAdd = driver.findElements(By.xpath("//input"));
		latitudeAdd.get(3).sendKeys("21.66");
		latitudeAdd.get(4).sendKeys("22.66");
		latitudeAdd.get(5).sendKeys("23.66");
		latitudeAdd.get(6).sendKeys("24.66");
		
		List<WebElement> crossLatitude = driver.findElements(By.xpath("//span[contains(@class,'negative')]"));
		crossLatitude.get(0).click();
		
		Thread.sleep(2000);
		
		objTaskPage.clickSubmitBtn();
		Thread.sleep(2000);
		objTaskPage.clickYesBtn();
		Thread.sleep(2000);
		
		//objSuccessPage.validateSurveyRequestTaskCompleted(devTitle);
		util.takeSnapShot();
		System.out.println("Completed Task: "+taskName);

	}

	@AfterMethod
	public void getResult(ITestResult result) throws Exception {
		String screenshotPath;
		if (result.getStatus() == ITestResult.FAILURE) {
			logger.log(LogStatus.FAIL, "Test Case Failed is " + result.getName());
			logger.log(LogStatus.FAIL, "Test Case Failed is " + result.getThrowable());
			screenshotPath = util.captureFinalScreenshot();
			System.out.println("Survey Request Task Failed");
			logger.log(LogStatus.FAIL, logger.addScreenCapture(screenshotPath));
		} else if (result.getStatus() == ITestResult.SKIP) {
			logger.log(LogStatus.SKIP, "Test Case Skipped is " + result.getName());
			screenshotPath = util.captureFinalScreenshot();
			logger.log(LogStatus.SKIP, logger.addScreenCapture(screenshotPath));
		}else if(result.getStatus() == ITestResult.SUCCESS) {
			screenshotPath = util.captureFinalScreenshot();
			logger.log(LogStatus.PASS, "Completed Survey Form Request Successfully"+logger.addScreenCapture(screenshotPath));
		}
		driver.quit();
	}
}
