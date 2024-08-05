package com.testCases;

import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.base.TestBase;
import com.pageObjects.CustomerFunctionalityTest;
import com.pageObjects.LoginTest;
import com.utilities.ReportCreator;

public class CustomerFunctionality {
	
	@BeforeTest
	public void setUp() throws IOException {

		TestBase.setUp();
		ReportCreator.individualReport();
	}
	@Test
	public void loginAsCustomer() throws IOException
	{
		LoginTest logintest=new LoginTest(TestBase.driver);
		logintest.loginAsCustomer();
	}
	@Test(dependsOnMethods = "loginAsCustomer")
	public void testTransactions() throws IOException
	{
		CustomerFunctionalityTest customerfunctionality=new CustomerFunctionalityTest();
		customerfunctionality. customerTransactions();
	}
	@AfterTest
	public void quitTest()
	{
		TestBase.teardown();
	}

}
