package com.pageObjects;

import java.io.IOException;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class CustomerFunctionalityTest extends TestBase {

	public CustomerFunctionalityTest() {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = "button[ng-click='transactions()']")
	public WebElement transactions;
	@FindBy(css = "button[ng-click='deposit()']")
	public WebElement deposit;
	@FindBy(css = "button[ng-click='withdrawl()']")
	public WebElement withdrawls;
	@FindBy(xpath="//span[text()='Deposit Successful']")
	public WebElement message;
	@FindBy(xpath = "//input[@placeholder='amount']")
	public WebElement amount;
	@FindBy(xpath="//button[@type='submit']")
	public WebElement depositButton;
	@FindBy(xpath="//button[text()='Withdraw']")
	public WebElement withdrawButton;
	@FindBy(xpath="//input[@id='start']")
	public WebElement start;

	public void customerTransactions() throws IOException {
		waitForElementToBeClickable(deposit, 60).click();
		waitForElementToBeDisplayed(amount, 60).sendKeys("1000");
		waitForElementToBeClickable(depositButton, 60).click();
		String messageText=message.getText();
		verifyEquals("Deposit Successful", messageText);
		waitForElementToBeClickable(withdrawls, 60).click();
		waitForElementToBeDisplayed(amount, 60).sendKeys("500");
		waitForElementToBeClickable(withdrawButton, 60).click();;
		waitForElementToBeClickable(deposit, 60).click();
		waitForElementToBeDisplayed(amount, 60).sendKeys("1000");
		waitForElementToBeClickable(depositButton, 60).click();
		verifyEquals("Deposit Successful", messageText);
		waitForElementToBeClickable(transactions, 60).click();
		waitForElementToBeClickable(start, 60).click();
		
		
		

	}

}
