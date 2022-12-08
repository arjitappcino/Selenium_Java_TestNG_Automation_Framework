package pageFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import random.RandomDataInput;
import utils.Utilities;

public class CreateRDIFPageElements {
	WebDriver driver;
	Utilities util;
	static Logger log = Logger.getLogger(CreateRDIFPageElements.class);
	RandomDataInput randomInput;
	String projectType;
	WebDriverWait wait;
	Duration waitDuration = Duration.ofSeconds(10);

	// page 1 elements

	@FindBy(xpath = "//label[text()='Development Title']/ancestor::div[2]//input")
	WebElement devTitle;

	@FindBy(xpath = "//span[text()='Food']")
	WebElement regionFood;

	@FindBy(xpath = "//span[text()='Region']/parent::div/following-sibling::div//span[contains(text(),'Select a Value')]/parent::div")
	WebElement region;

	@FindBy(xpath = "//span[text()='Sector']/parent::div/following-sibling::div//span[contains(text(),'Select a Value')]/parent::div")
	WebElement sector;

	@FindBy(xpath = "//span[contains(text(),'Proponent Key Contact')]/parent::div/following-sibling::div//td[1]//input")
	WebElement propContactName;

	@FindBy(xpath = "//span[contains(text(),'Proponent Key Contact')]/parent::div/following-sibling::div//td[2]//input")
	WebElement propContactEmail;

	@FindBy(xpath = "//span[contains(text(),'Sustainability Development Team Key Contact')]/parent::div/following-sibling::div//td[1]//input")
	WebElement susContactName;

	@FindBy(xpath = "//span[contains(text(),'Sustainability Development Team Key Contact')]/parent::div/following-sibling::div//td[2]//input")
	WebElement susContactEmail;

	@FindBy(xpath = "//label[contains(text(),'Scope Definition')]/parent::div/following-sibling::div//textarea")
	WebElement scopeDefinitionField;

	@FindBy(xpath = "//strong[text()='Types And Estimated Quantities Of Raw Materials']/ancestor::div[3]/following-sibling::div//textarea")
	WebElement TypesEstimatedQuantities1;

	@FindBy(xpath = "//strong[text()='Raw Materials Sources']/ancestor::div[3]/following-sibling::div//textarea")
	WebElement TypesEstimatedQuantities2;

	@FindBy(xpath = "//strong[text()='Estimated Energy Requirements']/ancestor::div[3]/following-sibling::div//textarea")
	public WebElement TypesEstimatedQuantities3;

	@FindBy(xpath = "//strong[text()='Energy Sources']/ancestor::div[3]/following-sibling::div//textarea")
	WebElement TypesEstimatedQuantities4;

	@FindBy(xpath = "//strong[text()='Fuel Types And Estimated Quantities']/ancestor::div[3]/following-sibling::div//textarea")
	WebElement TypesEstimatedQuantities5;

	@FindBy(xpath = "//button[text()='Cancel']")
	WebElement cancelBtn;

	@FindBy(xpath = "//button[text()='Next']")
	WebElement nextBtn;

	@FindBys({ @FindBy(xpath = "//input[@data-testid='DatePickerWidget-textInput']") })
	public List<WebElement> inputKeyDate;

	@FindBy(xpath = "//label[text()='Related Developments']/parent::div//following-sibling::div//textarea")
	WebElement relatedDevField;
	
	@FindBy(xpath="//span[text()='Insert Figure']/parent::div/following-sibling::div//input")
	WebElement insertFigure;

	// page 2 elements

	@FindBy(xpath = "//label[text()='STRATEGIC CONTEXT']/parent::div/following-sibling::div//textarea")
	WebElement strategicContextField;

	@FindBy(xpath = "//label[text()='PLANNING CONTEXT']/parent::div/following-sibling::div//textarea")
	WebElement planningContextField;

	@FindBy(xpath = "//label[text()='MasterPlan Project']")
	WebElement masterProjectTypeBtn;

	@FindBy(xpath = "//label[text()='Asset']")
	WebElement assetTypeBtn;

