package com.salesforce.qa.testcases.salesRep;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.salesforce.qa.base.TestBase;
import com.salesforce.qa.pages.AccountsPage;
import com.salesforce.qa.pages.HomePage;
import com.salesforce.qa.pages.LoginPage;
import com.salesforce.qa.utility.ExcelUtils;
import com.salesforce.qa.utility.TestUtil;



/*
 * TC_01 This Test case is developed to create New Account in Salesforce application with Sales Rep profile by providing all mandatory details
 */

public class Accounts_SalesRep extends TestBase {

	
	LoginPage loginPage;
	HomePage homePage;
	TestUtil testUtil;
	AccountsPage accountsPage;

	public static String TESTDATA_SHEET_PATH = System.getProperty("user.dir")+ "\\CRMTestData.xlsx";
	//public static WebDriver driver;
	static JavascriptExecutor js;

	String sheetName = "accounts";
	
	
	public Accounts_SalesRep(){
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
		loginPage = new LoginPage();
		loginPage.login(prop.getProperty("username1"), prop.getProperty("password1"));
		 
	}

	/*
	 * createAccount method is to create Account
	 */
	
	@Test(priority = 1)
	public void createAccountSalesRep() throws Exception{
		try {
		
			
			ExcelUtils.setExcelFile(TESTDATA_SHEET_PATH, sheetName);
			String accountName =  ExcelUtils.getCellData(1, 0);
			String rating = ExcelUtils.getCellData(1, 1);
			String phone = ExcelUtils.getCellData(1, 2);
			String fax = ExcelUtils.getCellData(1, 3);
			String accountNum = ExcelUtils.getCellData(1, 4);
			String billingCity =  ExcelUtils.getCellData(1, 5);
			String shippingCountry = ExcelUtils.getCellData(1, 6);
			String expirationDate = ExcelUtils.getCellData(1, 7);
			String website = ExcelUtils.getCellData(1, 8);
			String accountsite = ExcelUtils.getCellData(1, 9);
			String tickersymbol = ExcelUtils.getCellData(1, 10);
			String typeA = ExcelUtils.getCellData(1, 11);
			String ownership = ExcelUtils.getCellData(1, 12);
			String industry = ExcelUtils.getCellData(1, 13);
			String employees = ExcelUtils.getCellData(1, 14);
			String annualRevenue = ExcelUtils.getCellData(1, 15);
			String SICCode = ExcelUtils.getCellData(1, 16);
			String billingAddress = ExcelUtils.getCellData(1, 17);
			String shippingAddress = ExcelUtils.getCellData(1, 18);
						
			String accountText = accountName;
			String objectSelect = "Accounts";

			System.out.println("Test Script Started with Account creation_Profile Sales Rep");
			accountsPage.selectApplication(objectSelect);
			accountsPage.clickBtn();
			
			validateCreateNewAccount(accountName); 
			selectDropdown("rating_XPATH", rating);
			type("phone_XPATH",phone);
			type("fax_XPATH",fax);
			type("accountNumber_XPATH",accountNum);
			type("expiratnDate_XPATH",expirationDate);
			type("webSite_XPATH",website);
			type("accountSite_XPATH",accountsite);
			type("tickerSymbol_XPATH",tickersymbol);
			accountsPage.saveBtn();
			accountsPage.detailsTabClick();
			//accountsPage.verifyAccText(accountText);
			verifyActualWithExpectedText("verifyAccount_XPATH", accountText);
			screenShotMsg(accountName+" verified");
			testUtil.waitForPageLoad(5000);

			editPage();
			screenShotMsg(accountName+" Edited");
			/*
			 * selectDropdown("typeAcc_XPATH",typeA);
			 * selectDropdown("ownership_XPATH",ownership);
			 * testUtil.selectFromList("Industry",industry);
			 */
			testUtil.selectFromList("Type",typeA);
			testUtil.selectFromList("Ownership",ownership);
			testUtil.selectFromList("Industry",industry);
			type("employee_XPATH",employees);
			type("annualRevenue_XPATH",annualRevenue);
			type("sci_XPATH",SICCode);
			type("billingStreet_XPATH",billingAddress);
			type("shippingStreet_XPATH",shippingAddress);
			
			accountsPage.saveBtn();
			screenShotMsg(accountName+" Saved");
			
			logOff();
			logger.pass("logged out successfully");
			
		}catch(Exception e)
		{
			e.printStackTrace();

		}

	}

	public void validateCreateNewAccount(String accountName){
		accountsPage.accountCreation(accountName); 
	}

	/*
	 * tear down method is to cloe the browser successfully
	 */
	@AfterMethod
	public void tearDown() throws Exception{
		closeBrowser();
	}
	
}
