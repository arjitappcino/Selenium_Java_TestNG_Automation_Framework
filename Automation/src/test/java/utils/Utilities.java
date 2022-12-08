package utils;

import java.io.File;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import tests.BaseClass;

public class Utilities{

	WebDriver driver;

	public Utilities(WebDriver driver) {
		this.driver = driver;
	}

	public void takeSnapShot() throws Exception {

		Timestamp instant = Timestamp.from(Instant.now());
		String instantTime = instant.toString().replace(":", "-");
		String filePath = ".\\screenshots\\"+BaseClass.projectName;
		
		System.out.println(filePath);


		TakesScreenshot scrShot = ((TakesScreenshot) driver);
		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);

		String scrShotname = "\\" + instantTime + ".png";
		File DestFile = new File(filePath + scrShotname);

		FileUtils.copyFile(SrcFile, DestFile);

	}

	public void scrollTillElementFound(WebElement element) throws InterruptedException {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		Thread.sleep(3000);
	}

	public ArrayList<String> keyDates() {

		int gap = 2;
		ArrayList<String> arr = new ArrayList<String>();

		for (int i = 0; i < 10; i++) {
			LocalDate dateObj = LocalDate.now().plusDays(gap);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
			String date = dateObj.format(formatter);
			arr.add(date);
			gap = gap + 2;
		}

		return arr;
	}

	public WebElement fetchTask(String taskName, String projectName) {
		WebElement taskFetch = driver.findElement(By.xpath(
				"//a[text()='" + projectName + "']/ancestor::td/parent::tr/td[2]//a[text()='" + taskName + "']"));
		return taskFetch;

	}

	public static void newReportHTML() {
		String strPath = "C:\\Users\\arjit.yadav\\eclipse-workspace\\Automation\\screenshots\\", strName = "report";

		try {

			File file1 = new File(strPath + "" + strName + ".html");
			file1.createNewFile();
		} catch (Exception ex1) {
		}
	}
	
	public String getPropertyFileLocation() {
		return ".\\src\\test\\java\\config.properties";
	}
}
