package pageFactory;


import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Utilities;

public class LoginPageElements {
	
	WebDriver driver;
	Utilities util;
	Logger log = Logger.getLogger(LoginPageElements.class);
	
	@FindBy(id="un")
	WebElement usernameField;
	
	@FindBy(id="pw")
	WebElement passwordField;
	
	@FindBy(xpath="//input[@value='Sign In']")
	WebElement signInBtn;
	
	@FindBy(id="forgotPasswordLink")
	WebElement forgotPassLink;
	
	@FindBy(id="remember")
	WebElement rememberMeChkBox;
	
	public LoginPageElements(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		util = new Utilities(driver);
	}

	public void login(String strUsername,String strPassword) throws Exception {
		usernameField.sendKeys(strUsername);
		log.info("username set to "+strUsername);
		
		passwordField.sendKeys(strPassword);
		log.info("password set to "+strPassword);
		util.takeSnapShot();
		
		signInBtn.click();
		log.info("Signin button clicked");
		
		
	}
	
}
