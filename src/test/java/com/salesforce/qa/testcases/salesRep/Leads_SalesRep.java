package com.salesforce.qa.testcases.salesRep;

import java.io.IOException;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.salesforce.qa.base.TestBase;
import com.salesforce.qa.pages.AccountsPage;
import com.salesforce.qa.pages.ContactsPage;
import com.salesforce.qa.pages.HomePage;
import com.salesforce.qa.pages.LeadsPage;
import com.salesforce.qa.pages.LoginPage;
import com.salesforce.qa.utility.ExcelUtils;
import com.salesforce.qa.utility.TestUtil;

/*
 * TC_03 This Test case is developed to create Leads in Salesforce with Sales Rep profile application by providing all mandatory details
 */
public class Leads_SalesRep extends TestBase {
	LoginPage loginPage;
	HomePage homePage;
	TestUtil testUtil;
	AccountsPage accountsPage;
	LeadsPage leadsPage;
	
	public static String TESTDATA_SHEET_PATH = System.getProperty("user.dir")+ "\\CRMTestData.xlsx";
	static WebDriver driver;
	static JavascriptExecutor js;
	
	String sheetName = "leads";
	
	
	public Leads_SalesRep(){
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
		leadsPage = new LeadsPage();
		loginPage.login(prop.getProperty("username1"), prop.getProperty("password1"));
		
	}
	
	/*
	 * createLeadSalesRep method is to create Leads in Salesforce application using Sales Rep profile
	 */
	
	@Test(priority = 1)
	public void createLeadSalesRep() throws Exception{
		try {

			//logger = report.createTest("Contact Creation", "It's will create a Lead.");
			ExcelUtils.setExcelFile(TESTDATA_SHEET_PATH, sheetName);
			String salutation =  ExcelUtils.getCellData(1, 0);	
			String firstname =  ExcelUtils.getCellData(1, 1);	
			String lastname	=  ExcelUtils.getCellData(1, 2);
			String phone = ExcelUtils.getCellData(1, 3);
			String mobile =  ExcelUtils.getCellData(1, 4);
			String selectleadstatus = ExcelUtils.getCellData(1, 5);
			String company = ExcelUtils.getCellData(1, 6);
			String fax	= ExcelUtils.getCellData(1, 7);
			String title	= ExcelUtils.getCellData(1, 8);
			String email= ExcelUtils.getCellData(1, 9);	
			String leadSource	= ExcelUtils.getCellData(1, 10);
			String website	= ExcelUtils.getCellData(1, 11);
			String industry	= ExcelUtils.getCellData(1, 12);
			String annualRevenue	= ExcelUtils.getCellData(1, 13);
			String rating	= ExcelUtils.getCellData(1, 14);
			String employeeNo= ExcelUtils.getCellData(1, 15);

			String objectSelect = "Leads";
			String labelName = "Lead Status";
			String objectName ="Lead";
			String getContactName = salutation+" "+firstname+" "+lastname;
			
			System.out.println("Test Script Started with Leads creation_SalesRep Profile");
			accountsPage.selectApplication(objectSelect);
			leadsPage.clickBtn();
			testUtil.verifyNewClicked(objectName);

			selectDropdown("salutation_XPATH", salutation);
			leadsPage.sendFirstName(firstname);
			leadsPage.sendLastName(lastname);
			leadsPage.sendCompanyName(company);
			type("leadPhone_XPATH",phone);
			type("leadMobile_XPATH",mobile);
			type("leadFax_XPATH",fax);
			leadsPage.saveBtn();
			screenShotMsg("Lead details saved");
			testUtil.waitForPageLoad(5000);
			leadsPage.detailsTabClick();
			verifyActualWithExpectedText("getleadName_XPATH", getContactName);
			
			editPage();
			screenShotMsg("Lead Edited");
			type("leadTitle_XPATH",title);
			type("leadEmail_XPATH",email);
			testUtil.selectFromList("Lead Source",leadSource);
			type("leadWebSite_XPATH",website);
			testUtil.selectFromList("Industry",industry);
			testUtil.selectFromList(labelName, selectleadstatus);
			type("leadAnnualRevenue_XPATH",annualRevenue);
			selectDropdown("leadRating_XPATH", rating);
			type("leadEmployee_XPATH",employeeNo);
			leadsPage.saveBtn();
			screenShotMsg("Edited Lead Saved");

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
	public void tearDown() throws Exception{
		 closeBrowser();
	}

	 
}
