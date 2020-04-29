package com.organization.testng_hybrid_framework.basetestsuite;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;

import com.organization.testng_hybrid_framework.Keywords;
import com.organization.testng_hybrid_framework.util.DataUtil;
import com.organization.testng_hybrid_framework.util.ExtentManager;
import com.organization.testng_hybrid_framework.util.Listener;
import com.organization.testng_hybrid_framework.util.Xls_Reader;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
@Listeners(Listener.class)

public class BaseTest {
	
	public Keywords app;
	public ExtentReports rep = ExtentManager.getInstance();
	public ExtentTest test;
	public Xls_Reader xls;
	public static String testname;
	public static String suitename;
	public static String testrunid = ExtentManager.getRunName();
	private static final Logger logger = Logger.getLogger(BaseTest.class.getName());
	String createdBy = ExtentManager.getCreatedBy();
	
	@BeforeTest
		public void loadLog4j(){
			String log4jConfigPath = System.getProperty("user.dir")+"//src//test//resources//log4j.properties";
			PropertyConfigurator.configure(log4jConfigPath);	
		}
	
	@DataProvider
	 public Object[][] getData(){
		 return DataUtil.getData(xls, testname);
	 }
	
	@AfterMethod
	 public void quit() throws NumberFormatException, SQLException, IOException{
		app.getGenericKeywords().logInfo("INFO", "executing after Test for test name" + testname);
		 if(rep!=null){
			 rep.endTest(test);
			 rep.flush();
			 app.getGenericKeywords().logInfo("INFO", "Quitting the test");
		 }
		 if(app!=null){
			 app.getGenericKeywords().closeBrowser();
			 app.getGenericKeywords().logInfo("INFO", "Closing the Browser / Application");
		 }
	}
	
	@AfterTest
	public void testcaseReporting() throws NumberFormatException, SQLException{
		testrunid = ExtentManager.getRunName();
		app.getGenericKeywords().insertDBSummaryRecord(testrunid, suitename, testname, createdBy);
		app.getGenericKeywords().logInfo("INFO", "Creating a Summary Record for the Run");
	}
	
	@AfterSuite
	public void Reporting() throws SQLException {
		app.getGenericKeywords().logInfo("INFO", "Executing AfterSuite Method");
		testrunid = ExtentManager.getRunName();
		app.getGenericKeywords().insertDBSummaryRecord(testrunid, suitename, createdBy);
	}
}
