package pageFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import utils.Utilities;

public class SuccessPageElements {

	WebDriver driver;
	org.slf4j.Logger log = LoggerFactory.getLogger(SuccessPageElements.class);
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
		
	}
	
	public void validateSurveyRequestTaskCompleted(String strProjectName) {
		
	}
	
	public void validateReviewRDIFTaskCompleted(String strProjectName) {
		
		
	}
	
	public void validateScreeningFormFillTaskCompleted(String strProjectName) {
		
	}
	
	public void validateReviewScreeningFormTaskCompleted(String strProjectName) {
		
	}
	
	public void validateAcceptScreeningFormTaskCompleted(String strProjectName) {
		
	}
	public void validateSignScreeningFormTaskCompleted(String strProjectName) {
		
	}
	

	public void validateNCECUploadScreeningFormTaskCompleted(String strProjectName) {
		
	}
	
	public void validateUploadScopingReportTaskCompleted(String strProjectName) {

	}
	
	public void validateReviewScopingReportTaskCompleted(String strProjectName) {
	
	}
	
	public void validateAcceptScopingReportTaskCompleted(String strProjectName) {
		
	}
	
	public void validateNCECUploadScopingReportTaskCompleted(String strProjectName) {
		
	}
	
	public void validateAssessmentDeliverablesTaskCompleted(String strProjectName, String Category) {
	
	}
	
	public void validateReviewAssessmentDeliverablesTaskCompleted(String strProjectName) {
		
	}
	
	public void validateAcceptAssessmentDeliverablesTaskCompleted(String strProjectName) {
		
	}
	
	public void validateNCECUploadAssessmentDeliverablesTaskCompleted(String strProjectName) {
		
	}
	
}
