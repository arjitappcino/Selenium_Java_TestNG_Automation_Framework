package utils;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Properties;
import java.util.Random;

import javax.imageio.ImageIO;

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
	Random rand;

	public Utilities(WebDriver driver) {
		this.driver = driver;
		rand = new Random();
	}
	
	public String takeSnapShot() throws Exception {
		
		FileInputStream in = new FileInputStream(getProjectPropertyLocation());
		Properties properties = new Properties();
		properties.load(in);
		in.close();
		
//		FileInputStream out = new FileInputStream(getProjectPropertyLocation());
//		Properties propertiesOut = new Properties();
//		propertiesOut.load(out);
		//out.close();

		Timestamp instant = Timestamp.from(Instant.now());
		String instantTime = instant.toString().replace(":", "-");
		String projectName = properties.getProperty("currentProject");
		//System.out.println("utility project naem: "+projectName);
		String filePath = System.getProperty("user.dir")+"/screenshots/"+projectName;
	
		File theDir = new File(filePath);
		if (!theDir.exists()) {
			theDir.mkdirs();
		}
		
		
		scrShot = ((TakesScreenshot) driver);
		String SrcFile = scrShot.getScreenshotAs(OutputType.BASE64);
		String scrShotname = "/"+instantTime + ".png";
		//File DestFile = new File(destination);
		byte dearr[] = Base64.getDecoder().decode(SrcFile);
		Path destinationFile = Paths.get(filePath, scrShotname);
		Files.write(destinationFile, dearr);
		return scrShotname;
		

	}

	public void scrollTillElementFound(WebElement element) throws InterruptedException {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		Thread.sleep(3000);
	}

	public ArrayList<String> keyDates() {

		int gap = rand.nextInt(10);
		ArrayList<String> arr = new ArrayList<String>();

		for (int i = 0; i < 10; i++) {
			LocalDate dateObj = LocalDate.now().plusDays(gap);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
			String date = dateObj.format(formatter);
			arr.add(date);
			
			if(i%2==0) {
				gap = gap + 25 + rand.nextInt(15);
			}
			else {
				gap = gap + 1;
			}
		}

		return arr;
	}

	public WebElement fetchTask(String taskName, String projectName) throws InterruptedException {
		Thread.sleep(20000);
		WebElement taskFetch = null;
		if(taskName.contains("Develop and Submit Cat")) {
			taskFetch = driver.findElement(By.xpath(
					"//a[text()='" + projectName + "']/ancestor::td/parent::tr//a[contains(text(),'Develop and Submit Cat')]"));
		}
		else if(taskName.contains("NEV Review and Approve Cat")){
			taskFetch = driver.findElement(By.xpath(
					"//a[text()='" + projectName + "']/ancestor::td/parent::tr//a[contains(text(),'Review and Approve Cat')]"));
		}
		else {
			taskFetch = driver.findElement(By.xpath(
					"//a[text()='" + projectName + "']/ancestor::td/parent::tr//a[contains(text(),'" + taskName + "')]"));
		}
		System.out.println("Clicked on Task");
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
	
	public String getConfigPropertyLocation() {
		return ".\\src\\test\\java\\config.properties";
	}
	
	public String getProjectPropertyLocation() {
		return ".\\src\\test\\java\\project.properties";
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
