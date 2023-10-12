package com.salesforce.qa.testcases.salesRep;

import java.io.IOException;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.salesforce.qa.base.TestBase;
import com.salesforce.qa.pages.AccountsPage;
import com.salesforce.qa.pages.ContactsPage;
import com.salesforce.qa.pages.HomePage;
import com.salesforce.qa.pages.LeadsPage;
import com.salesforce.qa.pages.LoginPage;
import com.salesforce.qa.pages.OpportunityPage;
import com.salesforce.qa.utility.ExcelUtils;
import com.salesforce.qa.utility.TestUtil;

/*
 * TC_04_ This Test case is developed to create Opportunity in Salesforce application by providing all mandatory details
 */
public class Opportunity_SalesRep extends TestBase {
	
	LoginPage loginPage;
	HomePage homePage;
	TestUtil testUtil;
	AccountsPage accountsPage;
	OpportunityPage opportunityPage;
	
	public static ExtentTest logger;
	public static String TESTDATA_SHEET_PATH = System.getProperty("user.dir")+ "\\CRMTestData.xlsx";
	public static WebDriver driver;
	static JavascriptExecutor js;
	
	String sheetName = "opportunity";
	
	
	public Opportunity_SalesRep(){
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
		opportunityPage = new OpportunityPage();
		loginPage.login(prop.getProperty("username1"), prop.getProperty("password1"));
	}
	
	/*
	 * createOpportunitySalesRep method is to create New Opportunity with Sales Rep profile
	 */
	
	@Test(priority = 1)
	public void createOpportunitySalesRep() throws Exception{
		try {
			//logger = report.createTest("Opportunity Creation", "It's will create an Opportunity.");
			ExcelUtils.setExcelFile(TESTDATA_SHEET_PATH, sheetName);
			String opportunityname =  ExcelUtils.getCellData(1, 0);	
			String closedate =  ExcelUtils.getCellData(1, 1);	
			String selectStage = ExcelUtils.getCellData(1, 2);
			String amount = ExcelUtils.getCellData(1, 3);
			String leadsource = ExcelUtils.getCellData(1, 4);
			String nextStep	= ExcelUtils.getCellData(1, 5);
			String accountName	= ExcelUtils.getCellData(1, 6);
			String type	= ExcelUtils.getCellData(1, 7);
			//String probability	= ExcelUtils.getCellData(1, 8);
			String orderNumber	= ExcelUtils.getCellData(1, 9);
			String mainCompetitor	= ExcelUtils.getCellData(1, 10);
			String currentGenerator	= ExcelUtils.getCellData(1, 11);
			String deliveryStatus	= ExcelUtils.getCellData(1, 12);
			String trackingNumber	= ExcelUtils.getCellData(1, 13);
			String description= ExcelUtils.getCellData(1, 14);

			String objectSelect = "Opportunities";
			String labelName = "Stage";
			String objectName ="Opportunity";
			String selectLeadSource = "Lead Source";
			
			System.out.println("Test Script Started with Opportunity creation_SalesRep Profile");
			accountsPage.selectApplication(objectSelect);
			screenShotMsg(objectSelect + " Object selectd");
			opportunityPage.clickBtn();
			screenShotMsg("Clicked on New Button");
			testUtil.verifyNewClicked(objectName);
			
			type("opportunityName_XPATH",opportunityname);
			//selectFromLookup("Account Name", accountName);
			type("optyAmt_XPATH",amount);
			type("closeDate_XPATH",closedate);
			type("optyNextStep_XPATH",nextStep);
			testUtil.selectFromList(labelName, selectStage);
			selectDropdown("optyType_XPATH",type);
			opportunityPage.saveBtn();
			screenShotMsg("Opportunity created");
			testUtil.waitForPageLoad(5000);
			
			opportunityPage.detailsTabClick();
			
			editPage();
			screenShotMsg("Opportunity Edited");
			testUtil.selectFromList(selectLeadSource, leadsource);
			type("optyOrderNo_XPATH",orderNumber);
			type("optyOrderNo_XPATH",mainCompetitor);
			type("optyCurrentGenerator_XPATH",currentGenerator);
			selectDropdown("optyDeliveryStatus_XPATH",deliveryStatus);
			type("optyTrackingNum_XPATH",trackingNumber);
			type("optyDescription_XPATH",description);
			opportunityPage.saveBtn();
			screenShotMsg("edited Opportunity saved");
				
			logOff();
			
			}catch(Exception e)
			{
			e.printStackTrace();
			}
		 
		}
	
	/*
	 * tear down method is to close the browser successfully
	 */	
	@AfterMethod
	public void tearDown() throws Exception{
		closeBrowser();
	}
	 
}
