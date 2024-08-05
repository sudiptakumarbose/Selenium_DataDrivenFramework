package com.pageObjects;

import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
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

public class AddCustomerTest extends TestBase {

	public AddCustomerTest(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);

	}

	@FindBy(xpath = "//input[@placeholder='First Name']")
	public WebElement firstname;

	@FindBy(xpath = "//input[@placeholder='Last Name']")
	public WebElement lastname;

	@FindBy(xpath = "//input[@placeholder='Post Code']")
	public WebElement postcode;

	@FindBy(xpath = "//button[@type='submit']")
	public WebElement submitButton;

	public void addCustomerTest(Hashtable<String, String> data) {
		waitForElementToBeClickable(firstname, 60).sendKeys(data.get("firstname"));
		log.info("Typing firstname :" + data.get("firstname"));
		waitForElementToBeDisplayed(lastname, 60).sendKeys(data.get("lastname"));
		log.info("Typing Lastname :" + data.get("lastname"));
		waitForElementToBeDisplayed(postcode, 30).sendKeys(data.get("postcode"));
		log.info("Typing postcode :" + data.get("postcode"));
		waitForElementToBeClickable(submitButton, 30).click();
		;
		log.info("clicking on submit button");
		Alert alert = waitForAlert(60);
		Assert.assertTrue(alert.getText().contains(data.get("alerttext")));
		alert.accept();
		Reporter.log("Login Successfully executed");

	}

}
