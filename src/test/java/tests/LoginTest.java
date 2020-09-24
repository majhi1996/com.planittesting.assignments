package tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.TestUtility;

public class LoginTest 
{
	public static void main(String[] args) throws Exception
	{
		//Get browser name & test data from excel file
		File f=new File("LoginData.xls");
		FileInputStream fi=new FileInputStream(f);
		Workbook wb=WorkbookFactory.create(fi);
		Sheet sh=wb.getSheet("Sheet1");
		int nour=sh.getPhysicalNumberOfRows();
		int nouc=sh.getRow(0).getLastCellNum();
					
		//Create result column with current date & time as heading
		SimpleDateFormat sf=new SimpleDateFormat("dd-MMM-yyyy-hh-mm-ss");
		Date dt=new Date();
		Cell rc=sh.getRow(0).createCell(nouc);
		rc.setCellValue(sf.format(dt));
		sh.autoSizeColumn(nouc);
					
		//Define wrap text for remaining cells
		CellStyle cs=wb.createCellStyle();
		cs.setWrapText(true);
						
		//Create object to Utility Class
		TestUtility obj=new TestUtility();
		
		//Login functional testing with multiple test data
		for (int i=1;i<nour;i++)
		{
			try
			{
				//Get Data from Excel Sheet(LoginData.xls)
				DataFormatter df=new DataFormatter();
				String browsername=df.formatCellValue(sh.getRow(i).getCell(0));
			    String email=df.formatCellValue(sh.getRow(i).getCell(1));
				String emailcriteria=df.formatCellValue(sh.getRow(i).getCell(2));
				String pwd=df.formatCellValue(sh.getRow(i).getCell(3));
				String pwdcriteria=df.formatCellValue(sh.getRow(i).getCell(4));
				
				//Open browser
				RemoteWebDriver driver=obj.openBrowser(browsername);
				obj.launchSite("http://demowebshop.tricentis.com/login");
				WebDriverWait w=new WebDriverWait(driver,20);
				w.until(ExpectedConditions.visibilityOfElementLocated(By.name("Email")));
				driver.findElement(By.name("Email")).sendKeys(email);
				driver.findElement(By.name("Password")).sendKeys(pwd);
				driver.findElement(By.xpath("//*[@value='Log in']")).click();
				//Validatons
				if(email.length()==0 && emailcriteria.equalsIgnoreCase("invalid"))
				{
					try
					{
						w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Login was unsuccessful.')]")));
						Cell c=sh.getRow(i).createCell(nouc);
						c.setCellStyle(cs);
						c.setCellValue("Blank Email Test Passed");
					}
					catch(Exception ex)
					{
						Cell c=sh.getRow(i).createCell(nouc);
						c.setCellStyle(cs);
						c.setCellValue("Blank Email Test Failed and see: "+obj.screenshot());
					}						
				}
				else if(email.length()!=0 && emailcriteria.equalsIgnoreCase("invalid"))
				{
					try
					{	
						w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Login was unsuccessful.')]")));
						Cell c=sh.getRow(i).createCell(nouc);
						c.setCellStyle(cs);
						c.setCellValue("Invalid Email Test Passed");						
					}
					catch(Exception ex)
					{
						Cell c=sh.getRow(i).createCell(nouc);
						c.setCellStyle(cs);
						c.setCellValue("Invalid Email Test Failed and see: "+obj.screenshot());			
					}
				}
				else if(pwd.length()==0 && pwdcriteria.equalsIgnoreCase("invalid"))
				{
					try
					{
						w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Login was unsuccessful.')]")));
						Cell c=sh.getRow(i).createCell(nouc);
						c.setCellStyle(cs);
						c.setCellValue("Blank Password Test Passed");
					}
					catch(Exception ex)
					{
						Cell c=sh.getRow(i).createCell(nouc);
						c.setCellStyle(cs);
						c.setCellValue("Blank Password Test Failed and see: "+obj.screenshot());			
					}							
				}
				else if(pwd.length()!=0 && pwdcriteria.equalsIgnoreCase("invalid"))
				{
					try
					{
						w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Login was unsuccessful.')]")));
						Cell c=sh.getRow(i).createCell(nouc);
						c.setCellStyle(cs);
						c.setCellValue("Invalid Password Test Passed");
					}
					catch(Exception ex)
					{
						Cell c=sh.getRow(i).createCell(nouc);
						c.setCellStyle(cs);
						c.setCellValue("Invalid Password Test Failed and see: "+obj.screenshot());			
					}							
				}
				else 
				{
					try
					{
						if(email.length()!=0 && emailcriteria.equalsIgnoreCase("valid") && pwd.length()!=0 && pwdcriteria.equalsIgnoreCase("valid"))
						{								
							w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Welcome to our store')]")));
							Cell c=sh.getRow(i).createCell(nouc);
							c.setCellStyle(cs);
							c.setCellValue("Valid Email & valid Password Test Passed");
						}
					}
					catch(Exception ex)
					{
						Cell c=sh.getRow(i).createCell(nouc);
						c.setCellStyle(cs);
						c.setCellValue("Valid Email & valid Password Test Failed and see: "+obj.screenshot());			
					}							
				}
			}
			catch(Exception ex)
			{
				Cell c=sh.getRow(i).createCell(nouc);
				c.setCellStyle(cs);
				c.setCellValue(ex.getMessage());							
			}	
			//close site
			obj.closeSite();
		}												
		//Save & close excel file
		FileOutputStream fo=new FileOutputStream(f);
		wb.write(fo);
		wb.close();
		fo.close();
		fi.close();
	}
}
