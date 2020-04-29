package com.organization.api.utilities;

public class SoapConstants {
	
	//public static final String SOAP_WSDL = "https://svn.apache.org/repos/asf/airavata/sandbox/xbaya-web/test/Calculator.wsdl";
	//public static final String SOAP_WSDL = "http://www.freewebservicesx.com/GetGoldPrice.asmx?WSDL"; // This should be added before creating Scripts for SOAP
	
	//public static final String SOAP_ENDPOINT = "http://156.56.179.164:9763/services/Calculator.CalculatorHttpSoap11Endpoint/";
	//public static final String SOAP_ENDPOINT ="http://www.freewebservicesx.com/GetGoldPrice.asmx"; // This should be added before creating Scripts for SOAP
	
	public static final String SOAP_PROJECT_PATH = System.getProperty("user.dir");
	
	public static final String TESTDATA_EXCEL_PATH=SOAP_PROJECT_PATH+"//src//test//resources//ApiTestResources//";
	
	public static final String SOAP_REQUEST_TEMPLATE_PATH=SOAP_PROJECT_PATH+"//src//test//java//com//deloitte//api//SoapRequestTemplates//";
	
	public static final String SOAP_REQUEST_XML_PATH=SOAP_PROJECT_PATH+"//src//test//java//com//deloitte//api//soap//Requests//";

	public static final String SOAP_RESPONSE_XML_PATH=SOAP_PROJECT_PATH+"//src//test//java//com//deloitte//api//soap//Responses//";
	
	public static final String TESTDATA_SOAP_FILENAME="soaptestdata.xlsx";
	
	public static final String TESTDATA_REST_FILENAME="resttestdata.xlsx";



	
	

}
