package com.salesforce.qa.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.salesforce.qa.utility.DateUtils;
import com.salesforce.qa.utility.TestUtil;


public class TestBase {

	public static WebDriver driver;
	public static Properties prop;
	public static Properties prop1;
	public static WebDriverWait wait;
	static JavascriptExecutor js;
	public static ExtentTest logger;
	
	public static ExtentHtmlReporter extent = new ExtentHtmlReporter(
			System.getProperty("user.dir") + "\\Report\\ExtentReport_" + DateUtils.getTimeStamp() + ".html");
	public static ExtentReports report = new ExtentReports();

	public static final String PROJDIR = System.getProperty("user.dir");
	private static final String ORFILE_PATH = PROJDIR
			+ "\\src\\main\\java\\com\\salesforce\\qa\\config\\objectrepo.properties";


	public static Properties config = new Properties();
	public static Properties objectrepo = new Properties();
	private static FileInputStream fis;

	public TestBase() {
		try {
			
			prop = new Properties();
			FileInputStream ip = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\main\\java\\com\\salesforce\\qa\\config\\config.properties");
			prop.load(ip);

			FileInputStream fis = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\main\\java\\com\\salesforce\\qa\\config\\objectrepo.properties");
			objectrepo.load(fis);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void initialization(String objectName) {
		report.attachReporter(extent);
		logger = report.createTest(objectName +" creation");

		try {
		String browserName = prop.getProperty("browser");

		String chromePath = System.getProperty("user.dir")
				+ "\\src\\main\\resources\\executables\\chromedriver\\chromedriver.exe";
		String geckoPath = System.getProperty("user.dir")
				+ "src\\main\\resources\\executables\\geckodriver\\geckodriver.exe";

		if (browserName.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", chromePath);
			ChromeOptions co = new ChromeOptions();
			co.addArguments("--remote-allow-origins=*");
			co.addArguments("--disable-extensions");
			driver = new ChromeDriver(co);
		} else if (browserName.equals("FF")) {
			System.setProperty("webdriver.gecko.driver", geckoPath);
			driver = new FirefoxDriver();
		}
	
		
		Thread.sleep(1000);
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);
		
		driver.get(prop.getProperty("url"));
		Thread.sleep(2000);
		driver.manage().window().maximize();
		Thread.sleep(2000);
		screenShotMsg("Driver Maiximized");
		
		//logger.log(Status.INFO, "Salesforce Website is opened");
		//screenShotMsg("Launched Salesforce");
		} catch (Exception e) {
		} 	
	}
	
		
	/*
	 * switchToClassic method is to swicth to Classic mode
	 */

	public void switchToClassic() {
		try {
		String currUrl = driver.getCurrentUrl();
		if (currUrl.contains("lightning")) {
			driver.findElement(By.xpath(prop.getProperty("Profile_Xpath"))).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath(prop.getProperty("switch2Classic_Xpath"))).click();
			Thread.sleep(1000);
			System.out.println("Successfully Switched to the Classic version of salesforce");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * findElementByLocator - This method is created to inspect the HTML elements.
	 */
	private WebElement findElementByLocator(String locator) {
		WebElement element = null;
			
		if (locator.endsWith("_CSS")) {
			element = driver.findElement(By.cssSelector(objectrepo.getProperty(locator)));
		} else if (locator.endsWith("_XPATH")) {
			element = driver.findElement(By.xpath(objectrepo.getProperty(locator)));
		} else if (locator.endsWith("_ID")) {
			element = driver.findElement(By.id(objectrepo.getProperty(locator)));
		} else if (locator.endsWith("_TAG")) {
			element = driver.findElement(By.tagName(objectrepo.getProperty(locator)));
		} else if (locator.endsWith("_CLASS")) {
			element = driver.findElement(By.className(objectrepo.getProperty(locator)));
		} else if (locator.endsWith("_LINK")) {
			element = driver.findElement(By.linkText(objectrepo.getProperty(locator)));
		}
		
    	return element;
	}

	/*
	 * verifyActualWithExpectedText - Method is created to verify Actual Text with Expected Text
	 */
	public void verifyActualWithExpectedText(String locator, String ActualName) throws Exception {
		try {
		String getText = findElementByLocator(locator).getText().trim();
		System.out.println(getText +" is Actual Text");
		implicitWait(20);
		if (getText.equalsIgnoreCase(ActualName)) {
			System.out.println("Actual Text matches with the Expected Test");
		} else {
			System.out.println("Actual Text does not match with the Expected Test");
		}
		
		}catch (Exception e)
		{
			 e.printStackTrace();
			}
	}

	/*
	 * selectDropdown - Method is to select the values from drodpwn when it doesn't have Select class
	 */
	public void selectDropdown(String locator, String dropdownValue) {
		try {
		js = (JavascriptExecutor)driver;
		WebElement element = findElementByLocator(locator);
		// new Actions(driver).moveToElement(element).perform();
		js.executeScript("arguments[0].click();", element);
		Thread.sleep(500);
		String locator2 = "(//span[contains(@class,'slds-truncate')]/descendant-or-self::*[text()='" + dropdownValue+ "'])[1]";
		WebElement selectValue = driver.findElement(By.xpath(locator2));
		String actualVal = selectValue.getText();
		if (actualVal.equalsIgnoreCase(dropdownValue)) {
			//selectValue.click();
			js.executeScript("arguments[0].click();", selectValue);
			System.out.println("Value selected as" +dropdownValue);
			Thread.sleep(500);
		}
		}catch (Exception e)
		{
			 e.printStackTrace();
			}

	}
	/*
	 * dropDownSelect - Method is created to select the values from dropdwon for XPATH has buttons
	 */
	public void dropDownSelect(String locator, String value)
	{
		try {
		js = (JavascriptExecutor)driver;
		WebElement element = findElementByLocator(locator);
		// new Actions(driver).moveToElement(element).perform();
		element.click();
		implicitWait(200);
		String xpath2 = "//a[@title='"+value+"']";
		WebElement selectValue = driver.findElement(By.xpath(xpath2));
		String actualVal = selectValue.getText();
		if (actualVal.equalsIgnoreCase(value)) {
			//selectValue.click();
			js.executeScript("arguments[0].click();", selectValue);
			implicitWait(90);
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	/*
	 * implicitWait - method is to wait for an element to interact
	 */
	public void implicitWait(int value) {
		driver.manage().timeouts().implicitlyWait(value, TimeUnit.SECONDS);
	}

	/*
	 * Method is created to logg off from the application
	 */
	public void logOff() throws Exception {
		try {

			String profile = "//button[contains(@class,'userProfile')][1]";
			Thread.sleep(2000);
			driver.findElement(By.xpath(profile)).click();
			Thread.sleep(1500);
			Thread.sleep(3500);
			driver.findElement(By.xpath("(//a[text()='Log Out'])[1]")).click();
			screenShotMsg("Logging out from salesforce Lightning");
			//System.out.println("Logging out from salesforce Lightning");
		
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Unable to Logout from Salesforce");
		}

	}
	
	/*
	 * editPage - Method is created to edit the page
	 */
	public void editPage() {
		try {

			String editObject = "(//ul[@class='slds-button-group-list'])[1]/li[4]//button";
			String editxpath = "//a[@role='menuitem']/span[contains(text(),'Edit')]";

			WebElement editbutton = driver.findElement(By.xpath(editObject));
			editbutton.click();
			Thread.sleep(1000);
			System.out.println("Cliked on edit option");
			WebElement clickEdit = driver.findElement(By.xpath(editxpath));
			clickEdit.click();
			Thread.sleep(1000);
			System.out.println("Cliked on edit button now");
			//screenShotMsg("page Edited");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Unable to Logout from Salesforce");
		}
	}
	/*
	 * type - method is created to send the values to Text fields
	 */
	public void type(String locator, String value) {
		try {
		WebElement element = findElementByLocator(locator);
		element.sendKeys(value);
		Thread.sleep(250);
		System.out.println("Sending value to Textbox "+value);
		implicitWait(100);
		}catch (Exception e)
		{
			 e.printStackTrace();
			}
	}

	/*
	 * clickButton - Method is created to click on buttons
	 */
	public void clickButton(String buttonName) {
		try {
		String buttonXpath = "//button[contains(text(),'" + buttonName.trim() + "')]";
		WebElement element = driver.findElement(By.xpath(buttonXpath));
		element.click();
		implicitWait(60);
		}catch (Exception e)
		{
		 e.printStackTrace();
		}
	}

	/*
	 * Click - method is created to click on webelement
	 */
	public void click(String locator) {
		try {
		WebElement element = findElementByLocator(locator);
	//	test.log(LogStatus.INFO, "Trying to click on : " + locator);
		element.click();
		implicitWait(1000);
		}catch (Exception e)
		{
			 e.printStackTrace();
			}
	}
	
	/*
	 * selectCheckBox - Method is created to select the check box
	 */
	public void selectCheckBox(String locator) {
		try {
		WebElement checkBoxElement = findElementByLocator(locator);
		boolean isSelected = checkBoxElement.isSelected();
				
		//performing click operation if element is not checked
		if(isSelected == false) {
			checkBoxElement.click();
		}
		}catch (Exception e)
		{
		 e.printStackTrace();
		}
	}
	
	/*
	 * Close Browser method is created to close the browser
	 */
	public void closeBrowser() throws Exception
	{
		//driver.close();
		report.flush();
		Thread.sleep(3000);
		driver.close();
		try {
			Thread.sleep(7500);
			System.out.println("Closed Browser");
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
	}

		
	/*
	 * screenShotMsg - method is created to capture the screen shot
	 */
	public static void screenShotMsg(String msg)
	{
		try {
			logger.pass(msg, MediaEntityBuilder.createScreenCaptureFromPath(TestUtil.takeScreenshotAtTest()).build());
			//logger.pass("edited Opportunity saved", MediaEntityBuilder.createScreenCaptureFromPath(TestUtil.takeScreenshotAtTest()).build());
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public void WebDriverWaitForElement(String xpath,int waitingTimeinsec) throws Exception {
		WebElement element=null;
		     try {
        	
        	 	WebDriverWait wait = new WebDriverWait(driver, waitingTimeinsec);
        	 	element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        	  }
         	catch(Exception e)
         	{
         		e.printStackTrace();
         		System.out.println("Could not find the element even after waiting explicitly for ("+waitingTimeinsec+")sec");
         	          	 
         	}
     }
	/*
	 * selectFromLookup - method is created to select the values from look up
	 */
	public boolean selectFromLookup(String fieldname, String LookUpValue) throws Exception
    {
		js = (JavascriptExecutor)driver;    
           try
           {
        	   LookUpValue = LookUpValue;
        	   	String xpath = "(//label[contains(@class,'label') and contains(@class,'slds-form')]/descendant-or-self::*[text()='"+fieldname+"']/ancestor::div[contains(@class,'slds-grid slds')][1]/descendant::input)[1]";
        	   	WebElement element = driver.findElement(By.xpath(xpath));
        	   	element.clear();
        	   	element.sendKeys(LookUpValue);
				
				Thread.sleep(1000);
        	   	//xpath = "(//label[contains(@class,'label') and contains(@class,'slds-form')]/descendant-or-self::*[text()='"+fieldname+"']/ancestor::div[contains(@class,'slds-grid slds')][1]/descendant::li)[1]";
				//String lookUpValxpath = ".//*[@title='"+LookUpValue+"']/ancestor::ul/li[2]";
				String lookUpValxpath = ".//*[@title='"+LookUpValue+"']/ancestor::ul/li[1]";
				WebElement clickValue = driver.findElement(By.xpath(lookUpValxpath));
				clickValue.click();
				//js.executeScript("arguments[0].click();", clickValue);
				Thread.sleep(1000);
                System.out.println("Selected the value ("+LookUpValue+") from lookup field ("+fieldname+")");
                return true;               
           }
           catch(Exception e)
           {
                 e.printStackTrace();
                 System.out.println("Unable to find the value from lookup "+fieldname);
                 return false;
                 
           }
           
    }
	
	public void pickFromList(String labelName,String dropdownValue) {
		try {
		js = (JavascriptExecutor)driver;
		String element = "//label[contains(text(),'"+labelName+"')]/../descendant::button/span";
		js.executeScript("arguments[0].click();", element);
		Thread.sleep(500);
		String locator2 = "//label[contains(text(),'"+labelName+"')]/../descendant::div//button/span";
		WebElement selectValue = driver.findElement(By.xpath(locator2));
		String actualVal = selectValue.getText();
		if (actualVal.equalsIgnoreCase(dropdownValue)) {
			//selectValue.click();
			js.executeScript("arguments[0].click();", selectValue);
			System.out.println("Value selected as" +dropdownValue);
			Thread.sleep(500);
		}
		}catch (Exception e)
		{
			 e.printStackTrace();
			}

	}
}


