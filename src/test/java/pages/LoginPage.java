package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class LoginPage 
{
public RemoteWebDriver driver;
	
	@FindBy(how=How.XPATH,using="//*[text()='Log in']")
	public WebElement loginText;
	
	@FindBy(how=How.XPATH,using="//*[text()='Welcome, Please Sign In!']")
	public WebElement successfulLogin;
	
	@FindBy(how=How.NAME,using="Email")
	public WebElement emailId;
	
	@FindBy(how=How.NAME,using="Password")
	public WebElement password;
	
	@FindBy(how=How.XPATH,using="//*[@value='Log in']")
	public WebElement loginButton;
	
	public LoginPage(RemoteWebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}
	
	public void clickOnLoginText()
	{
		loginText.click();
	}
	
	public void fillEmail(String email)
	{
		emailId.sendKeys(email);
	}
	
	public void fillPassword(String pwd)
	{
		password.sendKeys(pwd);
	}	
	
	public void clickLoginButton()
	{
		loginButton.click();
	}
}
