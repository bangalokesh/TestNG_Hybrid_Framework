package com.organization.testng_hybrid_framework.exampleAppTestSuite_ContactManager;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Hashtable;
import org.testng.SkipException;
import org.testng.annotations.BeforeTest;

import com.organization.testng_hybrid_framework.BaseTest;
import com.organization.testng_hybrid_framework.Keywords;
import com.organization.testng_hybrid_framework.util.Constants;
import com.organization.testng_hybrid_framework.util.DataUtil;
import com.organization.testng_hybrid_framework.util.ExtentManager;
import com.organization.testng_hybrid_framework.util.Listener;
import com.organization.testng_hybrid_framework.util.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;
@Listeners(Listener.class)

public class Contact_Manager_Test extends BaseTest{
	String RunName = ExtentManager.getRunName();
	String CreatedBy = ExtentManager.getCreatedBy();
	String skip = "";
	private final Logger logger = Logger.getLogger(Contact_Manager_Test.class.getName());
	
	@BeforeTest
	public void init(){
		suitename= "exampleAppTestSuite_ContactManager";
		testname = "Contact_Manager_Test";
		xls = new Xls_Reader(Constants.CONTACTMGR_XLS);
	}
		 
	 @Test(dataProvider="getData")
	 public void ContactManagerTest(Hashtable<String,String>data) throws Exception{
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
			 app.executeKeywords(suitename, testname, xls, data);	
		 }
	 }
	  
	  @AfterMethod
	  public void closeTest() throws IOException, SQLException, NullPointerException{
		  if(skip.equals("SKIPPED")){
			  app.getGenericKeywords().insertDBDetailRecord(suitename, testname, "n/a", "SKIPPED",RunName, CreatedBy);
		  }
		  else if((app.getGenericKeywords().failflag.equals(false) && (app.getGenericKeywords().fatalflag.equals(false)))){
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
