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
import org.apache.commons.io.FileUtils;
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
	WebDriver driver;
	ExtentTest logger;
	File screenshotFolder = new File(".\\screenshots");
	static ExtentReports extent;
	static String alpha = RandomStringUtils.randomAlphabetic(4).toUpperCase();
	static String numeric = RandomStringUtils.randomNumeric(4);
	//public static String projectName = "ATMN ID " +alpha+ numeric;
	public static String projectName = "ATMN ID IJXS4332";
	Utilities util = new Utilities(driver);
	Properties properties;
	String currentDirectory = System.getProperty("user.dir");
	Robot robot;
	Duration waitDuration = Duration.ofSeconds(300);
	WebDriverWait wait;

	@BeforeSuite
	public void beforeSuite() throws IOException {
		//FileUtils.cleanDirectory(screenshotFolder);
		FileInputStream in = new FileInputStream(util.getPropertyFileLocation());
		properties = new Properties();
		properties.load(in);
		in.close();

		FileOutputStream out = new FileOutputStream(util.getPropertyFileLocation());
		properties.setProperty("currentProject", projectName);
		properties.store(out, null);
		out.close();

		String path = ".\\screenshots\\" + projectName + "\\report.html";
		System.out.println(path);
		extent = new ExtentReports(path, true);
		extent.loadConfig(new File(".\\extent-config.xml"));
		DOMConfigurator.configure("log4j.xml");
		PropertyConfigurator.configure(".\\logs\\generatedLogs.log");
	}

	@BeforeTest
	public void setUp() throws AWTException, InterruptedException {
		startSeleniumSession();
		Thread.sleep(5000);
		
	}

	@SuppressWarnings("deprecation")
	public void startSeleniumSession() throws AWTException, InterruptedException {
		System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");
		ChromeOptions capability = new ChromeOptions();
		//capability.addArguments("--window-size=1600,600");
		//capability.addArguments("--start-maximized");
		//capability.addArguments("--headless");
		capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		capability.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
		driver = new ChromeDriver(capability);

		driver.manage().window().maximize();

		robot = new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_SUBTRACT);
		robot.keyRelease(KeyEvent.VK_SUBTRACT);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		Thread.sleep(1000);

		System.out.println(currentDirectory);
	}

	public WebDriver getDriver() {
		return driver;
	}

	public static ExtentReports getExtent() {
		return extent;
	}

	public String getProjectName() {
		return projectName;
	}
	
	@AfterSuite
	public void afterSuite() {
		extent.flush();
	}

}