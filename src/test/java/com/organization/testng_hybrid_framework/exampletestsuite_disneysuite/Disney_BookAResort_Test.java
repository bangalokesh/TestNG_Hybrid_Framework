package com.deloitte.testng_hybrid_framework.exampletestsuite_disneysuite;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Hashtable;
import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
import com.deloitte.testng_hybrid_framework.Keywords;
import com.deloitte.testng_hybrid_framework.basetestsuite.BaseTest;
import com.deloitte.testng_hybrid_framework.util.Constants;
import com.deloitte.testng_hybrid_framework.util.DataUtil;
import com.deloitte.testng_hybrid_framework.util.ExtentManager;
import com.deloitte.testng_hybrid_framework.util.Listener;
import com.deloitte.testng_hybrid_framework.util.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;
@Listeners(Listener.class)

public class Disney_BookAResort_Test extends BaseTest{
	String RunName = ExtentManager.getRunName();
	String CreatedBy = ExtentManager.getCreatedBy();
	String skip = "";
	private final Logger logger = Logger.getLogger(Disney_BookAResort_Test.class.getName());
	
	@BeforeTest
	public void init(){
		suitename= "exampletestsuite_disneysuite";
		testname = "Disney_BookAResort_Test";
		xls = new Xls_Reader(Constants.DISNEYSUITE_XLS);
	}
		 
	 @Test(dataProvider="getData")
	 public void AmazonSearchTest(Hashtable<String,String>data) throws IOException, SQLException{
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
			  app.getGenericKeywords().insertDBDetailRecord(suitename, testname, "n/a", "SKIPPED",RunName, CreatedBy);
		  }
		  else if((app.getGenericKeywords().failflag.equals(false) && (app.getGenericKeywords().fatalflag.equals(false)))){
			  app.getGenericKeywords().reportPass(testname);
			  app.getGenericKeywords().logInfo("PASS", "Test Passed");
			  app.getGenericKeywords().insertDBDetailRecord(suitename, testname, "n/a", "PASS",RunName, CreatedBy);
		  }
		  else if((app.getGenericKeywords().failflag.equals(false) && (app.getGenericKeywords().fatalflag.equals(true)))){
			  app.getGenericKeywords().reportException(testname, "test failed");
			  app.getGenericKeywords().logInfo("FATAL", "Test FATAL Exception");
			  app.getGenericKeywords().insertDBDetailRecord(suitename, testname, "n/a", "FATAL",RunName, CreatedBy);
		  }
		  else if(app.getGenericKeywords().failflag.equals(true)){
			  app.getGenericKeywords().reportDefect(testname, "test failed");
			  app.getGenericKeywords().logInfo("FAIL", "Test Failed");
			  app.getGenericKeywords().insertDBDetailRecord(suitename, testname, "n/a", "FAIL",RunName, CreatedBy);
		  }
	  }
}
