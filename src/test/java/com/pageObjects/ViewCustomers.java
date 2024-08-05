package com.pageObjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;
import com.utilities.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.base.TestBase;

public class ViewCustomers extends TestBase {
	
	public static String firstName;
	public static String lastName;
	public static String accountNo;

	@FindBy(css = "button[ng-click='showCust()']")
	public WebElement customers;

	@FindAll({ @FindBy(xpath = "//table//tbody//tr") })
	private List<WebElement> tableRows;

	public ViewCustomers(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public void ViewAllCustomers() {
		Map<Integer,Result>hm=new HashMap();
		int key=1;
		waitForElementToBeClickable(customers, 60).click();
		waitForAllElementsToBeDisplayed(tableRows, 60);
		log.info("Total Number of Customers are displayed :" + tableRows.toString());
		int rowsCount = tableRows.size();
		log.info("No of Customers: " + rowsCount);
		for (WebElement row : tableRows) {
			String rowDescription=row.getText();
			String[] rowText=rowDescription.split(" ");
			firstName=rowText[0];
			lastName=rowText[1];
			accountNo=rowText[2];
			hm.put(key, new Result(firstName,lastName,accountNo));
			key++;
			
			
		}
		getResult(new ArrayList(hm.values()));
		ReportCreator.tableCompletion();
		ReportCreator.reportCompletion();
		

	}
	
	public void getResult(List<Result> result)
	{
		result=result.stream().collect(Collectors.toList());
		ReportCreator.tableHeading();
		for(Result res:result)
		{
			ReportCreator.addTestCaseData(res.firstName,res.lastName,res.accountNumber);
			log.info("Creating the Report "+getClass().getName());
		}
	}

}
