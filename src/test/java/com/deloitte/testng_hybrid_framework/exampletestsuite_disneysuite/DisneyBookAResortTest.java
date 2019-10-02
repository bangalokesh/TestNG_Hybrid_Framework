package com.deloitte.testng_hybrid_framework.exampletestsuite_disneysuite;

import java.io.IOException;

import java.sql.SQLException;
import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.deloitte.testng_hybrid_framework.BaseTest;
import com.deloitte.testng_hybrid_framework.Keywords;
import com.deloitte.testng_hybrid_framework.util.Constants;
import com.deloitte.testng_hybrid_framework.util.DataUtil;
import com.deloitte.testng_hybrid_framework.util.ExtentManager;
import com.deloitte.testng_hybrid_framework.util.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;

public class DisneyBookAResortTest extends BaseTest{
		String RunName = ExtentManager.getRunName();
		String CreatedBy = ExtentManager.getCreatedBy();
		String skip = "";
		private static final Logger logger = Logger.getLogger(DisneyBookAResortTest.class.getName());
		
		@BeforeTest
		public void init(){
			suitename = "exampletestsuite_disneysuite";
			testname = "Disney_BookAResort_Test";
			xls = new Xls_Reader(Constants.DISNEYSUITE_XLS);
		}
			 
		 @Test(dataProvider="getData")
		 public void calcTest(Hashtable<String,String>data) throws Exception{
			 test = rep.startTest(testname);
			 test.log(LogStatus.INFO, data.toString());
			 if(DataUtil.isSkip(xls, testname)|| data.get("RunMode").equals("N")){
				 skip = "SKIPPED";
				 test.log(LogStatus.SKIP, "Skipping the test as RunMode = N");
				 throw new SkipException("Skipping the test as RunMode = N");
			 }
			 else{
				 test.log(LogStatus.INFO, "Starting test = " + testname);
				 app = new Keywords(test);
				 app.executeKeywords(suitename,testname, xls,data);
			 }
		 }
		 
		 @AfterMethod
		 public void closeTest() throws IOException, SQLException, NullPointerException{
			  if((app.getGenericKeywords().failflag.equals(false) && (app.getGenericKeywords().fatalflag.equals(false)))){
				  app.getGenericKeywords().reportPass(testname);
				  app.getGenericKeywords().logInfo("PASS", testname, "Test Passed");
				  app.getGenericKeywords().insertDBDetailRecord(suitename, testname, "n/a", "PASS",RunName, CreatedBy);
			  }
			  else if((app.getGenericKeywords().failflag.equals(false) && (app.getGenericKeywords().fatalflag.equals(true)))){
				  app.getGenericKeywords().reportException(testname, "test failed");
				  app.getGenericKeywords().logInfo("FATAL", testname, "Test FATAL Exception");
				  app.getGenericKeywords().insertDBDetailRecord(suitename, testname, "n/a", "FATAL",RunName, CreatedBy);
			  }
			  else if(app.getGenericKeywords().failflag.equals(true)){
				  app.getGenericKeywords().reportDefect(testname, "test failed");
				  app.getGenericKeywords().logInfo("FAIL", testname, "Test Failed");
				  app.getGenericKeywords().insertDBDetailRecord(suitename, testname, "n/a", "FAIL",RunName, CreatedBy);
			  }
		  }

}
