package tests;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import pages.CheckoutPage;
import pages.HomePage;
import pages.LoginPage;
import pages.ProductPage;
import utilities.TestUtility;

public class TestRunner 
{
	//Define Global properties
	public RemoteWebDriver driver;
	public Properties prop;
	public WebDriverWait wait;
	public TestUtility utility;
	public LoginPage loginPage;
	public HomePage homePage;
	public ProductPage productPage;
	public CheckoutPage checkoutPage;
	public ExtentReports extentReport;
	public ExtentTest extentTest;
	
	//This will run before @Test annotated method. 
	//To load corresponding files before running @Test annotated method. 
	@BeforeTest
	public void loadFiles() throws Exception
	{
		File file=new File("D:\\Selenium4\\com.planittesting.assignment\\src\\test\\resources\\properties\\propertyfile.properties");
		FileInputStream fileInputStream=new FileInputStream(file);
		prop=new Properties();
		prop.load(fileInputStream);
		utility=new TestUtility();
		extentReport=new ExtentReports(System.getProperty("user.dir")+"\\TestReports.html",true);
		extentTest=extentReport.startTest("Validate Demo Web Shop Application !!");				
	}
	
	//Open browser and launch site
	@Test(priority=1)
	public void openbrowserAndLaunchSite()
	{
		driver=utility.openBrowser(prop.getProperty("browserName"));
		loginPage=new LoginPage(driver);
		homePage=new HomePage(driver);
		productPage=new ProductPage(driver);
		checkoutPage=new CheckoutPage(driver);
		utility.launchSite(prop.getProperty("url")); 
		wait=new WebDriverWait(driver,20);
	}
	
	//Validate Welcome, Please Sign In! Message
	@Test(priority=2)
	public void validateLoginMessage() throws Exception
	{
		wait.until(ExpectedConditions.visibilityOf(loginPage.loginText));
		loginPage.clickOnLoginText();
		wait.until(ExpectedConditions.visibilityOf(loginPage.successfulLogin));
		if(loginPage.successfulLogin.isDisplayed())
		{
			extentTest.log(LogStatus.PASS,"Please Sign In !! Message was displayed");
		}
		else
		{
			extentTest.log(LogStatus.FAIL,"Please Sign In !! Message was not displayed "+extentTest.addScreenCapture(utility.screenshot()));
		}
	}
	
	//Login with valid credentials
	@Test(priority=3)
	public void loginWithValidCredentials()
	{						
		loginPage.fillEmail(prop.getProperty("loginEmail"));
		loginPage.fillPassword(prop.getProperty("loginPassword"));
		loginPage.clickLoginButton();
	}
	
	//Validate the account of logged in user
	@Test(priority=4)
	public void validateUserAccount() throws Exception
	{
		wait.until(ExpectedConditions.visibilityOf(homePage.accountId));
		String actualAccountId=homePage.accountId.getText();
		if(actualAccountId.equals(prop.getProperty("loginEmail")))
		{
			extentTest.log(LogStatus.PASS,"Logged in as same user !!");
		}
		else
		{
			extentTest.log(LogStatus.FAIL,"Logged in as a different user !! "+extentTest.addScreenCapture(utility.screenshot()));
		}
	}
		
	//Clear the shopping cart if any. If no then continue further !!
	@Test(priority=5)
	public void clearShoppingCart()
	{
		String totalProduct=homePage.totalCart.getText();
		totalProduct=totalProduct.replace("(","");
		totalProduct=totalProduct.replace(")","");
		int tp=Integer.parseInt(totalProduct);
		if(tp==0)
		{
			extentTest.log(LogStatus.PASS,"Shopping cart is empty !!");			
		}
		else
		{
			extentTest.log(LogStatus.PASS,"Shopping cart is not empty !!");
			homePage.clickOnShoppingCart();
			wait.until(ExpectedConditions.visibilityOf(homePage.updateCart));
			homePage.removeQuantity();
			homePage.clickOnUpdateCart(); 
			wait.until(ExpectedConditions.visibilityOf(homePage.cartEmptyMessage));
			homePage.clickOnLogo();
		}		
	}
		
	//Select a book, display its price, enter quantity more than 1 and add to cart
	@Test(priority=6)
	public void selectBookAndAddToCart()
	{
		wait.until(ExpectedConditions.visibilityOf(homePage.book));
		homePage.clickBookFromCategory();
		wait.until(ExpectedConditions.visibilityOf(productPage.anyBook));
		productPage.clickAnyBook();
		wait.until(ExpectedConditions.visibilityOf(productPage.getPrice));
		String priceofbook=productPage.getPrice.getText();
		System.out.println("Price of book is: "+priceofbook);
		productPage.enterQuantity(prop.getProperty("quantity"));
		productPage.clickOnAddToCart();
	}
	
	//Validate "The product has been added to shopping cart" Message
	@Test(priority=7)
	public void validateAddedToCartSuccessfully() throws Exception
	{
		try
		{
			wait.until(ExpectedConditions.visibilityOf(productPage.successfulAddToCart)).isDisplayed();
			extentTest.log(LogStatus.PASS,"Product has been successfully added to your cart !!");
		}
		catch(Exception ex)
		{
			extentTest.log(LogStatus.FAIL,"Product has not been added successfully !! "+extentTest.addScreenCapture(utility.screenshot()));
		}
	}
	
