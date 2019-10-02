package com.deloitte.api.soap.Scripts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.http.client.ClientProtocolException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import com.deloitte.api.utilities.ExcelRead;
import com.deloitte.api.utilities.SoapConstants;
import com.deloitte.api.utilities.SoapUtils;

public class TrialWsdlApi {
	
	
	// This is individual Script file for each method.Below parameter values should be set up when creating.

	
	//https://svn.apache.org/repos/asf/airavata/sandbox/xbaya-web/test/Calculator.wsdl
	
	
	String requestExcelSheet = "Request Operation 1";
	String responseExcelSheet = "Response Operation 2";
	String requestXmlName="GetGoldPriceSoap12_GetCurrentGoldPrice";
	
	@BeforeClass
	public void beforeClass() {

	
	}
	
	@Test
	/*public void demo(String wsdl,String soapEndpoint, String testCaseName)  {
		
		String testCaseName="";
		String component = "";
		String tagName ="";
		String parentTagName ="";
		String tagValue ="";
		try {
		List<List<String>> myList = ExcelRead.readExcel(SoapConstants.TESTDATA_EXCEL_PATH, SoapConstants.TESTDATA_SOAP_FILENAME, requestExcelSheet);
		String testId=null;

		int size = myList.size();

		Set<String> testCases = new HashSet<>();
		for (int i = 0; i < size; i++) {
			testCases.add(myList.get(i).get(0));
		}
		
		SoapUtils.createRequestXMLTemplatefromWsdl();

		for (String testCase : testCases) {  
			
			System.out.println("Value of iterator  >> " + testCase);
			
					for (int j = 0; j < size; j++) {
						
						if (myList.get(j).get(0).equals("")) {					
							myList.get(j).set(0, testId);
						} else {
							testId = myList.get(j).get(0);
						}
						
							if (myList.get(j).get(0).equals(testCase)) {
										try {
											testCaseName=testId;
											component=myList.get(j).get(1);
											parentTagName=myList.get(j).get(2);
											tagName=myList.get(j).get(3);
											tagValue=myList.get(j).get(4);
											System.out.println("Specific value from list >> " + myList.get(j).get(0));
											System.out.println("Specific value from list >> " + myList.get(j).get(1));
											System.out.println("Specific value from list >> " + myList.get(j).get(2));
											System.out.println("Specific value from list >> " + myList.get(j).get(3));
											System.out.println("Specific value from list >> " + myList.get(j).get(4));
										}
										catch(Exception e){
											System.out.println(e.getMessage());
										}
										SoapUtils.updateEleVal(requestXmlName,component, tagName, parentTagName, tagValue);

							}	
					}  			
			SoapUtils.moveFiles(requestXmlName,  testCaseName);
			SoapUtils.getSoapResponse(requestXmlName, testCaseName,SoapConstants.SOAP_ENDPOINT);
			SoapUtils.createRequestXMLTemplatefromWsdl();

		}
		
		
	}
		catch (Exception e) {
		System.out.println("HERE..."+e);
		}

	}	*/
	
	@AfterClass
	public void afterClass() {
		
	}
	
}	

