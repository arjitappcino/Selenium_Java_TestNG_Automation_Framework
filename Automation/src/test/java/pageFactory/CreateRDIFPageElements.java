package pageFactory;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
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
import org.slf4j.LoggerFactory;
import random.RandomDataInput;
import utils.Utilities;

public class CreateRDIFPageElements {
	WebDriver driver;
	Utilities util;
	org.slf4j.Logger log = LoggerFactory.getLogger(CreateRDIFPageElements.class);
	RandomDataInput randomInput;
	String projectType;
	WebDriverWait wait;
	Duration waitDuration = Duration.ofSeconds(10);

	// page 1 elements

	@FindBy(xpath = "//label[text()='Development Title']/ancestor::div[2]//input")
	WebElement devTitle;

	@FindBy(xpath = "//span[text()='Food']")
	WebElement regionFood;

	@FindBy(xpath = "//span[text()='Region']/parent::div/following-sibling::div//span[contains(text(),'Select a Region')]/parent::div")
	WebElement region;

	@FindBy(xpath = "//span[text()='Sector']/parent::div/following-sibling::div//span[contains(text(),'Select a Sector')]/parent::div")
	WebElement sector;

	@FindBy(xpath = "//input[contains(@placeholder,'Select Proponent Member')]")
	WebElement propContactName;

	@FindBy(xpath = "//input[contains(@placeholder,'Select Sustainability Member')]")
	WebElement susContactName;

	@FindBy(xpath = "//label[contains(text(),'Scope Definition')]/parent::div/following-sibling::div//textarea")
	WebElement scopeDefinitionField;

	@FindBy(xpath = "//*[contains(text(),'Types and Estimated Quantities of Raw Materials')]/ancestor::div[3]/following-sibling::div//textarea")
	WebElement TypesEstimatedQuantities1;

	@FindBy(xpath = "//*[contains(text(),'Raw Material Sources')]/ancestor::div[3]/following-sibling::div//textarea")
	WebElement TypesEstimatedQuantities2;

	@FindBy(xpath = "//*[contains(text(),'Estimated Energy Requirements')]/ancestor::div[3]/following-sibling::div//textarea")
	public WebElement TypesEstimatedQuantities3;

	@FindBy(xpath = "//*[contains(text(),'Energy Sources')]/ancestor::div[3]/following-sibling::div//textarea")
	WebElement TypesEstimatedQuantities4;

	@FindBy(xpath = "//*[contains(text(),'Fuel Types and Estimated Quantities')]/ancestor::div[3]/following-sibling::div//textarea")
	WebElement TypesEstimatedQuantities5;

	@FindBy(xpath = "//button[text()='Cancel']")
	WebElement cancelBtn;

	@FindBy(xpath = "//button[text()='Next']")
	WebElement nextBtn;

	@FindBys({ @FindBy(xpath = "//input[@data-testid='DatePickerWidget-textInput']") })
	public List<WebElement> inputKeyDate;

	@FindBy(xpath = "//label[text()='Related Developments']/parent::div//following-sibling::div//textarea")
	WebElement relatedDevField;

	@FindBy(xpath = "//input[@type='file']")
	WebElement insertFigure;

	// page 2 elements

	@FindBy(xpath = "//label[text()='Strategic Context']/parent::div/following-sibling::div//textarea")
	WebElement strategicContextField;

	@FindBy(xpath = "//label[text()='Planning Context']/parent::div/following-sibling::div//textarea")
	WebElement planningContextField;

	@FindBy(xpath = "//label[text()='MasterPlan Project']")
	WebElement masterProjectTypeBtn;

	@FindBy(xpath = "//label[text()='Project']")
	WebElement singleProjectTypeBtn;

	@FindBy(xpath = "//label[contains(text(),'Please Specify')]/parent::div/following-sibling::div//textarea")
	WebElement otherTypoSpecifyField;
	
	@FindBy(xpath="//label[text()='Comments']/parent::div/following-sibling::div//textarea")
	WebElement commentField;
	
	@FindBy(xpath="//label[contains(text(),'Scale')]/parent::div/following::div//textarea")
	WebElement textareaScaleSense;

	// page 3 elements

	@FindBy(xpath = "//input[@placeholder='']")
	WebElement projectAreaField;

	@FindBy(xpath = "//div[text()='Please Select Category']")
	WebElement categoryTimeFrame;

	@FindBy(xpath = "//div[contains(text(),'Deliverables*')]")
	WebElement deliverablesTimeFrame;

	@FindBy(xpath = "//input[@placeholder='mm/dd/yyyy']")
	WebElement dateApproval;
	
	@FindBys({ @FindBy(xpath = "//textarea[@placeholder='']") })
	public List<WebElement> commentFieldTimeFrame;


	@FindBy(xpath = "//button[text()='Submit']")
	WebElement submitBtn;

	@FindBy(xpath = "//input[@type='file']")
	WebElement uploadField;

	@FindBys({ @FindBy(xpath = "//textarea") })
	public List<WebElement> textAreaFieldsRemaining;

