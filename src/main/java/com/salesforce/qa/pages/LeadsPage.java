package com.salesforce.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.salesforce.qa.base.TestBase;
import com.salesforce.qa.utility.TestUtil;


public class LeadsPage extends TestBase{

	@FindBy(xpath = "//a[@title='New']")
	WebElement newBtn;
	
	@FindBy(xpath = "//input[@name='firstName']")
	WebElement firstNameC;
	
	@FindBy(xpath = "//button[@name='SaveEdit']")
	WebElement saveButton;
	
	@FindBy(xpath = "//input[@name='lastName']")
	WebElement lastNameC;
	
	@FindBy(xpath = "//input[@name='Company']")
	WebElement companyLead;
	
	@FindBy(xpath = "//li[@data-label='Details']/a[@data-tab-value='detailTab']")
	WebElement detailsTab;
	
	TestUtil testUtil;

	public LeadsPage(){
		PageFactory.initElements(driver, this);
		testUtil = new TestUtil();
	}

	public void clickBtn() throws Exception
	{
		newBtn.click();
		testUtil.waitForPageLoad(2500);
		screenShotMsg("Clicked on New Button");
	}
	
	public LeadsPage sendFirstName(String firstNameVal)
	{
		try {
		firstNameC.sendKeys(firstNameVal);
		testUtil.waitForPageLoad(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return new LeadsPage();
		
		
	}
	public LeadsPage sendLastName(String secondNameVal)
	{
		try {
		lastNameC.sendKeys(secondNameVal);
		testUtil.waitForPageLoad(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return new LeadsPage();
		
	}
	
	public LeadsPage sendCompanyName(String companyVal)
	{
		try {
		companyLead.sendKeys(companyVal);
		testUtil.waitForPageLoad(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return new LeadsPage();
		
	}
	
	
	public void saveBtn() throws Exception
	{
		try {
		saveButton.click();
		testUtil.waitForPageLoad(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void detailsTabClick() throws Exception
	{
		detailsTab.click();
		testUtil.waitForPageLoad(3000);
		screenShotMsg("Clicked on Details tab");
	}	
}
