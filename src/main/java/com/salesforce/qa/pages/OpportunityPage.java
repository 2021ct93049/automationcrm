package com.salesforce.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.salesforce.qa.base.TestBase;
import com.salesforce.qa.utility.TestUtil;


public class OpportunityPage extends TestBase{

	@FindBy(xpath = "//a[@title='New']")
	WebElement newBtn;
	
	@FindBy(xpath = "//button[@name='SaveEdit']")
	WebElement saveButton;
	
	@FindBy(xpath = "//li[@data-tab-value='detailTab']")
	WebElement detailsTab;
	
	TestUtil testUtil;

	public OpportunityPage(){
		PageFactory.initElements(driver, this);
		testUtil = new TestUtil();
	}

	public void clickBtn() throws Exception
	{
		try {
		newBtn.click();
		testUtil.waitForPageLoad(3000);
		screenShotMsg("Clicked on New Button");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void saveBtn() throws Exception
	{
		try {
		saveButton.click();
		testUtil.waitForPageLoad(3000);
		} catch (Exception e) {
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
