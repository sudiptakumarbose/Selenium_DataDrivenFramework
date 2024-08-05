package com.pageObjects;

import org.testng.annotations.Test;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.base.TestBase;

public class LoginTest extends TestBase {
	
	public LoginTest(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		
	}
	@FindBy(xpath="//button[text()='Bank Manager Login']")
	public WebElement bankmanagerLoginButton;
	@FindBy(css="button[ng-click='addCust()']")
	public WebElement addCustomerButton;
	
	@FindBy(xpath="//button[text()='Customer Login']")
	public WebElement customerLogin;
	
	@FindBy(xpath="//select[@id='userSelect']")
	public WebElement selectUser;
	
	@FindBy(xpath="//button[@type='submit']")
	public WebElement loginButton;
	
	public void loginAsbankManager() throws IOException
	{
		String title=driver.getTitle();
		String expectedTitle="Protractor practice website - Banking App";
		verifyEquals(title, expectedTitle);
		log.info("Expected title is same as the title fetched");
		waitForElementToBeClickable(bankmanagerLoginButton, 60).click();
		log.info("clicking on the bank manager login button !: "+bankmanagerLoginButton.toString());
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		waitForElementToBeClickable(addCustomerButton, 60).click();
		log.info("clicking on the add customer button: "+addCustomerButton.toString());
		Assert.assertTrue(isElementPresent(By.cssSelector("button[ng-click='addCust()']")), "Login not successful!");
	}
	public void loginAsCustomer() throws IOException
	{
		String title=driver.getTitle();
		String expectedTitle="Protractor practice website - Banking App";
		verifyEquals(title, expectedTitle);
		waitForElementToBeClickable(customerLogin, 60).click();
		log.info("clicking on the bank manager login button !: "+customerLogin.toString());
		selectFromDropdownbyVisibleText(selectUser, "Hermoine Granger");
		waitForElementToBeClickable(loginButton, 60).click();
	}
}

	