package pageFactory;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import utils.Utilities;

public class SuccessPageElements {

	WebDriver driver;
	static Logger log = Logger.getLogger(LoginPageElements.class);
	Utilities util;

	@FindBy(xpath = "//strong[text()='HOME']")
	WebElement homeBtn;

	@FindBy(xpath = "//strong[text()='PROJECT DASHBOARD']")
	WebElement projectDashboardBtn;
	
	public SuccessPageElements(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		util = new Utilities(driver);
	}

	public void clickHomeBtn() {
		homeBtn.click();
	}

	public void clickProjectDashboardBtn() {
		projectDashboardBtn.click();
	}

	public void validateRDIFCreationCompleted(String strProjectName) {
		util.waitForElement("//strong[contains(text(),'Thank You')]");
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
		util.waitForElement("//strong[contains(text(),'Thank You')]");
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
		
		util.waitForElement("//strong[contains(text(),'Thank You')]");
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
		util.waitForElement("//strong[contains(text(),'Thank You')]");
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
	
	public void validateReviewScreeningFormTaskCompleted(String strProjectName) {
		util.waitForElement("//strong[contains(text(),'Thank You')]");
		WebElement successHeading = driver.findElement(By.xpath("//strong[contains(text(),'Thank You')]"));
		Assert.assertEquals(true, successHeading.isDisplayed());
		String heading = successHeading.getText();
		Assert.assertEquals(true, heading.contains("Thank You"));
		
		WebElement successSubHeading = driver.findElement(By.xpath("//span[contains(text(),'Screening')]"));
		Assert.assertEquals(true, successSubHeading.isDisplayed());
		String subHeading = successSubHeading.getText();
		Assert.assertEquals(true, subHeading.contains("Review Screening Form"));
		
		WebElement projectNameDisplay = driver.findElement(By.xpath("//span[contains(text(),'"+strProjectName+"')]"));
		Assert.assertEquals(true, projectNameDisplay.isDisplayed());
		
		log.info("Success heading Message shown = " + heading);
		log.info("Success Sub heading Message shown = " + subHeading);
	}
	
	public void validateSignScreeningFormTaskCompleted(String strProjectName) {
		util.waitForElement("//strong[contains(text(),'Thank You')]");
		WebElement successHeading = driver.findElement(By.xpath("//strong[contains(text(),'Thank You')]"));
		Assert.assertEquals(true, successHeading.isDisplayed());
		String heading = successHeading.getText();
		Assert.assertEquals(true, heading.contains("Thank You"));
		
		WebElement successSubHeading = driver.findElement(By.xpath("//span[contains(text(),'Screening')]"));
		Assert.assertEquals(true, successSubHeading.isDisplayed());
		String subHeading = successSubHeading.getText();
		Assert.assertEquals(true, subHeading.contains("E-Sign for Screening Form"));
		
		WebElement projectNameDisplay = driver.findElement(By.xpath("//span[contains(text(),'"+strProjectName+"')]"));
		Assert.assertEquals(true, projectNameDisplay.isDisplayed());
		
		log.info("Success heading Message shown = " + heading);
		log.info("Success Sub heading Message shown = " + subHeading);
	}
	

	public void validateNCECUploadScreeningFormTaskCompleted(String strProjectName) {
		util.waitForElement("//strong[contains(text(),'Thank You')]");
		WebElement successHeading = driver.findElement(By.xpath("//strong[contains(text(),'Thank You')]"));
		Assert.assertEquals(true, successHeading.isDisplayed());
		String heading = successHeading.getText();
		Assert.assertEquals(true, heading.contains("Thank You"));
		
		WebElement successSubHeading = driver.findElement(By.xpath("//span[contains(text(),'Screening')]"));
		Assert.assertEquals(true, successSubHeading.isDisplayed());
		String subHeading = successSubHeading.getText();
		Assert.assertEquals(true, subHeading.contains("Upload Screening To NCEC"));
		
		WebElement projectNameDisplay = driver.findElement(By.xpath("//span[contains(text(),'"+strProjectName+"')]"));
		Assert.assertEquals(true, projectNameDisplay.isDisplayed());
		
		log.info("Success heading Message shown = " + heading);
		log.info("Success Sub heading Message shown = " + subHeading);
	}
	
	public void validateUploadScopingReportTaskCompleted(String strProjectName) {
		util.waitForElement("//strong[contains(text(),'Thank You')]");
		WebElement successHeading = driver.findElement(By.xpath("//strong[contains(text(),'Thank You')]"));
		Assert.assertEquals(true, successHeading.isDisplayed());
		String heading = successHeading.getText();
		Assert.assertEquals(true, heading.contains("Thank You"));
		
		WebElement successSubHeading = driver.findElement(By.xpath("//span[contains(text(),'Scoping')]"));
		Assert.assertEquals(true, successSubHeading.isDisplayed());
		String subHeading = successSubHeading.getText();
		Assert.assertEquals(true, subHeading.contains("Upload Scoping Form"));
		
		WebElement projectNameDisplay = driver.findElement(By.xpath("//span[contains(text(),'"+strProjectName+"')]"));
		Assert.assertEquals(true, projectNameDisplay.isDisplayed());
		
		log.info("Success heading Message shown = " + heading);
		log.info("Success Sub heading Message shown = " + subHeading);
	}
	
	public void validateReviewScopingReportTaskCompleted(String strProjectName) {
		util.waitForElement("//strong[contains(text(),'Thank You')]");
		WebElement successHeading = driver.findElement(By.xpath("//strong[contains(text(),'Thank You')]"));
		Assert.assertEquals(true, successHeading.isDisplayed());
		String heading = successHeading.getText();
		Assert.assertEquals(true, heading.contains("Thank You"));
		
		WebElement successSubHeading = driver.findElement(By.xpath("//span[contains(text(),'Scoping')]"));
		Assert.assertEquals(true, successSubHeading.isDisplayed());
		String subHeading = successSubHeading.getText();
		Assert.assertEquals(true, subHeading.contains("Review Scoping Form"));
		
		WebElement projectNameDisplay = driver.findElement(By.xpath("//span[contains(text(),'"+strProjectName+"')]"));
		Assert.assertEquals(true, projectNameDisplay.isDisplayed());
		
		log.info("Success heading Message shown = " + heading);
		log.info("Success Sub heading Message shown = " + subHeading);
	}
	
	public void validateAcceptScopingReportTaskCompleted(String strProjectName) {
		util.waitForElement("//strong[contains(text(),'Thank You')]");
		WebElement successHeading = driver.findElement(By.xpath("//strong[contains(text(),'Thank You')]"));
		Assert.assertEquals(true, successHeading.isDisplayed());
		String heading = successHeading.getText();
		Assert.assertEquals(true, heading.contains("Thank You"));
		
		WebElement successSubHeading = driver.findElement(By.xpath("//span[contains(text(),'Scoping')]"));
		Assert.assertEquals(true, successSubHeading.isDisplayed());
		String subHeading = successSubHeading.getText();
		Assert.assertEquals(true, subHeading.contains("Accept Scoping Form"));
		
		WebElement projectNameDisplay = driver.findElement(By.xpath("//span[contains(text(),'"+strProjectName+"')]"));
		Assert.assertEquals(true, projectNameDisplay.isDisplayed());
		
		log.info("Success heading Message shown = " + heading);
		log.info("Success Sub heading Message shown = " + subHeading);
	}
	
	public void validateNCECUploadScopingReportTaskCompleted(String strProjectName) {
		util.waitForElement("//strong[contains(text(),'Thank You')]");
		WebElement successHeading = driver.findElement(By.xpath("//strong[contains(text(),'Thank You')]"));
		Assert.assertEquals(true, successHeading.isDisplayed());
		String heading = successHeading.getText();
		Assert.assertEquals(true, heading.contains("Thank You"));
		
		WebElement successSubHeading = driver.findElement(By.xpath("//span[contains(text(),'Scoping')]"));
		Assert.assertEquals(true, successSubHeading.isDisplayed());
		String subHeading = successSubHeading.getText();
		Assert.assertEquals(true, subHeading.contains("Upload Scoping To NCEC"));
		
		WebElement projectNameDisplay = driver.findElement(By.xpath("//span[contains(text(),'"+strProjectName+"')]"));
		Assert.assertEquals(true, projectNameDisplay.isDisplayed());
		
		log.info("Success heading Message shown = " + heading);
		log.info("Success Sub heading Message shown = " + subHeading);
	}
	
	public void validateAssessmentDeliverablesTaskCompleted(String strProjectName) {
		util.waitForElement("//strong[contains(text(),'Thank You')]");
		WebElement successHeading = driver.findElement(By.xpath("//strong[contains(text(),'Thank You')]"));
		Assert.assertEquals(true, successHeading.isDisplayed());
		String heading = successHeading.getText();
		Assert.assertEquals(true, heading.contains("Thank You"));
		
		WebElement successSubHeading = driver.findElement(By.xpath("//span[contains(text(),'Deliverables')]"));
		Assert.assertEquals(true, successSubHeading.isDisplayed());
		String subHeading = successSubHeading.getText();
		Assert.assertEquals(true, subHeading.contains("Assessment Deliverables - Category"));
		
		WebElement projectNameDisplay = driver.findElement(By.xpath("//span[contains(text(),'"+strProjectName+"')]"));
		Assert.assertEquals(true, projectNameDisplay.isDisplayed());
		
		log.info("Success heading Message shown = " + heading);
		log.info("Success Sub heading Message shown = " + subHeading);
	}
	
	public void validateReviewAssessmentDeliverablesTaskCompleted(String strProjectName) {
		util.waitForElement("//strong[contains(text(),'Thank You')]");
		WebElement successHeading = driver.findElement(By.xpath("//strong[contains(text(),'Thank You')]"));
		Assert.assertEquals(true, successHeading.isDisplayed());
		String heading = successHeading.getText();
		Assert.assertEquals(true, heading.contains("Thank You"));
		
		WebElement successSubHeading = driver.findElement(By.xpath("//span[contains(text(),'Review')]"));
		Assert.assertEquals(true, successSubHeading.isDisplayed());
		String subHeading = successSubHeading.getText();
		Assert.assertEquals(true, subHeading.contains("Review Assessment Deliverables"));
		
		WebElement projectNameDisplay = driver.findElement(By.xpath("//span[contains(text(),'"+strProjectName+"')]"));
		Assert.assertEquals(true, projectNameDisplay.isDisplayed());
		
		log.info("Success heading Message shown = " + heading);
		log.info("Success Sub heading Message shown = " + subHeading);
	}
	
	public void validateAcceptAssessmentDeliverablesTaskCompleted(String strProjectName) {
		util.waitForElement("//strong[contains(text(),'Thank You')]");
		WebElement successHeading = driver.findElement(By.xpath("//strong[contains(text(),'Thank You')]"));
		Assert.assertEquals(true, successHeading.isDisplayed());
		String heading = successHeading.getText();
		Assert.assertEquals(true, heading.contains("Thank You"));
		
		WebElement successSubHeading = driver.findElement(By.xpath("//span[contains(text(),'Accept')]"));
		Assert.assertEquals(true, successSubHeading.isDisplayed());
		String subHeading = successSubHeading.getText();
		Assert.assertEquals(true, subHeading.contains("Accept Assessment Deliverables"));
		
		WebElement projectNameDisplay = driver.findElement(By.xpath("//span[contains(text(),'"+strProjectName+"')]"));
		Assert.assertEquals(true, projectNameDisplay.isDisplayed());
		
		log.info("Success heading Message shown = " + heading);
		log.info("Success Sub heading Message shown = " + subHeading);
	}
	
	public void validateNCECUploadAssessmentDeliverablesTaskCompleted(String strProjectName) {
		util.waitForElement("//strong[contains(text(),'Thank You')]");
		WebElement successHeading = driver.findElement(By.xpath("//strong[contains(text(),'Thank You')]"));
		Assert.assertEquals(true, successHeading.isDisplayed());
		String heading = successHeading.getText();
		Assert.assertEquals(true, heading.contains("Thank You"));
		
		WebElement successSubHeading = driver.findElement(By.xpath("//span[contains(text(),'Upload')]"));
		Assert.assertEquals(true, successSubHeading.isDisplayed());
		String subHeading = successSubHeading.getText();
		Assert.assertEquals(true, subHeading.contains("Upload Assessment Deliverables to NCEC"));
		
		WebElement projectNameDisplay = driver.findElement(By.xpath("//span[contains(text(),'"+strProjectName+"')]"));
		Assert.assertEquals(true, projectNameDisplay.isDisplayed());
		
		log.info("Success heading Message shown = " + heading);
		log.info("Success Sub heading Message shown = " + subHeading);
	}
	
}
