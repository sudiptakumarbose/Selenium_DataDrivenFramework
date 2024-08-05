package com.utilities;

import java.util.Date;
import java.util.GregorianCalendar;

import jdk.internal.org.jline.utils.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.BufferedWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Calendar;

public class ReportCreator {
	
	public static Date sysDate = new Date();
	public static Date date = new Date();
	public static DateFormat dateFormat = new SimpleDateFormat("MM_dd_YYYY_HHmmss");
	public static String formattedSysDate = dateFormat.format(sysDate), formattedSysDateNew, formattedSysDateToday,
			moduleName = "", functionality = "", testcaseScenario = "", tcId = "", reportsPath = "";
	public static String folderPath = "";
	public static String docFileName = "";
	public static String gray = "DarkGrey";
	public static String red = "red";
	public static String green = "green";
	public static String Yellow = "Yellow";
	public static String Orange = "Orange";
	public static String black = "black";
	public static String blue = "LightSkyBlue";
	public static BufferedWriter bufferedWriter = null, bufferedWriterindivScenario = null;
	public static int i = 0, ii = 0, j = 0, a = 0, sno = 0, totalTC = 0, passCount = 0, failCount = 0;
	public static String path;
	public static String SnapShotPath;

	

	public static String individualReport()
	{
		sysDate=new Date();
		formattedSysDateNew=dateFormat.format(sysDate);
		new File("Reports").mkdir();
		new File("Reports//"+formattedSysDate).mkdir();
		folderPath="Reports//"+formattedSysDate;
		new File("Reports//"+formattedSysDate+"//"+testcaseScenario).mkdir();
		new File("Reports//"+formattedSysDate+"//"+testcaseScenario+"//").mkdir();
		SnapShotPath="Reports//"+formattedSysDate+"//";
		System.out.println("Snapshotpath = "+SnapShotPath);
		path="Reports//"+formattedSysDate+"//"+testcaseScenario+"//"+"//"+testcaseScenario+"_"+formattedSysDateNew+".htm";
		File indivhtmlReportPath=new File(path);
		SimpleDateFormat dateFormat1=new SimpleDateFormat("MM/dd/YYYY hh:mm a zzz");
		formattedSysDateToday=dateFormat1.format(sysDate);
		Calendar cal=GregorianCalendar.getInstance();
		System.out.println("Time = "+dateFormat1.format(cal.getTime()));
		int currentHour=cal.get(Calendar.HOUR_OF_DAY);
		System.out.println(cal.get(Calendar.YEAR));
		int shift=0;
		if(currentHour>6 && currentHour<13)
		{
			shift=1;
			
		}else {
			shift=2;
		}
		
			try {
				bufferedWriterindivScenario=new BufferedWriter(new FileWriter(indivhtmlReportPath));
				bufferedWriterindivScenario.write("<html>");
				bufferedWriterindivScenario.write("<body bgcolor="+ "white" +" style=\"font-family:calibri;\">");
				bufferedWriterindivScenario.write("<p style=\font-size:larger;\">Hi Team,</p>");
				bufferedWriterindivScenario.write("<p style=\"font-size:larger;\"> Please find the report of the Account information as on : "+formattedSysDateToday+"</p>");
				bufferedWriterindivScenario.write("<center><h2>Banking Customer Information status </h2></center>");
				bufferedWriterindivScenario.write("<hr>");
				
			}catch(IOException e) {
				e.printStackTrace();
			}
			
		
		return path;
		
		}
	public static void tableHeading() 
	{
		try {
			bufferedWriterindivScenario.write("<center><table border="+ 5 +" ");
			bufferedWriterindivScenario.write("<tr>");
			bufferedWriterindivScenario.write("<th width=" + 75 +"bgcolor="+ gray +"><center>First_Name</center></th>");
			bufferedWriterindivScenario.write("<th width=" + 200 +"bgcolor="+ gray +"><center>Last_Name</center></th>");
			bufferedWriterindivScenario.write("<th width=" + 75 +"bgcolor="+ gray +"><center>Post_code</center></th>");
			bufferedWriterindivScenario.write("</tr>");
		}catch(IOException e)
		{
			e.printStackTrace();
		}
		}
	public static void addTestCaseData(String firstName, String lastName, String accountNumber) {
		  try {
		    bufferedWriterindivScenario.write("<tr>");
		    bufferedWriterindivScenario.write("<td width=" + 200 + " bgcolor=" + gray + "><center>" + firstName + "</center></td>");
		    bufferedWriterindivScenario.write("<td width=" + 200 + " bgcolor=" + gray + "><center>" + lastName + "</center></td>");
		    bufferedWriterindivScenario.write("<td width=" + 200 + " bgcolor=" + gray + "><center>" + accountNumber + "</center></td>");
		    bufferedWriterindivScenario.write("</tr>");
		  } catch (IOException e) {
		    e.printStackTrace();
		  }
		}
	public static void tableCompletion()
	{
		try {
			bufferedWriterindivScenario.write("</table></center>");
			bufferedWriterindivScenario.write("<br><br><br>");
			bufferedWriterindivScenario.write("<center><h2>Report Complete</h2></center>");
			bufferedWriterindivScenario.write("</tr>");
			}catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	public static void reportCompletion()
	{
		try {
			bufferedWriterindivScenario.write("<p style=\font-size:larger;\">Thanks,</p>");
			bufferedWriterindivScenario.write("<p style=\"font-size:larger; color:blue;\"> Automation Bot</p>");
			bufferedWriterindivScenario.write("</body>");
			bufferedWriterindivScenario.write("</html>");
			bufferedWriterindivScenario.close();
			
		}catch(IOException e)
		{
			
		}
	}
}
	

	
	