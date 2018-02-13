package com.deloitte.testng_hybrid_framework;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Properties;

import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Listeners;

import com.deloitte.testng_hybrid_framework.util.Constants;
import com.deloitte.testng_hybrid_framework.util.Listener;
import com.deloitte.testng_hybrid_framework.util.Xls_Reader;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
@Listeners(Listener.class)

public class Keywords {
	AppKeywords app;
	ExtentTest test;
	String result;
	public Properties prop;

	public Keywords(ExtentTest test) {
		this.test=test;
		prop = new Properties();
		try{
			FileInputStream fs;
			fs = new FileInputStream(System.getProperty("user.dir")+"//src//test//resources//runname.properties");
			prop.load(fs);
			fs.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}


	
	public void executeKeywords(
			String suitename,
			String testUnderExecution,
			Xls_Reader xls,
			Hashtable<String,String> testData
			) throws IOException, SQLException{
		app = new AppKeywords(test);
		String testrunkey = prop.getProperty("RunName");
		String createdby = prop.getProperty("CreatedBy");
		int rows = xls.getRowCount(Constants.KEYWORDS_SHEET);
		
			for(int rNum=2;rNum<=rows;rNum++){
			String tcid  = xls.getCellData(Constants.KEYWORDS_SHEET, Constants.TCID_COL, rNum);
			if(tcid.equals(testUnderExecution)){
				String keyword  = xls.getCellData(Constants.KEYWORDS_SHEET, Constants.KEYWORD_COL, rNum);
				String object  = xls.getCellData(Constants.KEYWORDS_SHEET, Constants.OBJECT_COL, rNum);
				String key  = xls.getCellData(Constants.KEYWORDS_SHEET, Constants.DATA_COL, rNum);
				String keyPlatform = xls.getCellData(Constants.KEYWORDS_SHEET, Constants.DATA2_COL, rNum);
				String keyPlatformName = xls.getCellData(Constants.KEYWORDS_SHEET, Constants.PLATFORMNAME_COL, rNum);
				String keyDeviceName = xls.getCellData(Constants.KEYWORDS_SHEET, Constants.DEVICENAME_COL, rNum);
				String keyApp = xls.getCellData(Constants.KEYWORDS_SHEET, Constants.APPLICATION_COL, rNum);
				String keyAppPackage = xls.getCellData(Constants.KEYWORDS_SHEET, Constants.APPPACKAGE_COL, rNum);
				String keyAppActivity = xls.getCellData(Constants.KEYWORDS_SHEET, Constants.APPACTIVITY_COL, rNum);
				String keyPlatformVersion = xls.getCellData(Constants.KEYWORDS_SHEET, Constants.PLATFORMVERSION_COL, rNum);
				String data = testData.get(key);
				String data2 = testData.get(keyPlatform);
				String PlatformName =testData.get(keyPlatformName);
				String DeviceName =testData.get(keyDeviceName);
				String App =testData.get(keyApp);
				String AppPackage =testData.get(keyAppPackage);
				String AppActivity =testData.get(keyAppActivity);
				String PlatformVersion =testData.get(keyPlatformVersion);
				
				result="";
				
				switch (keyword){
					case "openBrowser":
						System.out.println(testData.get("Browser") + " -- " + testData.get("Platform"));
						result = app.openBrowser(testData.get("Browser"), testData.get("Platform"));
						break;
					case "openMobBrowser":
						result = app.openMobBrowser(testData.get("PlatformName"), testData.get("DeviceName"), testData.get("PlatformVersion"), testData.get("Browser"));
						break;
					case "openMobApp":
						result = app.openMobApp(testData.get("PlatformName"), testData.get("DeviceName"), testData.get("App"), testData.get("AppPackage"), testData.get("AppActivity"), testData.get("PlatformVersion"));
						break;
					case "navigate":
						result = app.navigate(object);
						break;
					case "click":
						result = app.click(object);
						break;
					case "input":
						result = app.input(object,data);
						break;
					case "closeBrowser":
						result = app.closeBrowser();
						break;
					case "verifyText":
						result = app.verifyText(object, data);
						break;
					case "verifyElementPresent":
						result = app.verifyElementPresent(object);
						break;
					case "verifyElementNotPresent":
						result = app.verifyElementNotPresent(object);
						break;
					case "EnterTime":
						test.log(LogStatus.INFO, "Submitting Time in DTE");
						result = app.EnterTime(testData);
						break;
					case "filterProductBySearch":
						result = app.filterProductBySearch(testData);
						break;
				}
				
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
	}
	
	public GenericKeywords getGenericKeywords(){
		return app;
	}
	

}
