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
	Duration waitDuration = Duration.ofSeconds(60);
	
	@FindBy(xpath="//strong[text()='PROJECT']")
	WebElement createProjectBtn;
	
	@FindBy(xpath="//div[text()='TASKS']")
	WebElement taskBtn;

	public HomePageElements(WebDriver driver){
		this.driver = driver;
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver,waitDuration);
	}
	
	public void clickCreateProjectBtn(){
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[text()='PROJECT']")));
		Assert.assertEquals(true, createProjectBtn.isDisplayed());
		createProjectBtn.click();
		log.info("Create Project Button Clicked");
	}
	
	public void clickTaskBtn() {
		taskBtn.click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[text()='TASKS']")));
	}
}
