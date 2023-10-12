package com.salesforce.qa.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.salesforce.qa.base.TestBase;
import com.salesforce.qa.utility.TestUtil;

public class LoginPage extends TestBase{
	
	//Page Factory - OR:
	@FindBy(xpath = "//input[@name='username']")
	WebElement userNameL;
	
	@FindBy(xpath = "//input[@name='pw']")
	WebElement passwordL;
	
	@FindBy(xpath="//input[@name='Login']")
	WebElement loginBtn;
	
	TestUtil testUtil;
	
	//Initializing the Page Objects:
	public LoginPage(){
		PageFactory.initElements(driver, this);
		testUtil = new TestUtil();
	}
	
	//Actions:
	public String validateLoginPageTitle(){
		return driver.getTitle();
	}
	
	public void login(String uname, String pwd) throws Exception{
		userNameL.sendKeys(uname);
		Thread.sleep(500);
		passwordL.sendKeys(pwd);
		screenShotMsg("Launched Salesforce application");
		//loginBtn.click();
		    	JavascriptExecutor js = (JavascriptExecutor)driver;
		    	js.executeScript("arguments[0].click();", loginBtn);
		    	testUtil.waitForPageLoad(5000);
		    	testUtil.waitForPageLoad(5000);
		 
		//return new HomePage();
	}
	
}