	//Validate the “Sub-Total” Price for selected book
	@Test(priority=8)
	public void validateSubTotal() throws Exception
	{
		productPage.clickOnShoppingCart();
		wait.until(ExpectedConditions.visibilityOf(homePage.updateCart)); 
		String price=productPage.price.getText();
		String quantity=productPage.quantity.getAttribute("value");
		String total=productPage.total.getText();
		float actualprice=Float.parseFloat(price);
		float actualquantity=Float.parseFloat(quantity);
		float expectedTotal=(actualprice*actualquantity);
		System.out.println("Total price is: "+expectedTotal+"0"); 
		if(total.equals(expectedTotal+"0"))
		{
			extentTest.log(LogStatus.PASS,"Actual and expected total price are equal");
		}
		else
		{
			extentTest.log(LogStatus.FAIL,"Actual and expected total price are not equal "+extentTest.addScreenCapture(utility.screenshot()));
		}
	}
	
	//Select “New Address” From “Billing Address” drop down and fill the mandatory fields
	@Test(priority=9)
	public void billingAddress()
	{
		productPage.selectAgreeCheckbox();
		productPage.clickOnCheckoutButton();
		wait.until(ExpectedConditions.visibilityOf(checkoutPage.billingDropdown));
		checkoutPage.selectBillingAddress(prop.getProperty("dropdownList"));
		wait.until(ExpectedConditions.visibilityOf(checkoutPage.firstname));
		checkoutPage.enterFirstName(prop.getProperty("firstName"));
		checkoutPage.enterLastName(prop.getProperty("lastName"));
		checkoutPage.enterEmailId(prop.getProperty("email"));
		checkoutPage.selectCountry(prop.getProperty("country"));
		checkoutPage.enterCity(prop.getProperty("city"));
		checkoutPage.enterAddress(prop.getProperty("address"));
		checkoutPage.enterZip(prop.getProperty("zip"));
		checkoutPage.enterPhone(prop.getProperty("phone"));
		checkoutPage.clickOnContinueOfBillingAddress();
	}
	
	//Select the “Shipping Address” as same as “Billing Address” from “Shipping Address” drop down and click on “Continue”
	@Test(priority=10)
	public void shippingAddress()
	{
		wait.until(ExpectedConditions.visibilityOf(checkoutPage.shippingDropdown));
		String shippingadd=prop.getProperty("firstName")+" "+prop.get("lastName")+", " +prop.getProperty("city")+", "+prop.getProperty("address")+" "+prop.getProperty("zip")+", "+ prop.getProperty("country");
		checkoutPage.selectShippingAddress(shippingadd);
		checkoutPage.clickOnContinueOfShippingAddress();
	}
	
	//Select the shipping method as “Next Day Air” and click on “Continue”
	@Test(priority=11)
	public void shippingMethod()
	{
		wait.until(ExpectedConditions.visibilityOf(checkoutPage.nextDayAirRadioButton));
		checkoutPage.selectShippingMethod();
		checkoutPage.clickOnContinueOfShippingMethod();
	}
	
	//Choose the payment method as COD (Cash on delivery) and click on “Continue”
	@Test(priority=12)
	public void paymentMethod()
	{
		wait.until(ExpectedConditions.visibilityOf(checkoutPage.cashonDeliveryRadioButton));
		checkoutPage.choosePaymentMethod();
		checkoutPage.clickOnContinueOfPaymentMethod();
	}
	
	//Validate the message “You will pay by COD” and click on “Continue”
	@Test(priority=13)
	public void validateCODMessage() throws Exception
	{
		wait.until(ExpectedConditions.visibilityOf(checkoutPage.paymentVerificationMessage));
		if(checkoutPage.paymentVerificationMessage.isDisplayed())
		{
			extentTest.log(LogStatus.PASS,"Cash On Delivery message was displayed !!");
		}
		else
		{
			extentTest.log(LogStatus.FAIL,"Cash On Delivery message was not displayed !!"+extentTest.addScreenCapture(utility.screenshot()));
		}
		checkoutPage.clickOnContinueOfPaymentInfo();
	}
	
	//Click on “Confirm Order”
	@Test(priority=14)
	public void confirmOrder()
	{
		wait.until(ExpectedConditions.visibilityOf(checkoutPage.confirmOrder));
		checkoutPage.clickOnConfirm();
	}
	
	//Validate the message “Your order has been successfully processed!” 
	@Test(priority=15)
	public void validateOrderPlaced() throws Exception
	{
		wait.until(ExpectedConditions.visibilityOf(checkoutPage.orderSuccessMessage));
		if(checkoutPage.orderSuccessMessage.isDisplayed())
		{
			extentTest.log(LogStatus.PASS,"Order was placed successfully !!");
		}
		else
		{
			extentTest.log(LogStatus.FAIL,"Order was not placed successfully !!"+extentTest.addScreenCapture(utility.screenshot()));
		}
	}
	
	//Print the Order number
	@Test(priority=16)
	public void displayOrderNumber()
	{
		wait.until(ExpectedConditions.visibilityOf(checkoutPage.orderNumber));
		String orderNumber=checkoutPage.orderNumber.getAttribute("href");
		String[] ordNum=orderNumber.split("/");
		System.out.println("Order Number is: "+ordNum[4]);
	}
	
	//Click on “Continue” and log out from the application 	 
	@Test(priority=17)
	public void logout()
	{
		checkoutPage.clickOnContinueAfterSuccessfulOrder();
		wait.until(ExpectedConditions.visibilityOf(checkoutPage.logout));
		checkoutPage.clickOnLogout();		
	}
	
	//This will run After @Test annotated method.
	//Save the reports and close site
	@AfterTest
	public void saveAndclose()
	{
		extentReport.endTest(extentTest);
		extentReport.flush();
		utility.closeSite();		
	}
}