	public CreateRDIFPageElements(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		util = new Utilities(driver);
		wait = new WebDriverWait(driver, waitDuration);
		randomInput = new RandomDataInput(driver);
	}

	// page 1 methods

	public void setDevTitle(String strTitle) {
		devTitle.sendKeys(strTitle);
		log.info("Development Title = " + strTitle);
	}

	public void selectRegion(String strRegion) {

		region.click();
		driver.findElement(By.xpath("//div[@data-tether-id='1']/following::div[text()='" + strRegion + "']")).click();
		log.info("region selected = " + strRegion);
	}

	public void selectSector(String strSector) {

		sector.click();
		driver.findElement(By.xpath("//div[@data-tether-id='1']/following::div[text()='" + strSector + "']")).click();
		log.info("sector selected = " + strSector);
	}

	public void addPropKeyContactDetails(String strName) throws InterruptedException, AWTException {
		
		propContactName.sendKeys(strName);
		if(strName.contains("arjit")) {
		propContactName.sendKeys("v");
		}
		if(strName.contains("Jhon")) {
			propContactName.sendKeys("e");
			}
		Thread.sleep(3000);
		driver.findElement(By.xpath("//div[@data-tether-id='1']/following::div//*[contains(text(),'"+strName+"')]")).click();
		
		Thread.sleep(2000);
		log.info("Proponent Key Contact(s) = " + strName);
	}

	public void addSusDevContactDetails(String strName) throws InterruptedException, AWTException {
		susContactName.sendKeys(strName);
		if(strName.contains("Henr")) {
			susContactName.sendKeys("y");
			}
		Thread.sleep(2000);
		driver.findElement(By.xpath("//div[@data-tether-id='1']/following::div//*[contains(text(),'"+strName+"')]")).click();
		Thread.sleep(2000);
		log.info("Sustainability Development Team Key Contact(s) = " + strName);
	}