	@FindBy(xpath = "//label[contains(text(),'Please Specify')]/parent::div/following-sibling::div//textarea")
	WebElement otherTypoSpecifyField;
	
	//page 3 elements
	
	@FindBy(xpath="//span[text()='Assessment Timeframes']/parent::div/following-sibling::div//input[@placeholder='']")
	WebElement projectAreaField;
	
	@FindBy(xpath="//div[text()='Please Select Category']")
	WebElement categoryTimeFrame;
	
	@FindBy(xpath="//div[text()='Please Select Deliverables']")
	WebElement deliverablesTimeFrame;
	
	@FindBy(xpath="//span[text()='Assessment Timeframes']/parent::div/following-sibling::div//input[@placeholder='mm/dd/yyyy']")
	WebElement dateApproval;
	
	@FindBy(xpath="//span[text()='Assessment Timeframes']/parent::div/following-sibling::div//textarea")
	WebElement commentFieldTimeFrame;
	
	@FindBy(xpath = "//button[text()='Submit']")
	WebElement submitBtn;
	
	@FindBy(xpath="//span[text()='Upload']/parent::div/following-sibling::div//input")
	WebElement uploadField;
	
	@FindBys({ @FindBy(xpath = "//textarea") })
	public List<WebElement> textAreaFieldsRemaining;
	

	public CreateRDIFPageElements(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		util = new Utilities(driver);
		wait = new WebDriverWait(driver,waitDuration);
		randomInput = new RandomDataInput(driver);
	}

	// page 1 methods

	public void setDevTitle(String strTitle) {
		devTitle.sendKeys(strTitle);
		log.info("Development Title = "+strTitle);
	}

	public void selectRegion(String strRegion) {

		region.click();
		driver.findElement(By.xpath("//div[@data-tether-id='1']/following::div[text()='" + strRegion + "']")).click();
		log.info("region selected = "+strRegion);
	}

	public void selectSector(String strSector) {

		sector.click();
		driver.findElement(By.xpath("//div[@data-tether-id='1']/following::div[text()='" + strSector + "']")).click();
		log.info("sector selected = "+strSector);
	}

	public void addPropKeyContactDetails(String strName, String strEmail) {
		propContactName.sendKeys(strName);
		propContactEmail.sendKeys(strEmail);
		log.info("Proponent Key Contact(s) = "+strName+", "+strEmail);
	}

	public void addSusDevContactDetails(String strName, String strEmail) {
		susContactName.sendKeys(strName);
		susContactEmail.sendKeys(strEmail);
		log.info("Sustainability Development Team Key Contact(s) = "+strName+", "+strEmail);
	}

