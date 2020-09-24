package pages;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class CheckoutPage 
{
public RemoteWebDriver driver;	
	
//public String item="New Address";

	@FindBy(how=How.ID,using="billing-address-select")
	public WebElement billingDropdown;
	
	@FindBy(how=How.ID,using="shipping-address-select")
	public WebElement shippingDropdown;

	@FindBy(how=How.ID,using="BillingNewAddress_FirstName")
	public WebElement firstname;	
	
	@FindBy(how=How.ID,using="BillingNewAddress_LastName")
	public WebElement lastname; 
	
	@FindBy(how=How.ID,using="BillingNewAddress_Email")
	public WebElement email; 
	
	@FindBy(how=How.XPATH,using="//*[@data-val-number='The field Country must be a number.']")
	public WebElement country;
	
	//String mycountry="India";
	
	@FindBy(how=How.ID,using="BillingNewAddress_City")
	public WebElement city;
	
	@FindBy(how=How.ID,using="BillingNewAddress_Address1")
	public WebElement address1;
	
	@FindBy(how=How.ID,using="BillingNewAddress_ZipPostalCode")
	public WebElement zipcode;
	
	@FindBy(how=How.ID,using="BillingNewAddress_PhoneNumber")
	public WebElement phone;
	
	@FindBy(how=How.XPATH,using="(//*[@value='Continue'])[1]")
	public WebElement continueButton1;  
	
	@FindBy(how=How.XPATH,using="(//*[@value='Continue'])[2]")
	public WebElement continueButton2; 
	
	@FindBy(how=How.XPATH,using="(//*[@type='radio'])[2]")
	public WebElement nextDayAirRadioButton;
	
	@FindBy(how=How.XPATH,using="(//*[@value='Continue'])[3]")
	public WebElement continueButton3; 
	
	@FindBy(how=How.XPATH,using="(//*[@type='radio'])[4]")
	public WebElement cashonDeliveryRadioButton;
	
	@FindBy(how=How.XPATH,using="(//*[@value='Continue'])[4]")
	public WebElement continueButton4; 
	
	@FindBy(how=How.XPATH,using="//*[text()='You will pay by COD']")
	public WebElement paymentVerificationMessage;
	
	@FindBy(how=How.XPATH,using="(//*[@value='Continue'])[5]")
	public WebElement continueButton5;
	
	@FindBy(how=How.XPATH,using="//*[@value='Confirm']")
	public WebElement confirmOrder;
	
	@FindBy(how=How.XPATH,using="//*[@class='title']/strong")
	public WebElement orderSuccessMessage;
	
	@FindBy(how=How.XPATH,using="//*[text()='Click here for order details.']")
	public WebElement orderNumber;
	
	@FindBy(how=How.XPATH,using="//*[@value='Continue']")
	public WebElement continueButton6;
	
	@FindBy(how=How.XPATH,using="//*[text()='Log out']")
	public WebElement logout;
	
	public CheckoutPage(RemoteWebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}
	
	public void selectBillingAddress(String billingAddress)
	{
		Select s=new Select(billingDropdown);
		s.selectByVisibleText(billingAddress);
	}
	
	public void selectShippingAddress(String shippingAddress)
	{
		Select s=new Select(shippingDropdown);
		s.selectByVisibleText(shippingAddress);
	}
	
	public void enterFirstName(String firstName)
	{
		firstname.clear();
		firstname.sendKeys(firstName);
	}
	
	public void enterLastName(String lastName)
	{
		lastname.clear();
		lastname.sendKeys(lastName);
	}
	
	public void enterEmailId(String emailId)
	{
		email.clear();
		email.sendKeys(emailId);
	}
	
	public void selectCountry(String countryName)
	{
		Select s=new Select(country);
		s.selectByVisibleText(countryName);
	}
	
	public void enterCity(String cityName)
	{
		city.sendKeys(cityName);
	}
	
	public void enterAddress(String address)
	{
		address1.sendKeys(address);
	}
	
	public void enterZip(String pincode)
	{
		zipcode.sendKeys(pincode);
	} 
	
	public void enterPhone(String mobileNumber)
	{
		phone.sendKeys(mobileNumber);
	}
	
	public void clickOnContinueOfBillingAddress()
	{
		continueButton1.click();
	}
	
	public void clickOnContinueOfShippingAddress()
	{
		continueButton2.click();
	}
	
	public void selectShippingMethod()
	{
		nextDayAirRadioButton.click();		
	}
	
	public void clickOnContinueOfShippingMethod()
	{
		continueButton3.click();
	}
	
	public void choosePaymentMethod()
	{
		cashonDeliveryRadioButton.click();
	}
	
	public void clickOnContinueOfPaymentMethod()
	{
		continueButton4.click();
	}
	
	public void clickOnContinueOfPaymentInfo()
	{
		continueButton5.click(); 
	}
	
	public void clickOnConfirm()
	{
		confirmOrder.click();
	}
	
	public void clickOnContinueAfterSuccessfulOrder()
	{
		continueButton6.click();
	}
	
	public void clickOnLogout()
	{
		logout.click();
	}
	

}
