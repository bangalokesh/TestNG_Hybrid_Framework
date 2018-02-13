package com.deloitte.testng_hybrid_framework.basetestsuite;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.testng.TestNG;
import org.testng.annotations.Listeners;
import org.testng.xml.Parser;
import org.testng.xml.XmlSuite;
import com.deloitte.testng_hybrid_framework.XmlGenerator;
import com.deloitte.testng_hybrid_framework.util.Constants;
import com.deloitte.testng_hybrid_framework.util.Listener;
import com.deloitte.testng_hybrid_framework.util.Xls_Reader;
@Listeners(Listener.class)

public class Parallel_Execution {

	public static void main(String[] args) throws FileNotFoundException, ParserConfigurationException, IOException, org.xml.sax.SAXException, InterruptedException {
		try{
			Xls_Reader xls = new Xls_Reader(Constants.MASTERSUITE_XLS);
			XmlGenerator.masterSuitGeneration(xls);
			XmlGenerator.creatTestNgSuiteXml();
			TestNG testng = new TestNG(); 
			Thread.sleep(20);
			testng.setXmlSuites((List <XmlSuite>)(new Parser(System.getProperty("user.dir")+"//src//test//resources//MasterSuite.xml").parse()));		
			testng.setSuiteThreadPoolSize(2);
			testng.run();
		}catch(Exception e){
			e.printStackTrace();
		}
    }	
}
