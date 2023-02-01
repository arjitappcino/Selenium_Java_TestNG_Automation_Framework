package pageFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.Random;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import utils.Utilities;

public class TaskPageElements {
	WebDriver driver;
	org.slf4j.Logger log = LoggerFactory.getLogger(HomePageElements.class);
	WebDriverWait wait;
	Duration waitDuration = Duration.ofSeconds(20);
	Properties properties;
	Utilities util;

	@FindBy(xpath = "//button[text()='Accept']")
	WebElement approveAcceptBtn;

	@FindBy(xpath = "//span[contains(text(),'This Task')]/ancestor::div[@role='presentation']//button[text()='Cancel']")
	WebElement approveCancelBtn;

	@FindBy(xpath = "//button[text()='Next']")
	WebElement nextBtn;

	@FindBy(xpath = "//span[text()='Sector/department']/parent::div/following-sibling::div//span[contains(text(),'Select a Value')]/parent::div")
	WebElement department;

	@FindBy(xpath = "//button[text()='Submit']")
	WebElement submitBtn;

	@FindBy(xpath = "//label[text()='Data']/parent::div/following-sibling::div//input")
	WebElement dataDate;

	@FindBy(xpath = "//button[text()='Yes']")
	WebElement yesBtn;

	@FindBy(xpath = "//span[text()='COMMENTS']/ancestor::div[7]/following-sibling::div//textarea")
	WebElement commentField;

	@FindBy(xpath = "//input[@placeholder='Search Tasks']")
	WebElement searchTextField;

	@FindBy(xpath = "//button[text()='Search']")
	WebElement searchBtn;

	// Screening Form Elements

	@FindBy(xpath = "//*[text()='Approval Type']/parent::div/following-sibling::div//span[contains(text(),'Select a Value')]/parent::div")
	WebElement approvalType;

	@FindBy(xpath = "//*[text()='Phase']/parent::div/following-sibling::div//span[contains(text(),'Select a Value')]/parent::div")
	WebElement phaseType;

	@FindBy(xpath = "//*[text()='Detailed Master Plan']/parent::div/following-sibling::div//span[contains(text(),'Select a Value')]/parent::div")
	WebElement detailedMasterPlanType;

	@FindBy(xpath = "//*[text()='Concept Design']/parent::div/following-sibling::div//span[contains(text(),'Select a Value')]/parent::div")
	WebElement conceptDesignType;

	@FindBy(xpath = "//*[text()='Execution Plan']/parent::div/following-sibling::div//span[contains(text(),'Select a Value')]/parent::div")
	WebElement executionPlanType;

	@FindBy(xpath = "//label[contains(text(),'Start Date')]/parent::div/following-sibling::div//input")
	WebElement ncecApprovalRequiredDate;

	@FindBy(xpath = "//label[text()='Name']/parent::div/following-sibling::div//input")
	WebElement activityName;

	@FindBy(xpath = "//label[text()='Comment']/ancestor::div/following-sibling::div//textarea")
	WebElement commentTextArea;

	@FindBy(xpath = "//button[text()='Accept']")
	WebElement approveAcceptBottomBtn;
	

