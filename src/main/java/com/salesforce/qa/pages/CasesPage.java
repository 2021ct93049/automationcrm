package com.salesforce.qa.pages;

import static org.testng.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.salesforce.qa.base.TestBase;
import com.salesforce.qa.utility.TestUtil;


public class CasesPage extends TestBase{

	static JavascriptExecutor js;
	
	@FindBy(xpath = "//a[@title='New']")
	WebElement newBtn;
	
	@FindBy(xpath = "//button[@name='SaveEdit']")
	WebElement saveButton;
	
	@FindBy(xpath = "//li[@data-tab-value='detailTab']")
	WebElement detailsTab;
	
	@FindBy(xpath="//*[text()='New Case']")
	WebElement newCasePage;
	
	@FindBy(xpath="(//*[@title='Case Number']/parent::div)[3]//p[2]//lightning-formatted-text")
	WebElement caseNumber;
	
	@FindBy(xpath="//button[@name='Edit']")
	WebElement editCase;
	
	TestUtil testUtil;

	public CasesPage(){
		PageFactory.initElements(driver, this);
		testUtil = new TestUtil();
	}

	public void clickNewBtn() throws Exception
	{
		newBtn.click();
		testUtil.waitForPageLoad(2500);
		screenShotMsg("Clicked on New Button");
	}
	
	
	
	public void saveBtn() throws Exception
	{
		saveButton.click();
		testUtil.waitForPageLoad(3000);
	}
	
	public void detailsTabClick() throws Exception
	{
		detailsTab.click();
		testUtil.waitForPageLoad(3000);
		screenShotMsg("Clicked on Details tab");
	}
	
	public void verifyNewCasePage() throws Exception
	{
		String getCaseText = newCasePage.getText();
		if(getCaseText.contains("New")) {
			System.out.println("Navigated to New Case Page");
		}else {
			System.out.println("Not on New Page");
		}
	}	
	

	public void editCase()
	{
		
		try {
			editCase.click();
			testUtil.waitForPageLoad(3000);
			screenShotMsg("Case Editing");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