	public void setScopeDefinition() throws Exception {
		WebElement element = driver.findElement(By.xpath("//*[contains(text(),'DEVELOPMENT DESCRIPTION')]"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		Thread.sleep(2000);
		
		scopeDefinitionField.sendKeys("Refer to the attachment – Book 2 Data Collection");
		log.info("Scope Definition Entered");
		util.takeSnapShot();

	}

	public void setParagraphQuantityFields() throws InterruptedException {

		WebElement element = driver.findElement(By.xpath("//*[contains(text(),'Types and Estimated Quantities of Raw Materials')]"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		Thread.sleep(2000);

		TypesEstimatedQuantities1.sendKeys("Construction Phase: Construction materials which they will be recorded and monitored.  These records will be kept for auditing.");
		TypesEstimatedQuantities2.sendKeys("Construction Phase: all material will be sourced form approved vendor i.e., all filling materials will be sourced from licensed sites.");
		TypesEstimatedQuantities3.sendKeys("Construction project, main energy requirements will in the shape of fuel consumption.  Fuel consumption records will be kept for auditing.");
		TypesEstimatedQuantities4.sendKeys("Diesel which will be sourced from local sources.");
		TypesEstimatedQuantities5.sendKeys("Construction: Low Sulphur Diesel");

		log.info("Filled paragraph Quantity fields");

	}

	public void clickCancelBtn() {
		cancelBtn.click();
		log.info("Cancel button clicked");
	}

	public void clickNextBtn() throws InterruptedException {
		WebElement element = driver.findElement(By.xpath("//button[text()='Next']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		Thread.sleep(1000);
		nextBtn.click();
		log.info("Next Button clicked");
		
		Thread.sleep(5000);
	}

	public void selectKeyDates() throws InterruptedException {
		WebElement element1 = driver.findElement(By.xpath("//*[text()='Key Dates*']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element1);
		Thread.sleep(2000);

		ArrayList<String> arrDates = new ArrayList<String>(util.keyDates());
		int j = 1;
		for (int i = 0; i < 10; i++) {
			inputKeyDate.get(i).sendKeys(arrDates.get(i));
			if (i % 2 == 0) {
				log.info("Start date for stage[" + j + "]= " + arrDates.get(i));
			} else {
				log.info("End date for stage[" + j + "]= " + arrDates.get(i));
				j++;
			}
		}
	}

	public void setRelatedDevelopment(String strRelatedDev) {
		relatedDevField.sendKeys(strRelatedDev);
		log.info("Related Development Filled.");
	}

	public void setInsertFigure() throws InterruptedException {
		WebElement element1 = driver.findElement(By.xpath("//label[text()='Scope Definition']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element1);
		Thread.sleep(2000);
		insertFigure.sendKeys("C:\\Users\\arjit.yadav\\Desktop\\docs\\screenshot.png");
		log.info("Document uploaded");
	}

	// page 2 methods

	public void setStrategicAndPlanningContext() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='DEVELOPMENT CONTEXT']")));
		String Strategic = "Visitors /Experience Centre, and the Palm Oasis (regeneration and augmentation of existing Palm trees within the project boundary) "
				+ "with the upgrade of all existing and proposed infrastructure requirements to support a nursery facility of this size in terms of operations. The full extent of the area is 3.3km². "
				+ "The nursery and operations consist of 2no. subsections"
				+ "the nursery facilities (that include service area and nursery offices)"
				+ "and the production area (which includes palm, tree, shrub, groundcover and grass production). Propagation greenhouses "
				+ "and seed nurseries will support production using the latest technology and sustainability approaches.";
		strategicContextField.sendKeys(Strategic);
		String Context = "A masterplan is currently in development that will clearly demonstrate the operational layout, location and wider landscape context for the nursery and Oasis proposal (see attached document – Book 4 Adopted CMP and Supporting Materials & Workshop 1 General Masterplan Review).";
		planningContextField.sendKeys(Context);
		log.info("Strategic and Planning Context Filled.");
	}

	public void setProjectType(String strType) throws InterruptedException {
		projectType = strType;
		switch (strType) {
		case "MasterPlan Project":
			masterProjectTypeBtn.click();
			log.info("Selected Project Type = Master Project");
			break;

		case "Project":
			singleProjectTypeBtn.click();
			log.info("Selected Project Type = Asset");
			;
			break;
		}
		Thread.sleep(2000);
	}

	public void setProjectTypology() throws InterruptedException {
		Thread.sleep(2000);
		String strTypology;
		if (projectType == "MasterPlan Project") {
			ArrayList<String> randTypoList = new ArrayList<String>();
			randTypoList = randomInput.getRandomProjectTypology();
			for (int i = 0; i < randTypoList.size(); i++) {
				
				strTypology = randTypoList.get(i);
				driver.findElement(By.xpath("//label[text()='" + strTypology + "']")).click();
				if (strTypology == "Other") {
					Thread.sleep(1000);
					otherTypoSpecifyField.sendKeys("Other Specified");
				}
			}
			Thread.sleep(2000);
		}

		else {
			strTypology = randomInput.getRandomAssetTopology();
			driver.findElement(By.xpath("//label[text()='" + strTypology + "']")).click();
			if (strTypology == "Other") {
				Thread.sleep(1000);
				otherTypoSpecifyField.sendKeys("Other Specified");
			}
			log.info("Typology selected = " + strTypology);
			Thread.sleep(2000);
		}
	}

	public void setCommentReviewRdif(String strComment) throws InterruptedException {
		commentField.sendKeys(strComment);
		WebElement element1 = driver.findElement(By.xpath("//label[text()='Comments']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element1);
		Thread.sleep(2000);
	}
	
	public void setScaleSensitivity() throws InterruptedException {
		
		
		WebElement element1 = driver.findElement(By.xpath("//label[text()='Entertainment Complex']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element1);
		Thread.sleep(2000);
		
		WebElement element2 = driver.findElement(By.xpath("//label[contains(text(),'Scale, Complexity and Sensitivity')]"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element2);
		Thread.sleep(2000);
		
		String strMatter = "The scale and complexity of the nursery is stand alone and is not integrated with any other development or urban massing. It is agricultural and horticulturally orientated with a specific operation in supply of planting stock. The initial phase is in the preservation of a relatively extensive palm plantation that has been degraded by poor maintenance and management and is not directly related to nursery operations. The number of Palm trees that has been identified for relocation at this stage is circa 10,000 palm trees. A future forecast in entirety could be as much as 30,000 palm trees however a full survey will need to be conducted to confirm the total number depending on mortality rate given no management at this juncture – hence the urgency.";
		textareaScaleSense.sendKeys(strMatter);
		log.info("Scale Sensitivity set");
		
	}
	
	// page 3 methods

	public void setAssignmentTimeframe(String strPAA, String strCategory, String strDate,
			String strComment) throws InterruptedException {

		WebElement element3 = driver.findElement(By.xpath("//*[contains(text(),'Development Details')]"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element3);
		Thread.sleep(2000);

		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Environmental Assessment')]")));

		projectAreaField.sendKeys(strPAA);

		categoryTimeFrame.click();
		driver.findElement(By.xpath("//div[@data-tether-id='1']/following::div[text()='" + strCategory + "']")).click();

		if (strCategory != "Below Assessment Category") {
			dateApproval.sendKeys(strDate);
		}

		commentFieldTimeFrame.get(0).sendKeys(strComment);
	}

	public void clickSubmit() throws InterruptedException {
		WebElement element = driver.findElement(By.xpath("//button[text()='Submit']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		Thread.sleep(2000);

		submitBtn.click();
	}

	public void setUpload() {
		uploadField.sendKeys("C:\\Users\\arjit.yadav\\Desktop\\docs\\process.jpg");
	}

	public void setRemainTextArea() {
		for (int i = 1; i < 6; i++) {
			textAreaFieldsRemaining.get(i).sendKeys("Aliquam porttitor convallis enim ac suscipit. Suspendisse sed finibus metus.");
		}
	}

}
