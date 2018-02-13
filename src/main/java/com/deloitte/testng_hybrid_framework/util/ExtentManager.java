package com.deloitte.testng_hybrid_framework.util;


//http://relevantcodes.com/Tools/ExtentReports2/javadoc/index.html?com/relevantcodes/extentreports/ExtentReports.html

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import org.testng.annotations.Listeners;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
@Listeners(Listener.class)

public class ExtentManager {
	private static ExtentReports extent;
	static String RunName;
	static String CreatedBy;
	static String ModifiedBy;

	public static ExtentReports getInstance() {
		if (extent == null) {
			Date d=new Date();
			//lokesh banga updated #10/08/2017
			String fileName=d.toString().replace(":", "_").replace(" ", "_");
			RunName ="Test_Execution_Run_No_" + fileName;
			String filePath=Constants.REPORT_PATH + RunName + ".html";
			extent = new ExtentReports(filePath, true, DisplayOrder.NEWEST_FIRST);
			extent.addSystemInfo("Selenium Version", "3.5.3").addSystemInfo(
					"Environment", "QA");
			CreatedBy = "root";
			ModifiedBy = "root";
			createRunName();
		}
		return extent;
	}
	
	public static void createRunName(){
		try{
			FileWriter fileWriter = new FileWriter(System.getProperty("user.dir")+"//src//test//resources//runname.properties");
			BufferedWriter writer = new BufferedWriter(fileWriter);
			writer.write("RunName = " + RunName);
			writer.newLine();
			writer.write("CreatedBy= " + CreatedBy);
			writer.newLine();
			writer.write("ModifiedBy= " + ModifiedBy);
			writer.flush();
			writer.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static String getRunName(){
		return RunName;
	}
	
	public static String getCreatedBy(){
		return CreatedBy;
	}
	
	public static String getModifiedBy(){
		return ModifiedBy;
	}
	
	
}
