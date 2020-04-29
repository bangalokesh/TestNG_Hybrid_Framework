package com.organization.testng_hybrid_framework.util;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import com.relevantcodes.extentreports.ExtentTest;


public class Listener implements ITestListener, IInvokedMethodListener{
	public static final String DRIVER_ATTRIBUTE = "driver";
	private static final Logger logger = Logger.getLogger(Listener.class.getName());
	private WebDriver driver;
	public ExtentTest test;
    //private static final String UTILS_KEY = "utils";
	
	public void takeScreenshot(String testName, WebDriver driver) throws IOException{
		logger.info("taking screenshot");
		try{
		// decide name - time stamp
				Date d = new Date();
				String screenshotFile=d.toString().replace(":", "_").replace(" ","_")+".png";
				String path=Constants.SCREENSHOT_PATH+testName+"_"+screenshotFile;
				// take screenshot
				File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(srcFile, new File(path));
				// embed
				logger.info("attaching screenshot to logs");
		}catch(Exception exception){
			exception.printStackTrace();
			logger.fatal("exception while taking screenshot");
		}
	}
	
	private void printTestResults(ITestResult result) {
		Reporter.log("TestName = " + result.getTestName(), true);
		Reporter.log("Test Method resides in " + result.getTestClass().getName(), true);
		if (result.getParameters().length != 0) {
			String params = null;
			for (Object parameter : result.getParameters()) {
				params += parameter.toString() + ",";
			}
			Reporter.log("Test Method had the following parameters : " + params, true);
		}
		String status = null;
		switch (result.getStatus()) {
		case ITestResult.SUCCESS:
			status = "Pass";
			break;
		case ITestResult.FAILURE:
			status = "FATAL";
			break;
		case ITestResult.SKIP:
			status = "Skipped";
		}
		Reporter.log("Test Status: " + status, true);
	}

	@Override
	public void afterInvocation(IInvokedMethod arg0, ITestResult arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeInvocation(IInvokedMethod arg0, ITestResult arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFinish(ITestContext arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(ITestContext arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub	
		try{
			final WebDriver driver = (WebDriver) result.getTestContext().getAttribute(DRIVER_ATTRIBUTE);
			if (driver != null) {
				takeScreenshot(result.getTestName(),driver);
			}
		}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
            //createScreenshot(result, driver);
			printTestResults(result);
            Reporter.log("Test failed " + result.getName(), true);
	}

	@Override
	public void onTestSkipped(ITestResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestStart(ITestResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestSuccess(ITestResult arg0) {
		// TODO Auto-generated method stub
		
	}

}
