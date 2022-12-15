package utils;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64OutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class Utilities{

	WebDriver driver;
	TakesScreenshot scrShot;
	static TakesScreenshot finalScrShot;
	Properties properties;

	public Utilities(WebDriver driver) {
		this.driver = driver;
	}

	public String takeSnapShot() throws Exception {
		
		FileInputStream in = new FileInputStream(getPropertyFileLocation());
		properties = new Properties();
		properties.load(in);
		in.close();

		Timestamp instant = Timestamp.from(Instant.now());
		String instantTime = instant.toString().replace(":", "-");
		String projectName = properties.getProperty("currentProject");
		String filePath = System.getProperty("user.dir")+"/screenshots/"+projectName;
		
		System.out.println(filePath);
		scrShot = ((TakesScreenshot) driver);
		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);

		String scrShotname = "/" + instantTime + ".png";
		String destination = filePath + scrShotname;
		File DestFile = new File(destination);

		FileUtils.copyFile(SrcFile, DestFile);
		return destination;
		

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
	
	public String captureFullScreenView() throws IOException {
		Screenshot s=new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
        //ImageIO.write(s.getImage(),"PNG",new File("C:\\projectScreenshots\\fullPageScreenshot.png"));
        String path = imgToBase64String(s.getImage(),"png");
        
		return "data:image/gif;base64,"+path;
		
	}
	
	@SuppressWarnings("deprecation")
	public static String imgToBase64String(final RenderedImage img, final String formatName) {
	    final ByteArrayOutputStream os = new ByteArrayOutputStream();
	    try (final OutputStream b64os = Base64.getEncoder().wrap(os)) {
	        ImageIO.write(img, formatName, b64os);
	    } catch (final IOException ioe) {
	        throw new UncheckedIOException(ioe);
	    }
	    return os.toString();
	}
	
	public String captureFinalScreenshot(){
		finalScrShot = ((TakesScreenshot) driver);
		String screenshotBase64 = finalScrShot.getScreenshotAs(OutputType.BASE64);
		screenshotBase64 = "data:image/gif;base64," + screenshotBase64;
		return screenshotBase64;
	}
	
	public void waitForElement(String strXpath) {
	    WebDriverWait wait = new WebDriverWait(driver,Duration.ofMillis(60000));
	    WebElement element =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(strXpath)));
	}
}
