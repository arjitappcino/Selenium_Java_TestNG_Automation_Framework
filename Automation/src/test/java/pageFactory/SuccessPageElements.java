package pageFactory;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class SuccessPageElements {

	WebDriver driver;
	static Logger log = Logger.getLogger(LoginPageElements.class);

	@FindBy(xpath = "//strong[text()='HOME']")
	WebElement homeBtn;

	@FindBy(xpath = "//strong[text()='PROJECT DASHBOARD']")
	WebElement projectDashboardBtn;
	
	public SuccessPageElements(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void clickHomeBtn() {
		homeBtn.click();
	}

	public void clickProjectDashboardBtn() {
		projectDashboardBtn.click();
	}

	public void validateRDIFCreationCompleted(String strProjectName) {
		WebElement successHeading = driver.findElement(By.xpath("//strong[contains(text(),'Thank You')]"));
		Assert.assertEquals(true, successHeading.isDisplayed());
		String heading = successHeading.getText();
		Assert.assertEquals(true, heading.contains("Thank You"));
		
		WebElement successSubHeading = driver.findElement(By.xpath("//span[contains(text(),'added into the System')]"));
		Assert.assertEquals(true, successSubHeading.isDisplayed());
		String subHeading = successSubHeading.getText();
		Assert.assertEquals(true, subHeading.contains("has been added"));
		
		WebElement projectNameDisplay = driver.findElement(By.xpath("//span[text()='"+strProjectName+"']"));
		Assert.assertEquals(true, projectNameDisplay.isDisplayed());
		
		log.info("Success heading Message shown = " + heading);
		log.info("Success Sub heading Message shown = " + subHeading);
	}
	
	public void validateSurveyRequestTaskCompleted(String strProjectName) {
		WebElement successHeading = driver.findElement(By.xpath("//strong[contains(text(),'Thank You')]"));
		Assert.assertEquals(true, successHeading.isDisplayed());
		String heading = successHeading.getText();
		Assert.assertEquals(true, heading.contains("Thank You"));
		
		WebElement successSubHeading = driver.findElement(By.xpath("//span[contains(text(),'Survey Request')]"));
		Assert.assertEquals(true, successSubHeading.isDisplayed());
		String subHeading = successSubHeading.getText();
		Assert.assertEquals(true, subHeading.contains("Survey Request Form"));
		
		WebElement projectNameDisplay = driver.findElement(By.xpath("//span[contains(text(),'"+strProjectName+"')]"));
		Assert.assertEquals(true, projectNameDisplay.isDisplayed());
		
		log.info("Success heading Message shown = " + heading);
		log.info("Success Sub heading Message shown = " + subHeading);
	}
	
	public void validateReviewRDIFTaskCompleted(String strProjectName) {
		WebElement successHeading = driver.findElement(By.xpath("//strong[contains(text(),'Thank You')]"));
		Assert.assertEquals(true, successHeading.isDisplayed());
		String heading = successHeading.getText();
		Assert.assertEquals(true, heading.contains("Thank You"));
		
		WebElement successSubHeading = driver.findElement(By.xpath("//span[contains(text(),'Review RDIF')]"));
		Assert.assertEquals(true, successSubHeading.isDisplayed());
		String subHeading = successSubHeading.getText();
		Assert.assertEquals(true, subHeading.contains("Review RDIF Form"));
		
		WebElement projectNameDisplay = driver.findElement(By.xpath("//span[contains(text(),'"+strProjectName+"')]"));
		Assert.assertEquals(true, projectNameDisplay.isDisplayed());
		
		log.info("Success heading Message shown = " + heading);
		log.info("Success Sub heading Message shown = " + subHeading);
	}
	
	public void validateScreeningFormFillTaskCompleted(String strProjectName) {
		WebElement successHeading = driver.findElement(By.xpath("//strong[contains(text(),'Thank You')]"));
		Assert.assertEquals(true, successHeading.isDisplayed());
		String heading = successHeading.getText();
		Assert.assertEquals(true, heading.contains("Thank You"));
		
		WebElement successSubHeading = driver.findElement(By.xpath("//span[contains(text(),'Screening')]"));
		Assert.assertEquals(true, successSubHeading.isDisplayed());
		String subHeading = successSubHeading.getText();
		Assert.assertEquals(true, subHeading.contains("Screening Form"));
		
		WebElement projectNameDisplay = driver.findElement(By.xpath("//span[contains(text(),'"+strProjectName+"')]"));
		Assert.assertEquals(true, projectNameDisplay.isDisplayed());
		
		log.info("Success heading Message shown = " + heading);
		log.info("Success Sub heading Message shown = " + subHeading);
	}
	
}
