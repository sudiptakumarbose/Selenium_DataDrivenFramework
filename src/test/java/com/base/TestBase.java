package com.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.utilities.ExcelReader;
import com.utilities.ExtentManager;
import com.utilities.TestUtil;
import org.apache.log4j.Logger;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {
	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;
	public static Logger log = Logger.getLogger(TestBase.class);
	public static ExcelReader excel = new ExcelReader(
			System.getProperty("user.dir") + "\\src\\test\\resources\\excel\\testdata.xlsx");
	public ExtentReports rep = ExtentManager.getInstance();
	public static ExtentTest test;

	public TestBase(WebDriver driver) {
		this.driver = driver;
	}

	public static void setUp() throws IOException {
		if (driver == null) {
			fis = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\config.properties");
			config.load(fis); // Loading the config.properties file
			log.info("Loading the config.properties file");
			/*
			fis = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\OR.properties");
			OR.load(fis); // Loading the OR.properties file
			log.info("Loading the OR.properties file");*/
			if (config.getProperty("browser").equals("chrome")) {
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
				log.info("Initializing chromedriver");

			} else if (config.getProperty("browser").equals("Edge")) {
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();
				log.info("Initializing EdgeDriver");

			} else if (config.getProperty("browser").equals("Firefox")) {
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();

			}
			driver.get(config.getProperty("testsiteurl"));
			log.info("Loading the base Url !");
			driver.manage().window().maximize();

		}
	}

	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;

		}

	}

	public void click(By elementLocator) {
		System.out.println("Clicking element with locator " + elementLocator.toString());
		waitForElementToBeClickable((WebElement) elementLocator, 60);
		try {
			driver.findElement(elementLocator).click();
			test.log(LogStatus.INFO, "clicking on :" + elementLocator.toString());
		} catch (WebDriverException ex) {
			System.out.println("Caught webdriver exception " + ex.getMessage() + "Will retry");
			driver.findElement(elementLocator).click();
			test.log(LogStatus.INFO, "clicking on :" + elementLocator.toString());

		}

	}

	public void setText(By elementLocator, String text) {
		System.out.println("Setting text field with locator " + elementLocator.toString() + "To Value: " + text);
		waitForElementToBeDisplayed((WebElement) elementLocator, 60);
		try {
			WebElement textBox = driver.findElement(elementLocator);
			textBox.clear();
			textBox.sendKeys(text);
			test.log(LogStatus.INFO, "Typing on textBox :" + elementLocator.toString());
		} catch (WebDriverException ex) {
			System.out.println(
					"Caught exception in waitForElementToBeClickable method " + ex.getMessage() + "will retry");
			WebElement textBox = driver.findElement(elementLocator);
			textBox.clear();
			textBox.sendKeys(text);
			test.log(LogStatus.INFO, "Typing on textBox :" + elementLocator.toString());

		}

	}

	public WebElement waitForElementToBeClickable(WebElement webElement, int timeOutInSeconds) {
		WebElement clickableElement;
		try {
			Thread.sleep(500);
		} catch (Exception e) {
			System.out.println("Error sleeping before waiting for clickable element");
		}

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds));
		System.out.println("Waiting for element " + webElement.toString() + " to be clickable");

		try {
			clickableElement = wait.until(ExpectedConditions.elementToBeClickable(webElement));
		} catch (WebDriverException ex) {
			System.out.println(
					"Caught exception in waitForElementToBeClickable method: " + ex.getMessage() + " Will retry.");
			clickableElement = wait.until(ExpectedConditions.elementToBeClickable(webElement));
		}

		System.out.println("Element " + webElement.toString() + " is clickable");
		return clickableElement;
	}

	public WebElement waitForElementToBeDisplayed(WebElement webElement, int timeOutInSeconds) {
		WebElement visibleElement;
		try {
			Thread.sleep(500);
		} catch (Exception e) {
			System.out.println("Error sleeping before waiting for clickable element");
		}

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds));
		System.out.println("Waiting for element " + webElement.toString() + " to be clickable");

		try {
			visibleElement = wait.until(ExpectedConditions.visibilityOf(webElement));
		} catch (WebDriverException ex) {
			System.out.println(
					"Caught exception in waitForElementToBeDisplayed method: " + ex.getMessage() + " Will retry.");
			visibleElement = wait.until(ExpectedConditions.visibilityOf(webElement));
		}

		System.out.println("Element " + webElement.toString() + " is clickable");
		return visibleElement;
	}

	public static List<WebElement> waitForAllElementsToBeDisplayed(List<WebElement> elements, int timeOutInSeconds) {
		try {
			Thread.sleep(500); // Optional sleep before waiting for clickable elements
		} catch (InterruptedException e) {
			System.out.println("Error sleeping before waiting for clickable element");
		}

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds));

		for (WebElement element : elements) {
			try {
				wait.until(ExpectedConditions.visibilityOf(element));
			} catch (org.openqa.selenium.WebDriverException ex) {
				System.out.println("Caught exception while waiting for element: " + ex.getMessage() + ". Will retry.");
				wait.until(ExpectedConditions.visibilityOf(element));
			}
		}

		return elements;
	}

	public static void verifyEquals(String expected, String actual) throws IOException {
		try {
			Assert.assertEquals(actual, expected);

		} catch (Throwable t) {
			TestUtil.captureScreenshot();
			Reporter.log("<br>" + "Verfication failure : " + t.getMessage() + "<br>");
			Reporter.log("<a target=\"_blank\" href=" + TestUtil.screenshotName + "><img src=" + TestUtil.screenshotName
					+ "height=200 width=200</img></a>");
			Reporter.log("<br>");
			Reporter.log("<br>");
			test.log(LogStatus.FAIL, "VERIFICATION FAILED WITH EXCEPTION : " + t.getMessage());
			test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));
		}
	}

	private Select getSelect(WebElement element) {
		Select select;

		try {
			select = new Select(element);
		} catch (WebDriverException ex) {
			System.out.println("Caught WebDriver exception: " + ex.getMessage() + ". Will retry.");
			select = new Select(element);
		}
		return select;
	}

	public void selectFromDropdownbyVisibleText(WebElement element, String selection) {
		waitForElementToBeClickable(element, 60);
		Select dropdown = getSelect(element);
		System.out.println(
				"Setting dropdown with element: " + element.toString() + " by visible text to value: " + selection);
		try {
			dropdown.selectByVisibleText(selection);
		} catch (WebDriverException ex) {
			System.out.println("Caught WebDriver exception: " + ex.getMessage() + ". Will retry.");
			dropdown.selectByVisibleText(selection);
		}
	}

	public Alert waitForAlert(int timeOutInSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds));
		return wait.until(ExpectedConditions.alertIsPresent());
	}

	public static void teardown() {
		if (driver != null) {
			driver.quit();
		}

	}
}
