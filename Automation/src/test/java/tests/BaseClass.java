package tests;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import utils.Utilities;

public class BaseClass {
	WebDriver driver;
	ExtentTest logger;
	File screenshotFolder = new File("C:\\Users\\arjit.yadav\\eclipse-workspace\\Automation\\screenshots");
	static ExtentReports extent;

	@BeforeSuite
	public void beforeSuite() throws IOException {
		FileUtils.cleanDirectory(screenshotFolder);
		Utilities.newReportHTML();
		extent = new ExtentReports("C:\\Users\\arjit.yadav\\eclipse-workspace\\Automation\\screenshots\\report.html",true);
		extent.loadConfig(new File("C:\\Users\\arjit.yadav\\eclipse-workspace\\Automation\\extent-config.xml"));
		DOMConfigurator.configure("log4j.xml");
		PropertyConfigurator.configure("C:\\Users\\arjit.yadav\\eclipse-workspace\\Automation\\logs\\generatedLogs.log");
	}
	
	@BeforeTest
	public void setUp() throws AWTException, InterruptedException {
		startSeleniumSession();
	}

	@SuppressWarnings("deprecation")
	public void startSeleniumSession() throws AWTException, InterruptedException {
		System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");
		ChromeOptions capability = new ChromeOptions();
		capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		capability.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
		driver = new ChromeDriver(capability);		
		
		driver.manage().window().maximize();

		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_SUBTRACT);
		robot.keyRelease(KeyEvent.VK_SUBTRACT);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		Thread.sleep(1000);
	}

	public WebDriver getDriver() {
		return driver;
	}
	
	public static ExtentReports getExtent() {
		return extent;
	}
	
	@AfterSuite
	public void afterSuitre() {
		extent.flush();
	}
}