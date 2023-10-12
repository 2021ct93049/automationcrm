package com.salesforce.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.salesforce.qa.base.TestBase;
import com.salesforce.qa.utility.TestUtil;


public class AccountsPage extends TestBase{

	@FindBy(xpath = "//div[@role='navigation']//button")
	WebElement appLauncherIcon;

	@FindBy(xpath = "//button[@type='button'][text()='View All']")
	WebElement viewAllOption;

	@FindBy(xpath = "//input[@placeholder='Search apps or items...']")
	WebElement searchApp;

	@FindBy(xpath = "//a[@title='New']")
	WebElement newBtn;
	
	@FindBy(xpath = "//input[@name='Name']")
	WebElement accountName;
	
	@FindBy(xpath = "//button[@name='SaveEdit']")
	WebElement saveButton;
	
	@FindBy(xpath = "//div[contains(@class,'testonly-')]//lightning-formatted-text")
	WebElement verifyAccount;
	
	@FindBy(xpath = "//li[@data-tab-value='detailTab']")
	WebElement detailsTab;
	
	TestUtil testUtil;

	public AccountsPage(){
		PageFactory.initElements(driver, this);
		testUtil = new TestUtil();
	}

	
	public void selectApplication(String ApplicationName) throws Exception {
		try {
		testUtil.WebDriverWaitForElement(appLauncherIcon, 100);
		appLauncherIcon.click();
		testUtil.waitForPageLoad(2500);
		
		testUtil.WebDriverWaitForElement(viewAllOption, 100);
		viewAllOption.click();
		testUtil.implicitWait(30);
		testUtil.waitForPageLoad(1500);
		
		String xp1 = "(//*[normalize-space(text())='"+ApplicationName.trim()+"'])[1]";
		searchApp.sendKeys(ApplicationName);
		testUtil.waitForPageLoad(1000);
		screenShotMsg(ApplicationName + " Object selectd");
		testUtil.waitForPageLoad(1500);

		if (testUtil.WaitForElement(xp1, 20))
		{
			Thread.sleep(5000L);
			WebElement elm = driver.findElement(By.xpath(xp1));
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("arguments[0].click();", elm);
			//elm.click();
			testUtil.waitForPageLoad(1500);
			System.out.println("Selected the app ("+ApplicationName+") successfully.");

		}
		else
		{
			System.out.println("Unable to select the app ("+ApplicationName+").");

		}
	}catch(Exception e)
		{
		e.printStackTrace();
		System.out.println("Unable to select the application ("+ApplicationName+")");
	
	}

	}
	
	public void clickBtn() throws Exception
	{
		newBtn.click();
		testUtil.waitForPageLoad(2500);
		
		screenShotMsg("Clicked on New Button");
	}
	
	public AccountsPage accountCreation(String accountVal)
	{
		
		try {
			accountName.sendKeys(accountVal);
			testUtil.waitForPageLoad(250);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		return new AccountsPage();
	}
	
	public void saveBtn() throws Exception
	{
		saveButton.click();
		testUtil.waitForPageLoad(3000);
		System.out.println("Clicked on Save button");
	}
	
	public void detailsTabClick() throws Exception
	{
		detailsTab.click();
		testUtil.waitForPageLoad(2000);
		System.out.println("Clicked on Details tab");
		screenShotMsg("Clicked on Details tab");
	}
	
	public void verifyAccText(String ActualName) throws Exception
	{
		String getText = verifyAccount.getText();
		if(getText.equalsIgnoreCase(ActualName))
		{
			System.out.println("Text matches");
		}else {
			System.out.println("Text doesn't match");
		}
	}
	
}
