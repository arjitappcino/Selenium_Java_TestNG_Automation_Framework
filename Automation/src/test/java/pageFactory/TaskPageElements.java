package pageFactory;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class TaskPageElements {
	WebDriver driver;
	Logger log = Logger.getLogger(HomePageElements.class);

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

	@FindBy(xpath = "//input[@placeholder='Search RDP TASK MGMT Task Details For Assignees']")
	WebElement searchTextField;

	@FindBy(xpath = "//button[text()='Search']")
	WebElement searchBtn;

	// Screening Form Elements


	@FindBy(xpath = "//span[text()='Approval Type']/parent::div/following-sibling::div//span[contains(text(),'Select A Value')]/parent::div")
	WebElement approvalType;

	@FindBy(xpath = "//span[text()='Phase']/parent::div/following-sibling::div//span[contains(text(),'Select A Value')]/parent::div")
	WebElement phaseType;

	@FindBy(xpath = "//span[text()='Detailed Master Plan']/parent::div/following-sibling::div//span[contains(text(),'Select A Value')]/parent::div")
	WebElement detailedMasterPlanType;

	@FindBy(xpath = "//span[text()='Concept Design']/parent::div/following-sibling::div//span[contains(text(),'Select A Value')]/parent::div")
	WebElement conceptDesignType;

	@FindBy(xpath = "//span[text()='Execution Plan']/parent::div/following-sibling::div//span[contains(text(),'Select A Value')]/parent::div")
	WebElement executionPlanType;

	@FindBy(xpath = "//label[text()='NCEC Approval Required Date']/parent::div/following-sibling::div//input")
	WebElement ncecApprovalRequiredDate;
	
	@FindBy(xpath="//label[text()='Name']/parent::div/following-sibling::div//input")
	WebElement activityName;

	public TaskPageElements(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void selectDepartment(String strDepartment) throws InterruptedException {
		department.click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[@data-tether-id='1']/following::div[text()='" + strDepartment + "']"))
				.click();
		log.info("department selected = " + strDepartment);
	}

	public void clickNextBtn() throws InterruptedException {
		Assert.assertEquals(true, nextBtn.isDisplayed());
		nextBtn.click();
		log.info("Clicked Next Button");
		Thread.sleep(2000);
	}

	public void clickAcceptBtn() throws InterruptedException {
		if(approveAcceptBtn.isDisplayed()==true) {
			Assert.assertEquals(true, approveAcceptBtn.isDisplayed());
			approveAcceptBtn.click();
		}
		log.info("Clicked Accept Button");
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
	}

	public void clickYesBtn() {
		yesBtn.click();
	}

	public void setComment(String strComment) {
		commentField.sendKeys(strComment);
	}

	public void setSearch(String strSearch) {
		searchTextField.sendKeys(strSearch);
	}

	public void clickSearch() {
		searchBtn.click();
	}

	public void cancelBtn() {
		Assert.assertEquals(true,approveCancelBtn.isDisplayed());
		approveCancelBtn.click();
		log.info("Clicked cancel approval Button");
	}
	
	//Screening Form page Methods
	
	public void isCoastalCommitteeRequired(String strDecision) {
		driver.findElement(By.xpath("//label[text()='"+strDecision+"']")).click();
		log.info("Clicked "+strDecision+" button for coastal committee");
	}
	
	public void setNcecDate(String strDate) {
		ncecApprovalRequiredDate.sendKeys(strDate);
		log.info("Set NCEC Required date to "+strDate);
	}
	
	public void selectValue(String strApprovalType, String strPhase, String strDetailedMasterPlan, String strConceptDesign, String strExecutionPlan) throws InterruptedException {
		approvalType.click();
		driver.findElement(By.xpath("//div[@data-tether-id='1']/following::div[text()='" + strApprovalType + "']")).click();
		log.info("Approval Type Selected= "+strApprovalType);
		Thread.sleep(1000);
		
		phaseType.click();
		driver.findElement(By.xpath("//div[@data-tether-id='1']/following::div[text()='" + strPhase + "']")).click();
		log.info("Phase Type Selected= "+strPhase);
		Thread.sleep(1000);
		
		detailedMasterPlanType.click();
		driver.findElement(By.xpath("//div[@data-tether-id='1']/following::div[text()='" + strDetailedMasterPlan + "']")).click();
		log.info("Detailed MasterPlan Type Selected= "+strDetailedMasterPlan);
		Thread.sleep(1000);
		
		conceptDesignType.click();
		driver.findElement(By.xpath("//div[@data-tether-id='1']/following::div[text()='" + strConceptDesign + "']")).click();
		log.info("Concept Design Type Selected= "+strConceptDesign);
		Thread.sleep(1000);
		
		executionPlanType.click();
		driver.findElement(By.xpath("//div[@data-tether-id='1']/following::div[text()='" + strExecutionPlan + "']")).click();
		log.info("Execution Plan Type Selected= "+strExecutionPlan);
		Thread.sleep(1000);
	}
	
	public void setActivityName(String strName) {
		activityName.sendKeys(strName);
	}

}
