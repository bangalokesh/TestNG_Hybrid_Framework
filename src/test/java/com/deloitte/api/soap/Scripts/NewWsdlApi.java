package com.deloitte.api.soap.Scripts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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

import com.deloitte.api.soap.Scripts.*;
import com.deloitte.api.utilities.ExcelRead;
import com.deloitte.api.utilities.SoapConstants;
import com.deloitte.api.utilities.SoapUtils;

public class NewWsdlApi {
	
	
	// This is individual Script file for each method.Below parameter values should be set up when creating.

	
	//https://svn.apache.org/repos/asf/airavata/sandbox/xbaya-web/test/Calculator.wsdl
	

	
	/*String requestExcelSheet = "Add Request";
	String responseExcelSheet = "Add Response";*/
	String requestXmlName="GetGoldPriceSoap_GetCurrentGoldPrice";
	String responseXmlName="GetGoldPriceSoap_GetCurrentGoldPrice";
	String wsdl="http://www.freewebservicesx.com/GetGoldPrice.asmx?WSDL";
	String soapEndPoint="http://www.freewebservicesx.com/GetGoldPrice.asmx";
	String requestTemplateFilePath=SoapConstants.SOAP_REQUEST_TEMPLATE_PATH+requestXmlName+".xml";
	String responseFilePath=SoapConstants.SOAP_RESPONSE_XML_PATH+requestXmlName+"_"+"?!$&"+".xml";
	
	@BeforeClass
	public void beforeClass() {
		HashMap<String, List<TestCaseProp>> hashMapObjects=new HashMap<String, List<TestCaseProp>>();
		initialiseHashMap(hashMapObjects);
		for(int i=1;i<=4;i++){
		    String testCase = "Test"+i;
			//SoapUtils.executeSoapRequest( requestXmlName,wsdl,soapEndPoint,testCase,hashMapObjects,requestTemplateFilePath);
		}

	}
	//Method to initialize test data
	private void initialiseHashMap(HashMap<String, List<TestCaseProp>> hashMapObjects) {
		List<TestCaseProp> test1=new ArrayList<TestCaseProp>();
		List<TestCaseProp> test2=new ArrayList<TestCaseProp>();
		List<TestCaseProp> test3=new ArrayList<TestCaseProp>();
		List<TestCaseProp> test4=new ArrayList<TestCaseProp>();
		String TestCase1="Test1";
		String TestCase2="Test2";
		String TestCase3="Test3";
		String TestCase4="Test4";
		TestCaseProp obj11=new TestCaseProp();
		TestCaseProp obj12=new TestCaseProp();
		TestCaseProp obj21=new TestCaseProp();
		TestCaseProp obj22=new TestCaseProp();
		TestCaseProp obj31=new TestCaseProp();
		TestCaseProp obj32=new TestCaseProp();
		TestCaseProp obj33=new TestCaseProp();
		TestCaseProp obj34=new TestCaseProp();
		TestCaseProp obj41=new TestCaseProp();
		TestCaseProp obj42=new TestCaseProp();
		TestCaseProp obj43=new TestCaseProp();
		TestCaseProp obj44=new TestCaseProp();
		
		obj11.setComponent("s11:Body");
		obj11.setParentTagName("ns:add");
		obj11.setTagName("ns:n1");
		obj11.setTagValue("1");
		test1.add(obj11);
		
		obj12.setComponent("s11:Body");
		obj12.setParentTagName("ns:add");
		obj12.setTagName("ns:n2");
		obj12.setTagValue("1");
		test1.add(obj12);
		
		hashMapObjects.put(TestCase1, test1);
		//listOfObj.clear();
		
		obj21.setComponent("s11:Body");
		obj21.setParentTagName("ns:add");
		obj21.setTagName("ns:n1");
		obj21.setTagValue("2");
		test2.add(obj21);
		
		obj22.setComponent("s11:Body");
		obj22.setParentTagName("ns:add");
		obj22.setTagName("ns:n2");
		obj22.setTagValue("3");
		test2.add(obj22);
		
		hashMapObjects.put(TestCase2, test2);
		//listOfObj.clear();
		
		obj31.setComponent("s11:Body");
		obj31.setParentTagName("ns:add");
		obj31.setTagName("ns:n1");
		obj31.setTagValue("");
		test3.add(obj31);
		
		obj32.setComponent("s11:Body");
		obj32.setParentTagName("ns:add");
		obj32.setTagName("ns:n2");
		obj32.setTagValue("4");
		test3.add(obj32);
		
		obj33.setComponent("s11:Body");
		obj33.setParentTagName("ns:add");
		obj33.setTagName("ns:n1");
		obj33.setTagValue("5");
		test3.add(obj33);
		
		obj34.setComponent("s11:Body");
		obj34.setParentTagName("ns:add");
		obj34.setTagName("ns:n2");
		obj34.setTagValue("");
		test3.add(obj34);
		
		hashMapObjects.put(TestCase3, test3);
		
		obj41.setComponent("s11:Body");
		obj41.setParentTagName("ns:add");
		obj41.setTagName("ns:n1");
		obj41.setTagValue("");
		test4.add(obj41);
		
		obj42.setComponent("s11:Body");
		obj42.setParentTagName("ns:add");
		obj42.setTagName("ns:n2");
		obj42.setTagValue("4");
		test4.add(obj42);
		
		obj43.setComponent("s11:Body");
		obj43.setParentTagName("ns:add");
		obj43.setTagName("ns:n3");
		obj43.setTagValue("5");
		test4.add(obj43);
		
		obj44.setComponent("s11:Body");
		obj44.setParentTagName("ns:add");
		obj44.setTagName("ns:n4");
		obj44.setTagValue("");
		test4.add(obj44);
		
		hashMapObjects.put(TestCase4, test4);
	}

	@Test
	public void demo()  {
		HashMap<String, List<TestCaseProp>> hashMapObjects=new HashMap<String, List<TestCaseProp>>();
		initialiseHashMap(hashMapObjects);
		for(int i=1;i<=4;i++){
		    String testCase = "Test"+i;
		    //SoapUtils.validateSoapResponse(responseFilePath, testCase, hashMapObjects);
		}
		
		
	}
	
	@AfterClass
	public void afterClass() {
		
	}
	
}	

