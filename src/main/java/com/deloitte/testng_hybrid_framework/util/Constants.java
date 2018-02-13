package com.deloitte.testng_hybrid_framework.util;

public class Constants {
	public static final String MASTERSUITE_XLS = System.getProperty("user.dir")+"\\data\\mastersuites\\MasterSuite.xlsx";
	public static final String SUITE_PATH = System.getProperty("user.dir")+ "\\data\\suites"; 
	public static final String DTESUITE_XLS = System.getProperty("user.dir")+"\\data\\suites\\exampletestsuite_dtesuite.xlsx";
	public static final String AMAZONSUITE_XLS = System.getProperty("user.dir")+"\\data\\suites\\exampletestsuite_amazonsuite.xlsx";
	public static final String CONTACTMGR_XLS = System.getProperty("user.dir")+"\\data\\suites\\exampleapptestsuite_contactmanager.xlsx";
	public static final String DISNEYSUITE_XLS = System.getProperty("user.dir")+"\\data\\suites\\exampletestsuite_disneysuite.xlsx";
	public static final String TESTSUIT_SHEET = "TestSuites";
	public static final String TESTCASES_SHEET = "TestCases";
	public static final String KEYWORDS_SHEET = "Keywords";
	public static final String TSID_COL = "TSID"; 
	public static final String TCID_COL = "TCID";
	public static final String KEYWORD_COL = "Keyword";
	public static final String OBJECT_COL = "Object";
	public static final String DATA_COL = "Data";
	public static final String DATA2_COL = "Data2";
	public static final String PLATFORMNAME_COL = "PlatformName";
	public static final String DEVICENAME_COL ="DeviceName";
	public static final String APPLICATION_COL ="Application";
	public static final String APPPACKAGE_COL = "AppPackage";
	public static final String APPACTIVITY_COL = "AppActivity";
	public static final String PLATFORMVERSION_COL = "PlatformVersion";
	public static final String RUNMODE_COL = "RunMode";
	public static final String SCREENSHOT_PATH = System.getProperty("user.dir")+ "\\Screenshots\\";
	public static final String REPORT_PATH = System.getProperty("user.dir")+ "\\Reports\\";
	public static final String PASS = "PASS";
	public static final String FAIL = "FAIL";
	public static final String FATAL = "FATAL";
}
