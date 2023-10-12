package com.salesforce.qa.testcases.salesRep;

import java.io.IOException;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.salesforce.qa.base.TestBase;
import com.salesforce.qa.pages.AccountsPage;
import com.salesforce.qa.pages.CampaignsPage;
import com.salesforce.qa.pages.ContactsPage;
import com.salesforce.qa.pages.HomePage;
import com.salesforce.qa.pages.LeadsPage;
import com.salesforce.qa.pages.LoginPage;
import com.salesforce.qa.utility.ExcelUtils;
import com.salesforce.qa.utility.TestUtil;

/*
 * TC_05 This Test case is developed to create Campaigns in Salesforce application by providing all mandatory details
 */

public class Campaigns_SalesRep extends TestBase {
	
	LoginPage loginPage;
	HomePage homePage;
	TestUtil testUtil;
	AccountsPage accountsPage;
	CampaignsPage campaignPage;

	//public static String TESTDATA_SHEET_PATH = System.getProperty("user.dir")+ "\\src\\main\\resources\\CRMTestData.xlsx";
	public static String TESTDATA_SHEET_PATH = System.getProperty("user.dir")+ "\\CRMTestData.xlsx";
	static WebDriver driver;
	static JavascriptExecutor js;

	String sheetName = "campaign";
	
	public Campaigns_SalesRep(){
		super();
		
	}

	/*
	 * set up method is to initialise the Browser and Launch Salesforce application
	 */
	
	@BeforeMethod
	public void setUp() throws Exception {
		
		initialization(sheetName);
		testUtil = new TestUtil();
		loginPage = new LoginPage();
		accountsPage = new AccountsPage();
		campaignPage = new CampaignsPage();
		loginPage.login(prop.getProperty("username1"), prop.getProperty("password1"));
	}
	
	/*
	 * createCampaign method is to create new Campaign Record
	 */
	
	@Test(priority = 1)
	public void createCampaignSalesRep() throws Exception{
		try {
			
			ExcelUtils.setExcelFile(TESTDATA_SHEET_PATH, sheetName);
			String campaignName =  ExcelUtils.getCellData(1, 0);
			String startdate = ExcelUtils.getCellData(1, 1);
			String enddate = ExcelUtils.getCellData(1, 2);
			String type	= ExcelUtils.getCellData(1, 3);
			String status	= ExcelUtils.getCellData(1, 4);
			String expectedRevenue	= ExcelUtils.getCellData(1, 5);
			String budgetCost	= ExcelUtils.getCellData(1, 6);
			String actualCost	= ExcelUtils.getCellData(1, 7);
			String description = ExcelUtils.getCellData(1, 8);

			String objectSelect = "Campaigns";
			String objectName ="Campaign"; 
			
			System.out.println("Test Script Started with Campaign creation_Profile Sales Rep");
			accountsPage.selectApplication(objectSelect);
			campaignPage.clickBtn();
			screenShotMsg("Cliked on New button");
			testUtil.verifyNewClicked(objectName);
			type("campaignName_XPATH",campaignName);
			selectCheckBox("activeCheckBox_XPATH");
			screenShotMsg(campaignName+ " New Campaign created");
			dropDownSelect("campaignType_XPATH",type);
			dropDownSelect("campaignStatus_XPATH",status);
			campaignPage.saveBtn();
			testUtil.waitForPageLoad(7000);
			verifyActualWithExpectedText("getCampaignName_XPATH", campaignName);
			
			click("detailsTab_XPATH");
			screenShotMsg("Clicked on Details Tab");
			
			testUtil.waitForPageLoad(500);
			
			campaignPage.editCampaign();
			screenShotMsg(campaignName+ " edited");
			testUtil.verifyEditPage();
			type("startDate_XPATH", startdate);
			type("endDate_XPATH", enddate);
			type("campaignExpRev_XPATH", expectedRevenue);
			type("campaignbudgetCost_XPATH", budgetCost);
			type("campaignActualCost_XPATH", actualCost);
			type("campaignDecription_XPATH", description);
			campaignPage.saveBtn();
			testUtil.waitForPageLoad(1500);
			screenShotMsg("Edited Campaign Saved");
			//logger.pass("edited Campaign saved");

			logOff();
			logger.pass("logged out successfully");

		}catch(Exception e)
		{

			e.printStackTrace();
		}

	}
	
	/*
	 * tear down method is to cloe the browser successfully
	 */
	@AfterMethod
	public void tearDown() throws Exception {
		closeBrowser();
	} 	
}
