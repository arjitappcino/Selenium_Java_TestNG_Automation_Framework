package tests;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import utils.Utilities;

public class BaseClass {
	public WebDriver driver;
	ExtentTest logger;
	File screenshotFolder = new File(".\\screenshots");
	static public ExtentReports extent;
	static String alpha = RandomStringUtils.randomAlphabetic(4).toUpperCase();
	static String numeric = RandomStringUtils.randomNumeric(4);
	// public static String projectName = "ATMN ID " +alpha+ numeric;
	// public static String projectName = "ATMN ID IJXS4332";
	static String getProject = System.getProperty("projectName");
	static public String category = System.getProperty("category");
	static public String signBy = System.getProperty("SignBy");
	static public String projectName = setProjectName(getProject);
	static public String regionSelected = System.getProperty("region");
	static public String environmentSelected = System.getProperty("Environment");

	Utilities util = new Utilities(driver);
	Properties properties;
	String currentDirectory = System.getProperty("user.dir");
	Robot robot;
	Duration waitDuration = Duration.ofSeconds(60);
	public WebDriverWait wait;

	@BeforeSuite
	public void beforeSuite() throws IOException {
		//FileUtils.cleanDirectory(screenshotFolder);
		FileInputStream in = new FileInputStream(util.getConfigPropertyLocation());
		properties = new Properties();
		properties.load(in);
		in.close();

		FileInputStream fis = new FileInputStream(util.getProjectPropertyLocation());
		properties.load(fis);
		System.out.println("project name in base class: "+projectName);
		properties.setProperty("currentProject", projectName);
		FileOutputStream fos = new FileOutputStream(util.getProjectPropertyLocation());
		properties.store(fos, null);
		
		// String path = ".\\screenshots\\" + projectName + "\\report.html";
		String path = ".\\screenshots\\report.html";
		extent = new ExtentReports(path, true);
		extent.loadConfig(new File(".\\extent-config.xml"));
		DOMConfigurator.configure("log4j.xml");
		PropertyConfigurator.configure(".\\logs\\generatedLogs.log");
	}

	@BeforeTest
	public void setUp() throws AWTException, InterruptedException {
		startSeleniumSession();
		Thread.sleep(3000);

	}

	@SuppressWarnings("deprecation")
	public void startSeleniumSession() throws AWTException, InterruptedException {
		System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");
		ChromeOptions capability = new ChromeOptions();
		capability.addArguments("--window-size=1600,900");
		// capability.addArguments("--start-maximized");
		capability.addArguments("--headless");
		capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		capability.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
		driver = new ChromeDriver(capability);

		//driver.manage().window().maximize();
		robot = new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_SUBTRACT);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_SUBTRACT);
		
		
//		robot.keyPress(KeyEvent.VK_CONTROL);
//		robot.keyPress(KeyEvent.VK_SUBTRACT);
		Thread.sleep(1000);

		// System.out.println(currentDirectory);
	}

	public WebDriver getDriver() {
		return driver;
	}

	public static ExtentReports getExtent() {
		return extent;
	}

	public String getProjectName() {
		projectName = setProjectName(getProject);
		return projectName;
	}

	public static String setProjectName(String strProject) {
		if (strProject.contains("random") == true) {
			return "ATMN ID " + alpha + numeric;
		} else {
			return System.getProperty("projectName");
		}
	}

	@AfterSuite
	public void afterSuite() {
		extent.flush();
	}

}