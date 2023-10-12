package com.salesforce.qa.testcases.salesRep;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.salesforce.qa.base.TestBase;
import com.salesforce.qa.pages.HomePage;
import com.salesforce.qa.pages.LoginPage;


public class LoginPageTest extends TestBase{
	LoginPage loginPage;
	HomePage homePage;
	
	public LoginPageTest(){
		super();
	}
	
	@BeforeMethod
	public void setUp(){
		initialization("Login");
		loginPage = new LoginPage();	
	}
	
		
	/*
	 * @AfterMethod public void tearDown(){ driver.quit(); }
	 */
	
	
	
	

}
