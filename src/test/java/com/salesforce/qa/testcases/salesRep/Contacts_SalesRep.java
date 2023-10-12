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
import com.salesforce.qa.pages.LoginPage;
import com.salesforce.qa.utility.ExcelUtils;
import com.salesforce.qa.utility.TestUtil;

/*
 * TC_02_ This Test case is developed to create Contacts in Salesforce with SalesRep profile application by providing all mandatory details
 */

public class Contacts_SalesRep extends TestBase {
	LoginPage loginPage;
	HomePage homePage;
	TestUtil testUtil;
	AccountsPage accountsPage;
	ContactsPage contactsPage;
	
	public static String TESTDATA_SHEET_PATH = System.getProperty("user.dir")+ "\\CRMTestData.xlsx";
	static WebDriver driver;
	static JavascriptExecutor js;
	
	String sheetName = "contacts";
	
	
	public Contacts_SalesRep(){
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
		contactsPage = new ContactsPage();
		
		loginPage.login(prop.getProperty("username1"), prop.getProperty("password1"));
		
	}
	/*
	 * createContact method is to create New Contact by Sales Rep profile
	 */
	
	@Test(priority = 1)
	public void createContactSalesRep() throws Exception{
		try {
			
			//logger = report.createTest("Contact Creation", "It's will create a Contact.");
			ExcelUtils.setExcelFile(TESTDATA_SHEET_PATH, sheetName);
			String salutation =    ExcelUtils.getCellData(1, 0);	
			String firstname =     ExcelUtils.getCellData(1, 1);	
			String lastname	=      ExcelUtils.getCellData(1, 2);
			String phone =         ExcelUtils.getCellData(1, 3);
			String homephone =     ExcelUtils.getCellData(1, 4);
			String mobile	=      ExcelUtils.getCellData(1, 5);
			String title	=      ExcelUtils.getCellData(1, 6);
			String otherPhone	=  ExcelUtils.getCellData(1, 7);
			String department	=  ExcelUtils.getCellData(1, 8);
			String fax	=  		   ExcelUtils.getCellData(1, 9);
			String birthdate	=  ExcelUtils.getCellData(1, 10);
			String email	=      ExcelUtils.getCellData(1, 11);
			String assistant	=  ExcelUtils.getCellData(1, 12);
			String leadsource	=  ExcelUtils.getCellData(1, 13);
			String asstphone=      ExcelUtils.getCellData(1, 14);
			String lookUpVal =     ExcelUtils.getCellData(1, 15);

			String objectSelect = "Contacts";
			
			System.out.println("Test Script Started with Contacts creation_Profile Sales Rep");
			String getContactName = salutation+" "+firstname+" "+lastname;
			System.out.println(getContactName);
			
			accountsPage.selectApplication(objectSelect);
			
			contactsPage.clickBtn();
			selectDropdown("salutation_XPATH", salutation);
			contactsPage.sendFirstName(firstname);
			contactsPage.sendLastName(lastname);
			type("contactPhone_XPATH",phone);
			type("contactHomePhone_XPATH",homephone);
			//selectFromLookup("Account Name", lookUpVal);
			type("contactMobile_XPATH",mobile);
			type("contactTitle_XPATH",title);
			contactsPage.saveBtn();
			
			contactsPage.detailsTabClick();
			
			verifyActualWithExpectedText("getContactName_XPATH", getContactName);
			screenShotMsg("Contact Saved");
			testUtil.waitForPageLoad(5000);
			
			editPage();
			screenShotMsg("Contact Edited");
			type("contactOtherPhone_XPATH",otherPhone);
			type("contactDepartment_XPATH",department);
			type("contactFax_XPATH",fax);
			type("contactBirthDt_XPATH",birthdate);
			type("contactEmail_XPATH",email);
			type("contactAssistant_XPATH",assistant);
			//selectDropdown("contactLeadSource_XPATH",leadsource);
			testUtil.selectFromList("Lead Source",leadsource);
			type("contactAssistantPhone_XPATH",asstphone);
			
			contactsPage.saveBtn();
			screenShotMsg("Edited Contact Saved");
			logOff();
			logger.pass("logged out successfully");
			
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
