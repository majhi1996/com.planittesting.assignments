package pages;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class HomePage
{
	public RemoteWebDriver driver;
	
	@FindBy(how=How.XPATH,using="(//*[@class='account'])[1]")
	public WebElement accountId;
	
	@FindBy(how=How.XPATH,using="//*[@class='ico-cart']/span[1]")
	public WebElement shoppingCart; 
	
	@FindBy(how=How.XPATH,using="(//*[@class='ico-cart']/span)[2]")
	public WebElement totalCart;
	
	@FindBy(how=How.XPATH,using="//*[@class='qty nobr']/input") 
	public List<WebElement> quantity; 
	
	@FindBy(how=How.NAME,using="updatecart")
	public WebElement updateCart; 	
	
	@FindBy(how=How.XPATH,using="//*[contains(text(),'Your Shopping Cart is empty!')]")
	public WebElement cartEmptyMessage; 
		        
	@FindBy(how=How.XPATH,using="//*[@alt='Tricentis Demo Web Shop']")
	public WebElement logo;
	
	@FindBy(how=How.XPATH,using="(//*[contains(text(),'Books')])[3]")
	public WebElement book; 
		
	public HomePage(RemoteWebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}
	
	public void clickOnShoppingCart()
	{
		shoppingCart.click();
	}
	
	public void removeQuantity()
	{
		for(int i=0;i<quantity.size();i++)
		{
			quantity.get(i).clear();
			quantity.get(i).sendKeys("0");
		}				
	}	
	
	public void clickOnUpdateCart()
	{
		updateCart.click();
	}
	
	public void clickOnLogo()
	{		
		logo.click();		
	}
	
	public void clickBookFromCategory()
	{
		book.click();
	}
}
