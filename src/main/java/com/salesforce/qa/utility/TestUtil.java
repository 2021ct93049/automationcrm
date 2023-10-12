package com.salesforce.qa.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;

import com.salesforce.qa.base.TestBase;


public class TestUtil extends TestBase {

	public static long PAGE_LOAD_TIMEOUT = 60;
	public static long IMPLICIT_WAIT = 30;
	static Workbook book;
	static Sheet sheet;
	static JavascriptExecutor js;

	/*
	 * takeScreenshotAtTest - method is to capture the screen shot
	 */
	public static String takeScreenshotAtTest() throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String currentDir = System.getProperty("user.dir");
		String screenShotPath=System.getProperty("user.dir")+"/screenshots/"+System.currentTimeMillis()+".png";
		File destination=new File(screenShotPath);
		try 
		{
			FileUtils.copyFile(scrFile, destination);
			System.out.println("Screenshot captured");
		} catch (IOException e) 
		{
			System.out.println("Capture Failed "+e.getMessage());
		}
		
		return screenShotPath;
	}
	
	/*
	 * method is to verify driver has clicked on New button
	 */
	public void verifyNewClicked(String value){
		try {
		String xpath1 = "//h2[text()='New ";
		String xpath2 = ""+value+"']";
		WebElement element = driver.findElement(By.xpath(xpath1+xpath2));
		String text = element.getText();
		if(text.contains(value))
		{
			System.out.println("Text on New Page is"+ text);
			System.out.println("New" +value+"page opened");
		}else
		{
			System.out.println("New" +value+"page not opened");
		}
		}catch (NoSuchElementException exc){
			exc.printStackTrace();
		}
	}
	
	/*
	 * isElementPresent - Method is to verify HTML elemt is present on the web page or not
	 */
	public boolean isElementPresent(String locator){
		try{
			driver.findElement(By.xpath(locator));        
			return true;
		} catch (NoSuchElementException exc){
			exc.printStackTrace();
		}
		return false;
	}

	/*
	 * select - Method is to select the values from dropdown when HTML element contains Select class
	 */
	public void select(String locator, String value){
		WebElement dropdown = driver.findElement(By.xpath(locator));
		Select select = new Select(dropdown);
		select.selectByVisibleText(value);
	}

	/*
	 * selectFromList - Method is to select the values from List
	 */
	public void selectFromList(String labelName,String value){
		js = (JavascriptExecutor)driver;
		String xpapth = "//label[contains(text(),'"+labelName+"')]/../descendant::button/span";
		String listXpath = "//label[contains(text(),'"+labelName+"')]/../descendant::div//following-sibling::span/span";
		WebElement dropdown = driver.findElement(By.xpath(xpapth));
		js.executeScript("arguments[0].click();", dropdown);
		//dropdown.click();
		implicitWait(100);
		
		List<WebElement> options = driver.findElements(By.xpath(listXpath));
		int count = options.size();
		for(int i=0;i<count;i++) {
			 String text= options.get(i).getText();
			 if(text.equalsIgnoreCase(value)) {
				js.executeScript("arguments[0].click();", options.get(i));
				System.out.println(value +" value selected from dropdown for field "+ labelName);
				logger.pass(value +" value selected from dropdown for field "+ labelName);
				 //options.get(i).click();
				 break;
			 }
		}
	}
	/*
	 * getPageTitle - method is to get the title of web page
	 */
	public String getPageTitle(){
		return driver.getTitle();
	}

	/*
	 * waitForPageLoad - method is to wait for page load
	 */
	public void waitForPageLoad(int attValue) throws InterruptedException {
		Thread.sleep(attValue);
	}
	/*
	 * WebDriverWaitForElement - Method is to wait for element at particular condition
	 */
	public WebElement WebDriverWaitForElement(WebElement WebElement, long waitingTimeinsec) throws Exception
	{
		WebElement element=null;
		try {
			WebDriverWait wait = new WebDriverWait(driver, waitingTimeinsec);
			element = wait.until(ExpectedConditions.elementToBeClickable(WebElement));
			return element;
		}
		catch(NoSuchElementException e)
		{
			e.printStackTrace();
			System.out.println("Could not find the element even after waiting explicitly for ("+waitingTimeinsec+")sec");
			return element;
		}
	}
	
	/*
	 * click - Method is to click on particular web element
	 */
	public void click(String xpath, String value){
        WebElement element = driver.findElement(By.xpath(xpath));
        element.click();
        implicitWait(100);
    }
	
	/*
	 * verifyEditPage - method is to verify whether the page is edited or not
	 */
	public void verifyEditPage(){
		String xpath = "//div[@data-aura-class='forceDetailPanelDesktop']//h2";
        WebElement element = driver.findElement(By.xpath(xpath));
        String getText = element.getText();
        if(getText.contains("Edit"))
        {
        	Assert.assertTrue(true);
        	logger.info("landed on " +getText);
        }
        implicitWait(50);
    }
	
	/*
	 * WaitForElement - method is to wait for an element to search
	 */
	public boolean WaitForElement(String xpath, long waitingTimeinsec) throws Exception
	{
		try {

			driver.manage().timeouts().implicitlyWait(waitingTimeinsec,TimeUnit.SECONDS);
			List<WebElement> myDynamicElement = driver.findElements(By.xpath(xpath));
			if (myDynamicElement.size() > 0)
			{
				System.out.println("Success: WaitForElement->Number of Element present is: "+myDynamicElement.size() +"Xpath is:"+xpath);
				return true;
			}
			else
			{
				System.out.println("Unsuccess: WaitForElement->Number of Element present is: "+myDynamicElement.size()+"Xpath is:"+xpath);
				return false;
			}
		}
		catch(NoSuchElementException e)
		{
			e.printStackTrace();
			System.out.println("Exception inside WaitForElement:"+xpath);
			return false;
		}
	}
	
}


/*	    public void tearDown(ITestResult result) throws IOException{

if(result.getStatus() == ITestResult.FAILURE) {
	   logger.fail("Test Failed", MediaEntityBuilder.createScreenCaptureFromPath(TestUtil.takeScreenshotAtTest()).build());
}
else if(result.getStatus() == ITestResult.SUCCESS) {
	   logger.fail("Test Passed", MediaEntityBuilder.createScreenCaptureFromPath(TestUtil.takeScreenshotAtTest()).build());
}
/*
	 * public void attachScreenShot(ITestResult result) { try {
	 * if(result.getStatus() == ITestResult.FAILURE) {
	 * 
	 * logger.fail("Test Failed",
	 * MediaEntityBuilder.createScreenCaptureFromPath(TestUtil.takeScreenshotAtTest(
	 * )).build());
	 * 
	 * } else if(result.getStatus() == ITestResult.SUCCESS) {
	 * logger.pass("Test Passed",
	 * MediaEntityBuilder.createScreenCaptureFromPath(TestUtil.takeScreenshotAtTest(
	 * )).build()); } }catch (IOException e) { e.printStackTrace(); } }
	 
report.flush();
	/*
	public void click(String locator){
		WebElement element = driver.findElement(By.xpath(locator));
		element.click();
	}
	 * public void type(String locator, String value){ WebElement element =
	 * driver.findElement(By.xpath(locator)); element.sendKeys(value); }
	 * 
	 * public String getLabelText(String locator){ WebElement element =
	 * driver.findElement(By.xpath(locator)); return element.getText(); }
	 	public void switchToFrame() {
		driver.switchTo().frame("mainpanel");
	}
}*/
//accountsPage.clickAppLauncher();
		//accountsPage.clickViewAll();
//logger = report.createTest("Account Creation", "It's will create an Account.");
