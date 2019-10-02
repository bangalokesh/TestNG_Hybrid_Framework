package com.deloitte.testng_hybrid_framework;

import java.io.FileInputStream;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.testng.annotations.Listeners;

import com.deloitte.testng_hybrid_framework.util.Constants;
import com.deloitte.testng_hybrid_framework.util.ExtentManager;
import com.deloitte.testng_hybrid_framework.util.Listener;
import com.deloitte.testng_hybrid_framework.util.Xls_Reader;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
@Listeners(Listener.class)

public class Keywords {
	AppKeywords app;
	ExtentTest test;
	String result;
	public Properties prop;
	public Properties suiteProp;
	AndroidDriver<MobileElement> aDriver = GenericKeywords.aDriver;
	private final Logger logger = Logger.getLogger(Keywords.class.getName());
	

	public Keywords(ExtentTest test) {
		this.test=test;
		prop = new Properties();
		try{
			FileInputStream fs;
			fs = new FileInputStream(Constants.RUNNAMEPROPERTIES);
			prop.load(fs);
			fs.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}

	public void executeKeywords (
			String suitename,
			String testUnderExecution,
			Xls_Reader xls,
			Hashtable<String,String> testData
			) throws Exception{
		app = new AppKeywords(test);
		String testrunkey = prop.getProperty("RunName");
		String createdby = prop.getProperty("CreatedBy");
		int rows = xls.getRowCount(Constants.KEYWORDS_SHEET);
		
		suiteProp = new Properties();
		String suitePropertiesFile = suitename+".properties";
		FileInputStream fso;
		fso = new FileInputStream(System.getProperty("user.dir")+"//src//test//resources//"+suitePropertiesFile);
		suiteProp.load(fso);
		
			for(int rNum=2;rNum<=rows;rNum++){
			String tcid  = xls.getCellData(Constants.KEYWORDS_SHEET, Constants.TCID_COL, rNum);
			if(tcid.equals(testUnderExecution)){
				String keyword  = xls.getCellData(Constants.KEYWORDS_SHEET, Constants.KEYWORD_COL, rNum);
				String object  = xls.getCellData(Constants.KEYWORDS_SHEET, Constants.OBJECT_COL, rNum);
				String selectKey  = xls.getCellData(Constants.KEYWORDS_SHEET, Constants.SELECTKEY, rNum);
				String selectData  = xls.getCellData(Constants.KEYWORDS_SHEET, Constants.ENTERSELECTDATA, rNum);

				
				result="";
				executeMethods (keyword, object, suiteProp, testData, selectKey, selectData);
							
				// central place reporing failure
				/*if(result.startsWith(Constants.PASS)){
					app.reportPass(tcid);
					app.insertDBDetailRecord(suitename, tcid, keyword, "PASS", testrunkey, createdby);
				}			
				else if (result.startsWith(Constants.FATAL)){
					app.reportException(tcid, result+ "exception found");
					app.insertDBDetailRecord(suitename, tcid, keyword, "FATAL", testrunkey, createdby);
				}
				else if(result.startsWith(Constants.FAIL)){
					app.reportDefect(tcid, result+ " Assertion Failed");
					app.insertDBDetailRecord(suitename, tcid, keyword, "FAIL", testrunkey, createdby);
				}*/
			}
		}
	}
	
	public GenericKeywords getGenericKeywords(){
		return app;
	}
	
	
	
	public void executeMethods (String keyword, String object, Properties suiteProp, Hashtable <String, String> testData, String selectKey, String selectData)  {
		try {
			@SuppressWarnings("rawtypes")
			Class[] argTypesFive = new Class[] {Hashtable.class, Properties.class, String.class, String.class, String.class};
			
			Class <?> c = Class.forName("com.deloitte.testng_hybrid_framework.AppKeywords");
	        Object cls = Class.forName("com.deloitte.testng_hybrid_framework.AppKeywords").getConstructor(ExtentTest.class).newInstance(test);
	        Method method = null;
	        method = cls.getClass().getMethod(keyword, argTypesFive);
	        if(method.getName().equalsIgnoreCase(keyword)){
	        	int countOfParameters = method.getParameterCount();
		        if(countOfParameters == 5) {
		        	 if((cls.getClass().getMethod(keyword, argTypesFive)) != null) {
		        		 method = cls.getClass().getMethod(keyword, argTypesFive);
				         method.invoke(cls, testData, suiteProp, object, selectKey, selectData);
		        	 }
		        } 
		        logger.info("Method Invoked = " + method.getName());
	        } else {
		        test.log(LogStatus.FATAL, keyword, "Invalid Keyword / Keyword not found in GenericKeywords & AppKeywords class");
		        result = Constants.FATAL;
	        }
		} catch (Exception e) {
			e.printStackTrace();
			test.log(LogStatus.FATAL, keyword, "Invalid Keyword / Keyword not found in GenericKeywords & AppKeywords class");
	        result = Constants.FATAL;
		}
		
	}
	
	public void updateDbTestResults(String result, String keyword) throws IOException, SQLException {
		test = this.test;
		app = new AppKeywords(test);
		BaseTest bt = new BaseTest();
		String suitename = bt.returnSuiteName();
		String tcid = bt.returnTestName();
		String testrunkey = ExtentManager.getRunName();
		String createdby = prop.getProperty("CreatedBy");
		// central place reporing failure
		if(result.startsWith(Constants.PASS)){
			app.reportPass(tcid);
			app.insertDBDetailRecord(suitename, tcid, keyword, "PASS", testrunkey, createdby);
		}			
		else if (result.startsWith(Constants.FATAL)){
			app.reportException(tcid, result+ "exception found");
			app.insertDBDetailRecord(suitename, tcid, keyword, "FATAL", testrunkey, createdby);
		}
		else if(result.startsWith(Constants.FAIL)){
			app.reportDefect(tcid, result+ " Assertion Failed");
			app.insertDBDetailRecord(suitename, tcid, keyword, "FAIL", testrunkey, createdby);
		}
	}
	

}
