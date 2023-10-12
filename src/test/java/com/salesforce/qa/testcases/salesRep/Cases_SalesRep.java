package com.salesforce.qa.testcases.salesRep;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.salesforce.qa.base.TestBase;
import com.salesforce.qa.pages.AccountsPage;
import com.salesforce.qa.pages.CasesPage;
import com.salesforce.qa.pages.HomePage;
import com.salesforce.qa.pages.LoginPage;
import com.salesforce.qa.utility.ExcelUtils;
import com.salesforce.qa.utility.TestUtil;

/* 
 * This Test Case is to create New Cases in Salesforce application using Sales Rep profile
 */
public class Cases_SalesRep extends TestBase{
	
	LoginPage loginPage;
	HomePage homePage;
	TestUtil testUtil;
	AccountsPage accountsPage;
	CasesPage casePage;

	public static String TESTDATA_SHEET_PATH = System.getProperty("user.dir")+ "\\CRMTestData.xlsx";
	static JavascriptExecutor js;

	String sheetName = "cases";
	public Cases_SalesRep(){
		super();
	}
	
	/*
	 * set up method is to initialise the Browser and Launch Salesforce application
	 */
	@BeforeMethod
	public void setUp() throws Exception {

		initialization(sheetName);
		testUtil = new TestUtil();
		accountsPage = new AccountsPage();
		casePage = new CasesPage();
		loginPage = new LoginPage();
		loginPage.login(prop.getProperty("username1"), prop.getProperty("password1"));
	}
	/*
	 * createCase method is to create New Case
	 */
	@Test(priority = 1)
	public void createCase() throws Exception{
		try {
			
			//logger = report.createTest("Account creation", "It will create an Account");
			ExcelUtils.setExcelFile(TESTDATA_SHEET_PATH, sheetName);
			String contactName =  ExcelUtils.getCellData(1, 0);
			String status	=  ExcelUtils.getCellData(1, 1);
			String priority	=  ExcelUtils.getCellData(1, 2);
			String caseorigin	=  ExcelUtils.getCellData(1, 3);
			String accountName	=  ExcelUtils.getCellData(1, 4);
			String type	=  ExcelUtils.getCellData(1, 5);
			String casereason	=  ExcelUtils.getCellData(1, 6);
			String webemail	=  ExcelUtils.getCellData(1, 7);
			String webcompany	=  ExcelUtils.getCellData(1, 8);
			String webname	=  ExcelUtils.getCellData(1, 9);
			String webphone	=  ExcelUtils.getCellData(1, 10);
			String product	=  ExcelUtils.getCellData(1, 11);
			String requiredNumber	=  ExcelUtils.getCellData(1, 12);
			String liability	=  ExcelUtils.getCellData(1, 13);
			String violation	=  ExcelUtils.getCellData(1, 14);
			String subject	=  ExcelUtils.getCellData(1, 15);
			String description	=  ExcelUtils.getCellData(1, 16);
			String internalcomments =  ExcelUtils.getCellData(1, 17);

			String objectSelect = "Cases";

			System.out.println("Test Script Started with Case creation_Profile Sales Rep");
			accountsPage.selectApplication(objectSelect);
			casePage.clickNewBtn();
			casePage.verifyNewCasePage();
			//testUtil.selectFromLookup("Contact Name", contactName);
			testUtil.selectFromList("Status",status);
			testUtil.selectFromList("Priority",priority);
			testUtil.selectFromList("Case Origin",caseorigin);
			testUtil.selectFromList("Type",type);
			testUtil.selectFromList("Case Reason",casereason);
			casePage.saveBtn();
			
			casePage.editCase();
			
			//type("caseWebEmail_XPATH",webemail);
			//type("caseWebCompany_XPATH",webcompany);
			//type("caseWebName_XPATH",webname);
			//type("caseWebPhone_XPATH",webphone);
			
			testUtil.selectFromList("Product",product);
			type("caseReqNumber_XPATH",requiredNumber);
			testUtil.selectFromList("Potential Liability",liability);
			testUtil.selectFromList("SLA Violation",violation);
			type("subject_XPATH",subject);
			type("caseDescription_XPATH",description);
			type("caseComments_XPATH",internalcomments);
			casePage.saveBtn();
			screenShotMsg("Case Created suceesfully");

			logOff();
			logger.pass("logged out successfully");
			//logger.log(Status.PASS, "Test passed");
			
		}catch(Exception e)
		{
			
			logger.log(Status.FAIL, "Test Failed");
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