	public void setScopeDefinition(String strScope) throws Exception {
		scopeDefinitionField.sendKeys(strScope);
		log.info("Scope Definition Entered");
		util.takeSnapShot();
		
		WebElement element = driver.findElement(By.xpath("//label[text()='Scope Definition']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		Thread.sleep(2000);

	}

	public void setParagraphQuantityFields(String strLorem) throws InterruptedException {
		
		WebElement element = driver.findElement(By.xpath("//div[contains(text(),'Supported Types')]"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		Thread.sleep(2000);

		TypesEstimatedQuantities1.sendKeys(strLorem);
		TypesEstimatedQuantities2.sendKeys(strLorem);
		TypesEstimatedQuantities3.sendKeys(strLorem);
		TypesEstimatedQuantities4.sendKeys(strLorem);
		TypesEstimatedQuantities5.sendKeys(strLorem);
		
		log.info("Filled paragraph Quantity fields");

	}

	public void clickCancelBtn() {
		cancelBtn.click();
		log.info("Cancel button clicked");
	}

	public void clickNextBtn() {
		nextBtn.click();
		log.info("Next Button clicked");
	}

	public void selectKeyDates() throws InterruptedException {
		WebElement element1 = driver.findElement(By.xpath("//span[text()='Key Dates']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element1);
		Thread.sleep(2000);
		
		ArrayList<String> arrDates = new ArrayList<String>(util.keyDates());
		int j = 1;
		for (int i = 0; i < 10; i++) {
			inputKeyDate.get(i).sendKeys(arrDates.get(i));
			if(i%2==0) {
				log.info("Start date for stage["+j+"]= "+arrDates.get(i));
			}
			else {
				log.info("End date for stage["+j+"]= "+arrDates.get(i));
				j++;
			}
		}
	}

	public void setRelatedDevelopment(String strRelatedDev) {
		relatedDevField.sendKeys(strRelatedDev);
		log.info("Related Development Filled.");
	}
	
	public void setInsertFigure() {
		insertFigure.sendKeys("C:\\Users\\arjit.yadav\\Desktop\\docs\\user.xlsx");
	}

	// page 2 methods
	
	public void setStrategicAndPlanningContext(String strPara) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[text()='DEVELOPMENT CONTEXT']")));
		strategicContextField.sendKeys(strPara);
		planningContextField.sendKeys(strPara);
		log.info("Strategic and Planning Context Filled.");
	}

	public void setProjectType(String strType) throws InterruptedException {
		projectType = strType;
		switch (strType) {
		case "MasterPlan Project":
			masterProjectTypeBtn.click();
			log.info("Selected Project Type = Master Project");
			break;

		case "Asset":
			assetTypeBtn.click();
			log.info("Selected Project Type = Asset");;
			break;
		}
	}

	public void setProjectTypology() throws InterruptedException {
		
		WebElement element2 = driver.findElement(By.xpath("//span[text()='Type']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element2);
		
		Thread.sleep(2000);
		
		String strTypology;
		if (projectType == "MasterPlan Project") {
			ArrayList<String> randTypoList = new ArrayList<String>();
			randTypoList = randomInput.getRandomProjectTypology();
			
			for (int i = 0; i < randTypoList.size(); i++) {
				
				strTypology = randTypoList.get(i);
				driver.findElement(By.xpath("//label[text()='"+strTypology+"']")).click();
				
				if (strTypology == "Other") {
					Thread.sleep(1000);
					otherTypoSpecifyField.sendKeys("Other Specified");
				}
			}
		}

		else {
			strTypology = randomInput.getRandomAssetTopology();
			driver.findElement(By.xpath("//label[text()='"+strTypology+"']")).click();
			if (strTypology == "Other") {
				Thread.sleep(1000);
				otherTypoSpecifyField.sendKeys("Other Specified");
			}
			log.info("Typology selected = "+strTypology);
		}
	}
	
	//page 3 methods
	
	public void setAssignmentTimeframe(String strPAA, String strCategory, String strDeliverables, String strDate, String strComment) throws InterruptedException {
		
		WebElement element3 = driver.findElement(By.xpath("//strong[text()='ENVIRONMENTAL ASSESSMENT & APPROVALS']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element3);
		Thread.sleep(2000);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[text()='ENVIRONMENTAL ASSESSMENT & APPROVALS']")));
		
		
		projectAreaField.sendKeys(strPAA);
		
		categoryTimeFrame.click();
		driver.findElement(By.xpath("//div[@data-tether-id='1']/following::div[text()='" + strCategory + "']")).click();
		
		if(strCategory!="Below Assessment Category") {
			
			//deliverablesTimeFrame.click();
			//driver.findElement(By.xpath("//div[@data-tether-id='1']/following::div[text()='" + strDeliverables + "']")).click();
			
			dateApproval.sendKeys(strDate);
		
		}
		
		commentFieldTimeFrame.sendKeys(strComment);
	}
	
	public void clickSubmit() throws InterruptedException {
		WebElement element4 = driver.findElement(By.xpath("//span[text()='Upload']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element4);
		Thread.sleep(2000);
		
		submitBtn.click();
	}
	
	public void setUpload() {
		uploadField.sendKeys("C:\\Users\\arjit.yadav\\Desktop\\docs\\process.jpg");
	}
	
	public void setRemainTextArea() {
		for(int i=1;i<6;i++) {
			textAreaFieldsRemaining.get(i).sendKeys("random text for testing");
		}
	}

}
