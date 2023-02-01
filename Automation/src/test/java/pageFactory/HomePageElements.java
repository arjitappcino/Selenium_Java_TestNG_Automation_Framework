package pageFactory;

import java.time.Duration;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class HomePageElements {
	
	WebDriver driver;
	Logger log = Logger.getLogger(HomePageElements.class);
	WebDriverWait wait;
	Duration waitDuration = Duration.ofSeconds(30);
	
	@FindBy(xpath="//button[contains(text(),'Start New RDIF')]")
	WebElement createProjectBtn;
	
	@FindBy(xpath="//div[text()='TASKS']")
	WebElement taskBtn;
	
	@FindBy(xpath="//div[text()='PROJECTS']")
	WebElement projectsBtn;

	public HomePageElements(WebDriver driver){
		this.driver = driver;
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver,waitDuration);
	}
	
	public void clickCreateProjectBtn() throws InterruptedException{
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Start New RDIF']")));
		Assert.assertEquals(true, createProjectBtn.isDisplayed());
		createProjectBtn.click();
		log.info("Create Project Button Clicked");
		Thread.sleep(3000);
	}
	
	public void clickTaskBtn() throws InterruptedException {
		taskBtn.click();
		Thread.sleep(5000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Search')]")));
	}
	
	public void clickProjectsBtn() throws InterruptedException {
		projectsBtn.click();
		Thread.sleep(3000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='EXPORT PROJECT TRACKER']")));
	}
	
	
}
