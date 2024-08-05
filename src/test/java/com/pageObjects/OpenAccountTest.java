package com.pageObjects;

import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.base.TestBase;
import com.utilities.TestUtil;

public class OpenAccountTest extends TestBase {

	public OpenAccountTest(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);

	}

	@FindBy(xpath = "//button[normalize-space()='Open Account']")
	public WebElement openAcount;

	@FindBy(xpath = "//select[@id='userSelect']")
	public WebElement customer1;

	@FindBy(xpath = "//select[@id='currency']")
	public WebElement currency1;

	@FindBy(xpath = "//button[normalize-space()='Process']")
	public WebElement processbutton;

	public void openAccount(Hashtable<String, String> data) throws InterruptedException {

		waitForElementToBeClickable(openAcount, 60).click();
		log.info("Clicked on the Open Account Button : " + openAcount.toString());
		selectFromDropdownbyVisibleText(customer1, data.get("Customer"));
		log.info("Seleted the Customer : " + data.get("customer"));
		selectFromDropdownbyVisibleText(currency1, data.get("Currency"));
		log.info("Selected the currency : " + "Locator : " + currency1.toString() + "Data :" + data.get("Currency"));
		waitForElementToBeClickable(processbutton, 60).click();
		// log.info("Clicking the process button :"+processbutton.toString());
		Alert alert = waitForAlert(60);
		try {
			alert.accept();
		} catch (UnhandledAlertException e) {
			System.out.println("Alert not handled...Retrying!!!");

			alert.accept();
		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Reporter.log("Account opening Successfully executed");

	}

}
