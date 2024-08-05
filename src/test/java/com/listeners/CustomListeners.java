package com.listeners;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;

import com.base.TestBase;
import com.relevantcodes.extentreports.LogStatus;
import com.utilities.MonitoringMail;
import com.utilities.ReportCreator;
import com.utilities.TestUtil;

public class CustomListeners implements ITestListener {
	
	MonitoringMail monitoringmail = new MonitoringMail();
	String reportPath;

	public void onFinish(ITestContext arg0) {

	}

	public void onStart(ITestContext arg0) {
		TestBase testBase = new TestBase(TestBase.driver);
		
		
		testBase.test = testBase.rep.startTest(arg0.getName().toUpperCase());
		/*
		 * if (!TestUtil.isTestRunnable(arg0.getName(), testBase.excel)) { throw new
		 * SkipException("Skipping the test " + arg0.getName().toUpperCase() +
		 * " as the run mode is N"); }
		 */

	}

	public void onTestFailedWithinSuccessPercentage(ITestResult arg0) {

	}

	public void onTestFailure(ITestResult arg0) {
		TestBase testBase = new TestBase(TestBase.driver);
		System.setProperty("org.uncommons.reporting.escape-output", "false");

		try {
			TestUtil.captureScreenshot();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		testBase.test.log(LogStatus.FAIL,
				arg0.getName().toUpperCase() + "FAILED WITH EXCEPTION : " + arg0.getThrowable());
		testBase.test.log(LogStatus.FAIL, testBase.test.addScreenCapture(TestUtil.screenshotName));
		Reporter.log("Click to see Screenshot");
		Reporter.log("<a target=\"_blank\" href=" + TestUtil.screenshotName + ">Screenshot</a>");
		Reporter.log("<br>");
		Reporter.log("<br>");
		Reporter.log("<a target=\"_blank\" href=" + TestUtil.screenshotName + "><img src=" + TestUtil.screenshotName
				+ "height=200 width=200</img></a>");
		testBase.rep.endTest(testBase.test);
		testBase.rep.flush();

	}

	public void onTestSkipped(ITestResult arg0) {
		TestBase testBase = new TestBase(TestBase.driver);
		testBase.test.log(LogStatus.SKIP, arg0.getName().toUpperCase() + " Skipped because the Run Mode is set as N");
		testBase.rep.endTest(testBase.test);
		testBase.rep.flush();
	}

	public void onTestStart(ITestResult arg0) {
		

	}

	public void onTestSuccess(ITestResult arg0) {
		TestBase testBase = new TestBase(TestBase.driver);
		
		testBase.test.log(LogStatus.PASS, arg0.getName().toUpperCase() + "PASS");
		testBase.rep.endTest(testBase.test);
		testBase.rep.flush();
		//ReportCreator.tableHeading();
		//ReportCreator.addTestCaseData(arg0.firstName,res.lastName,res.accountNumber);
		//ReportCreator.tableCompletion();
		//ReportCreator.reportCompletion();

	}

}