	public TaskPageElements(WebDriver driver) throws IOException {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver,waitDuration);
		util = new Utilities(driver);
		FileInputStream in = new FileInputStream(util.getProjectPropertyLocation());
		properties = new Properties();
		properties.load(in);
		in.close();
		
	}

	public void checkPageProgress(String strPageName, String strExpected) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Development Details']/following-sibling::span/span")));
		WebElement ele = driver
				.findElement(By.xpath("//span[text()='" + strPageName + "']/following-sibling::span/span"));
		
		String strCheck = ele.getText();
		log.info(strPageName+":"+strCheck);
	}

	public void selectDepartment(String strDepartment) throws InterruptedException {
		department.click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[@data-tether-id='1']/following::div[text()='" + strDepartment + "']"))
				.click();
		log.info("department selected = " + strDepartment);
	}

	public void clickNextBtn() throws InterruptedException {
		WebElement element = driver.findElement(By.xpath("//button[text()='Next']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		Thread.sleep(2000);
		Assert.assertEquals(true, nextBtn.isDisplayed());
		nextBtn.click();
		log.info("Clicked Next Button");
		Thread.sleep(2000);
	}

	public void clickAcceptBtn() throws InterruptedException {
		String acceptStatus = System.getProperty("Accepted");
		if (acceptStatus.contains("Yes")) {
			log.info("Already clicked Task Accept Button");
		} else {
			approveAcceptBtn.click();
			log.info("Clicked Task Accept Button");
			
		}
		Thread.sleep(2000);
	}

	public void clickAcceptBottomBtn() throws InterruptedException {
		WebElement element = driver.findElement(By.xpath("//button[text()='Accept']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		Thread.sleep(2000);
		approveAcceptBottomBtn.click();
		log.info("Clicked Accept Bottom Button");
		Thread.sleep(2000);
	}

	public void clickCancelApproveBtn() {
		Assert.assertEquals(true, approveCancelBtn.isDisplayed());
		approveCancelBtn.click();
		log.info("Clicked cancel approval Button");
	}

	public void clickTerrestialScope(String strScope) throws InterruptedException {

		driver.findElement(By.xpath("//label[text()='" + strScope + "']")).click();
		log.info("Selected terrestial scope");

	}

	public void clickSubmitBtn() {
		Assert.assertEquals(true, submitBtn.isDisplayed());
		submitBtn.click();
		log.info("Clicked Submit Button");
	}

	public void setDataDate(String strDate) throws InterruptedException {
		WebElement element = driver.findElement(By.xpath("//strong[text()='REPORTING DETAILS AND TIME FRAME']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		Thread.sleep(2000);
		
		dataDate.sendKeys(strDate);
		log.info("Date Set to: "+strDate);
	}

	public void clickYesBtn() {
		yesBtn.click();
		log.info("Clicked yes button");
	}

	public void setComment(String strComment) {
		commentField.sendKeys(strComment);
		log.info("Comment set");
	}

	public void setSearch(String strSearch) {
		searchTextField.sendKeys(strSearch);
		log.info("Set search to: "+strSearch);
	}

	public void clickSearch() throws InterruptedException {
		
		driver.findElement(By.xpath("//span[text()='Status']")).click();
		Thread.sleep(2000);
		String acceptStatus = System.getProperty("Accepted");
		if(acceptStatus.contains("Yes")) {
			driver.findElement(By.xpath("//div[@data-tether-id='1']/following::div[text()='ACCEPTED']")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//div[@data-tether-id='1']/following::div[text()='ASSIGNED']")).click();
			Thread.sleep(2000);
		}

		
		searchBtn.click();
		Thread.sleep(3000);
		log.info("Clicked search button");
		
	}

	public void cancelBtn() {
		Assert.assertEquals(true, approveCancelBtn.isDisplayed());
		approveCancelBtn.click();
		log.info("Clicked cancel approval Button");
	}

	// Screening Form page Methods

	public void isCoastalCommitteeRequired(String strDecision) {
		driver.findElement(By.xpath("//label[text()='" + strDecision + "']")).click();
		log.info("Clicked " + strDecision + " button for coastal committee");
	}

	public void setNcecDate(String strDate) {
		ncecApprovalRequiredDate.sendKeys(strDate);
		log.info("Set NCEC Required date to " + strDate);
	}

	public void selectValue(String strApprovalType, String strPhase, String strDetailedMasterPlan,
			String strConceptDesign, String strExecutionPlan) throws InterruptedException {
		approvalType.click();
//		driver.findElement(By.xpath("//div[@data-tether-id='1']/following::div[text()='" + strApprovalType + "']"))
//				.click();
		driver.findElement(By.xpath("//div[@data-tether-id='1']/following::div[contains(text(),'Track')]"))
		.click();
		log.info("Approval Type Selected= " + strApprovalType);
		Thread.sleep(1000);

		phaseType.click();
		driver.findElement(By.xpath("//div[@data-tether-id='1']/following::div[text()='" + strPhase + "']")).click();
		log.info("Phase Type Selected= " + strPhase);
		Thread.sleep(1000);

		detailedMasterPlanType.click();
		driver.findElement(
				By.xpath("//div[@data-tether-id='1']/following::div[text()='" + strDetailedMasterPlan + "']")).click();
		log.info("Detailed MasterPlan Type Selected= " + strDetailedMasterPlan);
		Thread.sleep(1000);

		conceptDesignType.click();
		driver.findElement(By.xpath("//div[@data-tether-id='1']/following::div[text()='" + strConceptDesign + "']"))
				.click();
		log.info("Concept Design Type Selected= " + strConceptDesign);
		Thread.sleep(1000);

		executionPlanType.click();
		driver.findElement(By.xpath("//div[@data-tether-id='1']/following::div[text()='" + strExecutionPlan + "']"))
				.click();
		log.info("Execution Plan Type Selected= " + strExecutionPlan);
		Thread.sleep(1000);
	}

	public void setActivityName(String strName) {
		activityName.sendKeys(strName);
		log.info("Activity name set to: "+strName);
	}

	public void setCommentTextArea(String strComment) throws InterruptedException {
		WebElement element = driver.findElement(By.xpath("//label[text()='Comment']/ancestor::div/following-sibling::div//textarea"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		Thread.sleep(2000);

		commentTextArea.sendKeys(strComment);
		log.info("Comment set");
	}
}
