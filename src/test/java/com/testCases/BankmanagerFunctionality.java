package com.testCases;

import java.io.IOException;
import java.util.Hashtable;

import javax.mail.MessagingException;

import org.apache.commons.mail.EmailException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.base.TestBase;
import com.pageObjects.AddCustomerTest;
import com.pageObjects.LoginTest;
import com.pageObjects.OpenAccountTest;
import com.pageObjects.ViewCustomers;
import com.utilities.MonitoringMail;
import com.utilities.ReportCreator;
import com.utilities.TestUtil;

import jakarta.mail.internet.AddressException;
import jdk.internal.org.jline.utils.Log;

public class BankmanagerFunctionality {

	MonitoringMail monitoringmail=new MonitoringMail();
	

	@BeforeTest
	public void setUp() throws IOException {

		TestBase.setUp();
		ReportCreator.individualReport();
	}

	@Test
	public void loginAsBankManager() throws IOException {
		LoginTest logintest = new LoginTest(TestBase.driver);
		logintest.loginAsbankManager();
	}

	@Test(dataProviderClass = TestUtil.class, dataProvider = "dp", dependsOnMethods = { "loginAsBankManager" })
	public void TestaddCustomer(Hashtable<String, String> data) {
		AddCustomerTest addcustomer = new AddCustomerTest(TestBase.driver);
		addcustomer.addCustomerTest(data);
		
		
	}

	@Test(dataProviderClass = TestUtil.class, dataProvider = "dp", dependsOnMethods = { "TestaddCustomer" })
	public void openAccountTest(Hashtable<String, String> data) throws InterruptedException {
		OpenAccountTest openaccounttest = new OpenAccountTest(TestBase.driver);
		openaccounttest.openAccount(data);
	}

	@Test(dependsOnMethods = "openAccountTest")
	public void ViewAccountstest() {
		ViewCustomers viewcustomers = new ViewCustomers(TestBase.driver);
		viewcustomers.ViewAllCustomers();
		
	}

	@AfterTest
	public void quitTest() throws EmailException, AddressException {
		
		try {
			monitoringmail.sendEmail(TestUtil.server, TestUtil.from, TestUtil.sendTo(), TestUtil.sendCC(),TestUtil.subject,  TestUtil.messageBody());
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TestBase.teardown();

}
}
