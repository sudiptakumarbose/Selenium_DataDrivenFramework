package com.utilities;

import java.util.Date;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.DataProvider;
import static com.utilities.ReportCreator.path;
import com.base.TestBase;

public class TestUtil extends TestBase {

	
	public static String mailContent;
	public static String screenShotPath;
	public static String screenshotName;
	public static DateFormat dateformat = new SimpleDateFormat();
	public static Date date = new Date();
	public static String date1 = dateformat.format(date);
	public static String server = "smtp.gmail.com";
	public static String from = "bankingoperations@xyzbank.com";
	public static String subject = " Banking Operations updates " + date1 + " IST ";
	
	public TestUtil() {
		super(driver);
		
	}
	public static void captureScreenshot() throws IOException {
		File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		screenshotName = "error.jpg";
		FileUtils.copyFile(screenshotFile,
				new File(System.getProperty("user.dir") + "\\target\\surefire-reports\\html\\" + screenshotName));

	}
	@DataProvider(name = "dp")
	public static Object[][] getData(Method m) {
	    String sheetName = m.getName();
	    int rows = excel.getRowCount(sheetName);
	    int columns = excel.getColumnCount(sheetName);

	    // Check for empty sheet or invalid sheet name
	    if (rows <= 1) {
	        System.out.println("No data found in sheet: " + sheetName);
	        return new Object[0][0]; // Return an empty array
	    }

	    Hashtable<String, String> table = null;
	    Object[][] data = new Object[rows - 1][1];

	    for (int rowNum = 2; rowNum <= rows; rowNum++) {
	        table = new Hashtable<String, String>();
	        for (int colNum = 0; colNum < columns; colNum++) {
	            table.put(excel.getCellData(sheetName, colNum, 1), excel.getCellData(sheetName, colNum, rowNum));
	            data[rowNum - 2][0] = table;
	        }
	    }

	    return data;
	}


	public static boolean isTestRunnable(String testName, ExcelReader excel) {
		String sheetName = "test_suite";
		int rows = excel.getRowCount(sheetName);
		for (int row_num = 2; row_num <= rows; row_num++) {
			String testCase = excel.getCellData(sheetName, "TCID", row_num);
			if (testCase.equalsIgnoreCase(testName)) {
				String runmode = excel.getCellData(sheetName, "Runmode", row_num);
				if (runmode.equalsIgnoreCase("Y")) {
					return true;
				} else {
					return false;
				}
			}

		}
		return false;

	}

	public static String[] sendTo() {
		String sheetName = "EmailConfig";
		int rowNum = excel.getRowCount(sheetName);
		String elements[] = null;
		for (int to = 2; to <= rowNum; to++) {
			String value = excel.getCellData(sheetName, 0, to);
			for (int rows = 2; rows <= rowNum; rows++) {
				elements = value.split(";");
				for (int i = 0; i <= elements.length; i++) {
					return elements;
				}
			}
		}
		return null;

	}

	public static String[] sendCC() {
		String sheetName = "EmailConfig";
		int rowNum = excel.getRowCount(sheetName);
		String elements[] = null;
		for (int cc = 2; cc <= rowNum; cc++) {
			String value = excel.getCellData(sheetName, 1, cc);
			for (int rows = 2; rows <= rowNum; rows++) {
				elements = value.split(";");
				for (int i = 0; i <= elements.length; i++) {
					return elements;
				}
			}
		}
		return null;
	}
	public static String messageBody()
	{
		File file=new File(path);
		StringBuilder strb=new StringBuilder();
		try {
			BufferedReader br=new BufferedReader(new FileReader(file));
			String st;
			while((st=br.readLine())!=null)
			{
				strb.append(st);
			}
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		mailContent=strb.toString();
		return mailContent;
			
		}
	}


