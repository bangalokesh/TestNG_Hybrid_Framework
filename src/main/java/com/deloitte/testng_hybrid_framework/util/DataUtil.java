package com.deloitte.testng_hybrid_framework.util;

import java.util.Hashtable;
import org.testng.annotations.Listeners;
@Listeners(Listener.class)

public class DataUtil {
	
	public static Object[][] getData(Xls_Reader xls,String testname ){
		int teststartrownum = 1;
		String sheetName = "TestData";
		
		//find the test case start point
		while(!xls.getCellData(sheetName, 0,teststartrownum).equals(testname)){
			teststartrownum++;
		}
		System.out.println("Test Starts from row num = " + teststartrownum);
		int colstartrownum = teststartrownum+1;
		int datastartrownum = colstartrownum +1;
		
		//calculate number of rows of data for the test case
		int datarows=0;
		while(!xls.getCellData(sheetName,0,datastartrownum+datarows).equals("")){
			datarows++;
		}
		System.out.println("Total data rows for the test case are = " + datarows);
		
		//calculate number of columns of data for the test case
		
		int datacols = 0;
		while(!xls.getCellData(sheetName, datacols, colstartrownum).equals("")){
			datacols++;
		}
		System.out.println("Total data columns for the test case are = " + datacols);
		
		//read the data
		int row=0;
		Object[][] data = new Object[datarows][1];
		Hashtable<String, String> table =null;
		for(int rnum=datastartrownum;rnum<datarows+datastartrownum;rnum++){
			table = new Hashtable<String, String>();
			for(int cnum=0;cnum<datacols;cnum++){
				String key = xls.getCellData(sheetName, cnum, colstartrownum);
				String value = xls.getCellData(sheetName, cnum, rnum);
				table.put(key, value);
				}
			data[row][0]= table; 
			row++;
			
			
		}
		return data;
	}
	
	
	//true = N
	//false = Y
	public static boolean isSkip(Xls_Reader xls, String testname){
		int rows = xls.getRowCount(Constants.TESTCASES_SHEET);
		String RunMode = "true";
		boolean x = true;
		for(int rnum=2;rnum<=rows;rnum++){
			String tcid = xls.getCellData(Constants.TESTCASES_SHEET, Constants.TCID_COL, rnum);
			if(tcid.equals(testname)){
				RunMode = xls.getCellData(Constants.TESTCASES_SHEET, Constants.RUNMODE_COL, rnum);
				if(RunMode.equals("Y")){
					x= false;
				}
				else 
					x= true;
			}
		}
		return x;
	}
	
}
