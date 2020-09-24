package utilities;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestUtility 
{
	RemoteWebDriver driver;
	
	public TestUtility()
	{
		driver=null;
	}
	
	public RemoteWebDriver openBrowser(String bn)
	{
		if(bn.equalsIgnoreCase("chrome"))
		{
			System.setProperty("webdriver.chrome.silentOutput","true");
			WebDriverManager.chromedriver().setup();
			driver=new ChromeDriver();
			driver.manage().window().maximize();
		}
		else if(bn.equalsIgnoreCase("opera"))
		{
			WebDriverManager.operadriver().setup();
			driver=new OperaDriver();
			driver.manage().window().maximize();
		}
		else if(bn.equalsIgnoreCase("edge"))
		{
			WebDriverManager.edgedriver().setup();
			driver=new EdgeDriver();
			driver.manage().window().maximize();
		}
		else
		{
			WebDriverManager.iedriver().setup();
			driver=new InternetExplorerDriver();
			driver.manage().window().maximize();
		}
		return(driver);
	}
	
	public void launchSite(String url)
	{
		driver.get(url);
	}
	
	public String screenshot() throws Exception
	{
		SimpleDateFormat sf=new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
		Date d=new Date();
		String fname=sf.format(d)+".png";
		File src=driver.getScreenshotAs(OutputType.FILE);
		File dest=new File(fname);
		FileHandler.copy(src,dest);
		return(dest.getAbsolutePath());
	}
	
	public void closeSite()
	{
		driver.quit();
	}
}
