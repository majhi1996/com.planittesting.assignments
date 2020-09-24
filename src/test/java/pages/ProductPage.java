package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class ProductPage 
{
public RemoteWebDriver driver;	
	
	@FindBy(how=How.XPATH,using="(//*[@class='picture'])[1]")
	public WebElement anyBook;	
	
	@FindBy(how=How.XPATH,using="//*[@class='product-price']/span")
	public WebElement getPrice; 
	
	@FindBy(how=How.NAME,using="addtocart_13.EnteredQuantity")
	public WebElement quantityBox; 
	
	@FindBy(how=How.ID,using="add-to-cart-button-13") 
	public WebElement addToCart;
	
	@FindBy(how=How.XPATH,using="//*[text()='The product has been added to your ']")
	public WebElement successfulAddToCart;  
	
	@FindBy(how=How.XPATH,using="//*[@class='ico-cart']/span[1]")
	public WebElement shoppingCart; 
	
	@FindBy(how=How.XPATH,using="//*[@class='unit-price nobr']/span[2]")
	public WebElement price;
	
	@FindBy(how=How.XPATH,using="//*[@class='qty-input']")
	public WebElement quantity;
	
	@FindBy(how=How.XPATH,using="//*[@class='product-subtotal']")
	public WebElement total;
	
	@FindBy(how=How.ID,using="termsofservice")
	public WebElement checkbox;
	
	@FindBy(how=How.ID,using="checkout")
	public WebElement checkoutButton;	
	
	
	public ProductPage(RemoteWebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}
	
	public void clickAnyBook()
	{
		anyBook.click();
	}
	
	public void enterQuantity(String value)
	{
		quantityBox.clear();
		quantityBox.sendKeys(value);
	}
	
	public void clickOnAddToCart()
	{
		addToCart.click();
	}	
	
	public void clickOnShoppingCart()
	{
		shoppingCart.click();
	}
	
	public void selectAgreeCheckbox()
	{
		checkbox.click();
	}
	
	public void clickOnCheckoutButton()
	{
		checkoutButton.click();
	}
}
