package com.deloitte.testng_hybrid_framework;

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

import com.deloitte.testng_hybrid_framework.util.Constants;
import com.deloitte.testng_hybrid_framework.util.DataUtil;
import com.deloitte.testng_hybrid_framework.util.ExtentManager;
import com.deloitte.testng_hybrid_framework.util.Listener;
import com.deloitte.testng_hybrid_framework.util.Xls_Reader;
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
	public static String suiteXlsPath = Constants.SUITE_PATH;;
	public static String createdBy = ExtentManager.getCreatedBy();
	private static final Logger logger = Logger.getLogger(BaseTest.class.getName());
	
	@BeforeTest
		public void loadLog4j() {
			String log4jConfigPath = Constants.LOG4JPROPERTIES;
			PropertyConfigurator.configure(log4jConfigPath);
			logger.info("loadedlog4j");
		}
	
	@DataProvider
	 public Object[][] getData(){
		 return DataUtil.getData(xls, testname);
	 }
	
	@AfterMethod
	 public void quit() throws NumberFormatException, SQLException, IOException{
		app.getGenericKeywords().logInfo("INFO", testname, "executing after Test for test name");
		 if(rep!=null){
			 rep.endTest(test);
			 rep.flush();
			 app.getGenericKeywords().logInfo("INFO", testname, "Quitting the test");
		 }
		 if(app!=null){
			 app.getGenericKeywords().closeBrowser();
			 app.getGenericKeywords().logInfo("INFO", testname, "Closing the Browser / Application");
		 }
		 logger.info("executed After Method in Base Page");
	}
	
	@AfterTest
	public void testcaseReporting() throws NumberFormatException, SQLException, IOException{
		testrunid = ExtentManager.getRunName(); 
		app.getGenericKeywords().insertDBSummaryRecord(testrunid, suitename, testname, createdBy);
		app.getGenericKeywords().logInfo("INFO", testrunid, "Creating a Summary Record for the Run");
		app.getGenericKeywords().insertServerDetailsDB(testrunid, testname, "AfterTest", "localhost");
	}
	
	@AfterSuite
	public void Reporting() throws SQLException, IOException {
		app.getGenericKeywords().logInfo("INFO", suitename, "Executing AfterSuite Method");
		testrunid = ExtentManager.getRunName();
		app.getGenericKeywords().insertDBSummaryRecord(testrunid, suitename, createdBy);
		app.getGenericKeywords().insertServerDetailsExtentReport(rep, "AfterSuite", "localhost");
		logger.info("executed After Method in Base Page");
	}
	
	public String returnSuiteName () {
		return suitename;
	}
	
	public String returnTestName () {
		return testname;
	}
	
	
	
}
