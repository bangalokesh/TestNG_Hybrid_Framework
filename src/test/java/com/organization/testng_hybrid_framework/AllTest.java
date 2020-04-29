package com.organization.testng_hybrid_framework;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Hashtable;

import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import com.organization.testng_hybrid_framework.util.Constants;
import com.organization.testng_hybrid_framework.util.DataUtil;
import com.organization.testng_hybrid_framework.util.ExtentManager;
import com.organization.testng_hybrid_framework.util.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;

public class AllTest extends BaseTest{
	
	public static Xls_Reader xls, masterSuiteXls, suiteXls;
	public static String testname;
	public static String suitename;
	public static String suiteXlsPath = Constants.SUITE_PATH;
	String skip = "";
	public static int masterRowCount = 0;
	String runName = ExtentManager.getRunName();
	String createdBy = ExtentManager.getCreatedBy();
	
	@BeforeTest
	public void init(){
		String suitename = getSuiteName();
		testname = getTestName(suitename);
		setSuiteName(suitename);
		setTestName(testname);
	}
	
	@DataProvider
	 public Object[][] getData(){
		Object[][] data = new Object[0][0];
		suiteXlsPath = getSuitePath(suitename);
		if(!suiteXlsPath.contains("null")) {
			xls = new Xls_Reader(suiteXlsPath);
			data = DataUtil.getData(xls, testname);
		}
		return data;
	 }
	
	 	
	 public void allTest(Hashtable<String,String>data) throws Exception {
		 test = rep.startTest(testname);
		 System.out.println(data.toString());
		 if(DataUtil.isSkip(xls, testname)|| data.get("RunMode").equals("N")){
			 skip = "SKIPPED";
			 test.log(LogStatus.SKIP, "Skipping the test as RunMode = N");
			 throw new SkipException("Skipping the test as RunMode = N");
		 }
		 else {
			 test.log(LogStatus.INFO, "Starting test = " + testname);
			 app = new Keywords(test);
			 app.executeKeywords(suitename,testname, xls,data);	
		 }
	 }
	 
	 @AfterMethod
	  public void closeTest() throws IOException, SQLException, NullPointerException{
		  if(skip.equals("SKIPPED")){
			  app.getGenericKeywords().insertDBDetailRecord(suitename, testname, "n/a", "SKIPPED", runName, createdBy);
		  }
		  else if((app.getGenericKeywords().failflag.equals(false) && (app.getGenericKeywords().fatalflag.equals(false)))){
			  app.getGenericKeywords().reportPass(testname);
			  app.getGenericKeywords().logInfo("PASS", testname, "Test Passed");
			  app.getGenericKeywords().insertDBDetailRecord(suitename, testname, "n/a", "PASS", runName, createdBy);
		  }
		  else if((app.getGenericKeywords().failflag.equals(false) && (app.getGenericKeywords().fatalflag.equals(true)))){
			  app.getGenericKeywords().reportException(testname, "test failed");
			  app.getGenericKeywords().logInfo("FATAL", testname, "Test FATAL Exception");
			  app.getGenericKeywords().insertDBDetailRecord(suitename, testname, "n/a", "FATAL",runName, createdBy);
		  }
		  else if(app.getGenericKeywords().failflag.equals(true)){
			  app.getGenericKeywords().reportDefect(testname, "test failed");
			  app.getGenericKeywords().logInfo("FAIL", testname, "Test Failed");
			  app.getGenericKeywords().insertDBDetailRecord(suitename, testname, "n/a", "FAIL",runName, createdBy);
		  }
	  }
	 
	@AfterTest
	 public void updateSuiteXls() {
		setTestRunMode(testname, suitename, "N"); 
	 }
	 

	 @AfterSuite
	 public void updateMasterSuiteXls() {
		 setSuiteRunMode (suitename, "N");
	 }
	 
	 public String getTestName (String suitename) {
			int i = 2;
			String suiteName = (suitename + ".xlsx"); 
			suiteXlsPath = Constants.SUITE_PATH + suiteName;
			Xls_Reader suiteXls = new Xls_Reader(suiteXlsPath);
			outerloop:
			while (suiteXls.getCellData ("TestCases", 1, i) != "") {
				if (suiteXls.getCellData("TestCases", 2, i).equalsIgnoreCase("Y")) {
					testname = suiteXls.getCellData ("TestCases", 1, i);
					break outerloop;
				}
				i++;
			}
			return testname;
		}
			
		 
		 public String getSuiteName () {
			Xls_Reader masterSuiteXls = new Xls_Reader(Constants.MASTERSUITE_XLS);
			int i = 2;
			outerloop:
			while (masterSuiteXls.getCellData ("TestSuites", 1, i) != "") {
				if (masterSuiteXls.getCellData ("TestSuites", 2, i).equalsIgnoreCase("Y")) {
					suitename = masterSuiteXls.getCellData ("TestSuites", 1, i);
					break outerloop;
				}
				i++;
			}
			setSuiteName(suitename);
			return suitename;
		}
		
		public static void setTestRunMode (String testName, String suiteName, String RunMode) {
			Xls_Reader suiteXls = new Xls_Reader(suiteXlsPath);
			int row = suiteXls.getCellRowNum("TestCases", "TCID", testName);
			suiteXls.setCellData("TestCases", "RunMode", row, RunMode);
		}
		
		public static void setTestRunModes (String testName, String suiteName, String RunMode) {
			String suitePath = suiteXlsPath + suiteName + ".xlsx";
			Xls_Reader suiteXls = new Xls_Reader(suitePath);
			int row = suiteXls.getCellRowNum("TestCases", "TCID", testName);
			suiteXls.setCellData("TestCases", "RunMode", row, RunMode);
		}
			
		public static void setSuiteRunMode (String suiteName, String RunMode) {
			Xls_Reader masterSuiteXls = new Xls_Reader(Constants.MASTERSUITE_XLS);
			int row = masterSuiteXls.getCellRowNum("TestSuites", "TSID", suiteName);
			masterSuiteXls.setCellData("TestSuites", "RunMode", row, RunMode);
		}
		
		public int getMasterRowCount() {
			Xls_Reader masterSuiteXls = new Xls_Reader(Constants.MASTERSUITE_XLS);
			int rowCount = masterSuiteXls.getRowCount("TestSuites");
			return rowCount;
		}
		
		public int getSuiteRowCount(String suitename) {
			Xls_Reader SuiteXls = new Xls_Reader(getSuitePath(suitename));
			int rowCount = SuiteXls.getRowCount("TestSuites");
			return rowCount;
		}
		
		public String getSuitePath(String suitename) {
			String suiteName = (suitename + ".xlsx"); 
			suiteXlsPath = System.getProperty("user.dir") + "\\data\\suites\\" + suiteName;
			return suiteXlsPath;
		}

		public String returnSuiteName () {
			return suitename;
		}
		
		public String returnTestName () {
			return testname;
		}
		
		public void setSuiteName (String suiteName) {
			suitename = suiteName;
			BaseTest.suitename = suitename;
		}
		
		public void setTestName (String testName) {
			testname = testName;
			BaseTest.testname = testname;
		}
}
