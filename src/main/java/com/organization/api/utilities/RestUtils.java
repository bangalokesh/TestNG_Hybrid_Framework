package com.organization.api.utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

public class RestUtils extends RestConstants{
	
	private final static String USER_AGENT = "Mozilla/5.0";
	static HttpResponse response ;
	static SoftAssert softAssertion = new SoftAssert();

	/*print console and testng reporter logs*/

	
    public static void out(String str) {
        System.out.println(str);
        Reporter.log("<p>"+str+"<p>");
    }

		
    // HTTP GET request 	/*call GET method without parameters*/

		private void sendGet(String scheme, String host, String path) throws Exception {
			URIBuilder builder = new URIBuilder();
			builder.setScheme("http").setHost("httpbin.org").setPath("/ip");
			java.net.URI uri = builder.build();
			HttpGet httpget1 = new HttpGet(uri);
			httpget1.setHeader("Accept","application/json");
			httpget1.setHeader("Content-Type","application/json");
			httpget1.setHeader("charset","utf-8");
		
			out(httpget1.getURI().toString());
			HttpClient client = new DefaultHttpClient();
			HttpResponse resp = client.execute(httpget1);
			resp.addHeader("Content-Type","application/json");
		
			Reader in = new BufferedReader(new InputStreamReader(resp.getEntity().getContent(), "UTF-8"));
			StringBuilder sb = new StringBuilder();
			for (int c; (c = in.read()) >= 0;)
				sb.append((char)c);
			String response = sb.toString();
			out(response);
		}

	    // HTTP GET request 	/*call POST method with parameters*/


	public static void setPostRequestParameters(String uri, String requestExcelSheetName) throws IOException, URISyntaxException {
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		List<List<String>> myList = ExcelRead.readExcel(RestConstants.TESTDATA_EXCEL_PATH, RestConstants.TESTDATA_REST_FILENAME, requestExcelSheetName);
		
		int size = myList.size();

		Set<String> testCases = new HashSet<>();
		for (int i = 0; i < size; i++) {
			testCases.add(myList.get(i).get(0));
		}

			for (String testCase : testCases) { 	
				// For all test cases associated values
				//String testCase = "Test2";   // If want to get specific test case associated value
					out("Value of iterator Test case >> " + testCase);
					for (int j = 0; j < size; j++) {
						if (myList.get(j).get(0).equals(testCase)) {
						////	for(int k =0; k < myList.get(j).size(); k++) {
							try {
								out("Specific value from Test Case >> " + myList.get(j).get(0));
								out("Specific value from Compnent >> " + myList.get(j).get(1));
								out("Specific value from Parent Tag >> " + myList.get(j).get(2));
								out("Specific value from Tag >> " + myList.get(j).get(3));
								out("Specific value from Value >> " + myList.get(j).get(4));
								
								urlParameters.add(new BasicNameValuePair(myList.get(j).get(3), myList.get(j).get(4)));
							}
							catch(Exception e){
								out(e.getMessage());
							}
						}
					}
					System.out.println(urlParameters.size());
					
					   //Build the server URI together with the parameters you wish to pass
				    URIBuilder uriBuilder = new URIBuilder(uri);
				    uriBuilder.addParameters(urlParameters);

				    HttpPost post = new HttpPost(uriBuilder.build());
				    post.setHeader("Content-Type", "application/json");
				    post.setHeader("User-Agent", USER_AGENT);
					post.setHeader("Accept","application/json");
					//post.setHeader("Content-Type","application/json");
					post.setHeader("charset","utf-8");
					
				    HttpClient client = new DefaultHttpClient();
				    
					 response = client.execute(post);
					response.addHeader("Content-Type","application/json");
				
					out("\nSending 'POST' request to URL : " + uriBuilder);
					out("Post parameters : " + post.getEntity());
					out("Response Code : " + response.getStatusLine().getStatusCode());
					
					Reader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
				    StringBuilder sb = new StringBuilder();
				    for (int c; (c = in.read()) >= 0;)
				        sb.append((char)c);
				    String responseStr = sb.toString();
				    out(responseStr);
					try (FileWriter file = new FileWriter(RestConstants.REST_RESPONSE_JSON_PATH+testCase+".txt")){
						file.write(responseStr);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					out("Successfully Copied Response JSON Object to File...");
					urlParameters.clear();
				}
	}
	
    // HTTP GET request 	/*call GET method with parameters*/

	
	public static void setGetRequestParameters(String uri) throws IOException, URISyntaxException {
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		List<List<String>> myList = ExcelRead.readExcel(RestConstants.TESTDATA_EXCEL_PATH, RestConstants.TESTDATA_REST_FILENAME, "Request Operation 1");
		
		int size = myList.size();

		Set<String> testCases = new HashSet<>();
		for (int i = 0; i < size; i++) {
			testCases.add(myList.get(i).get(0));
		}

			for (String testCase : testCases) { 	
				// For all test cases associated values
				//String testCase = "Test2";   // If want to get specific test case associated value
					out("Value of iterator Test case >> " + testCase);
					for (int j = 0; j < size; j++) {
						if (myList.get(j).get(0).equals(testCase)) {
						////	for(int k =0; k < myList.get(j).size(); k++) {
							try {
								out("Specific value from Test Case >> " + myList.get(j).get(0));
								out("Specific value from Compnent >> " + myList.get(j).get(1));
								out("Specific value from Parent Tag >> " + myList.get(j).get(2));
								out("Specific value from Tag >> " + myList.get(j).get(3));
								out("Specific value from Value >> " + myList.get(j).get(4));
								
								urlParameters.add(new BasicNameValuePair(myList.get(j).get(3), myList.get(j).get(4)));
							}
							catch(Exception e){
								out(e.getMessage());
							}
						}
					}
					System.out.println(urlParameters.size());
					
					//Build the server URI together with the parameters you wish to pass
				    URIBuilder uriBuilder = new URIBuilder("uri");
				    uriBuilder.addParameters(urlParameters);

				    HttpGet get = new HttpGet(uriBuilder.build());
				    get.setHeader("Content-Type", "application/json");
				    get.setHeader("User-Agent", USER_AGENT);
				    get.setHeader("Accept","application/json");
				    get.setHeader("Content-Type","application/json");
				    get.setHeader("charset","utf-8");
					
				    HttpClient client = new DefaultHttpClient();
				    
					response = client.execute(get);
					response.addHeader("Content-Type","application/json");
				
					out("\nSending 'GEt' request to URL : " + uriBuilder);
					out("Post parameters : " + get);
					out("Response Code : " + response.getStatusLine().getStatusCode());

					Reader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
				    StringBuilder sb = new StringBuilder();
				    for (int c; (c = in.read()) >= 0;)
				        sb.append((char)c);
				    String responseStr = sb.toString();
				    out(responseStr);
					try (FileWriter file = new FileWriter(RestConstants.REST_RESPONSE_JSON_PATH+testCase+".txt")){
						file.write(responseStr);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					out("Successfully Copied Response JSON Object to File...");
					urlParameters.clear();
				}
	}
	
	/*Verify is the Json Element Value in a JSON Object ,is present in Response*/
	
	public static void verifyJsonObjectElement(String testcase , String jsonObjectParent, String jsonObjectKeyName 
			, String jsonObjectKeyValue )throws ParseException, IOException {
		
		out("Verifying Object ..verifyJsonObjectElement");

		
		BufferedReader reader = new BufferedReader(new FileReader(RestConstants.REST_RESPONSE_JSON_PATH+testcase+".txt"));
		String json = "";
		try {
		    StringBuilder sb = new StringBuilder();
		    String line = reader.readLine();

		    while (line != null) {
		        sb.append(line);
		        sb.append("\n");
		        line = reader.readLine();
		    }
		    json = sb.toString();
		} finally {
		    reader.close();
		}
		JSONObject o = new JSONObject(json); // this will get you the entire JSON node

		try{
			out(o.getJSONObject(jsonObjectParent).getString(jsonObjectKeyName));
			softAssertion.assertEquals(o.getJSONObject(jsonObjectParent).getString(jsonObjectKeyName),jsonObjectKeyValue);
			 
		}
		catch(Exception e) {
			out("NOT FOUND"+ jsonObjectKeyName +":"+ jsonObjectKeyValue);
		}
	}

	/*Verify is the Json Object Value is present in Response*/

	
	public static void verifyJsonObject(String testcase , String jsonObjectKeyName 
			, String jsonObjectKeyValue )throws ParseException, IOException {
		
		out("Verifying verifyJsonObject ..");
		SoftAssert softAssertion = new SoftAssert();

		
		BufferedReader reader = new BufferedReader(new FileReader(RestConstants.REST_RESPONSE_JSON_PATH+testcase+".txt"));
		String json = "";
		try {
		    StringBuilder sb = new StringBuilder();
		    String line = reader.readLine();

		    while (line != null) {
		        sb.append(line);
		        sb.append("\n");
		        line = reader.readLine();
		    }
		    json = sb.toString();
		} finally {
		    reader.close();
		}
		JSONObject o = new JSONObject(json); // this will get you the entire JSON node

		try{
			out(o.getJSONObject(jsonObjectKeyName).toString());
			softAssertion.assertEquals(o.getJSONObject(jsonObjectKeyName).toString().contains(jsonObjectKeyValue),true);
			 
		}
		catch(Exception e) {
			
			out("NOT FOUND"+ jsonObjectKeyName +":"+ jsonObjectKeyValue);
		}
	}

	/*Verify is the Json Element Value is present in Response*/

	
	public static void verifyJsonElement(String testcase , String jsonObjectKeyName 
			, String jsonObjectKeyValue )throws ParseException, IOException {
		
		out("Verifying verifyJsonElement ..");
		
		SoftAssert softAssertion = new SoftAssert();

		BufferedReader reader = new BufferedReader(new FileReader(RestConstants.REST_RESPONSE_JSON_PATH+testcase+".txt"));
		String json = "";
		try {
		    StringBuilder sb = new StringBuilder();
		    String line = reader.readLine();

		    while (line != null) {
		        sb.append(line);
		        sb.append("\n");
		        line = reader.readLine();
		    }
		    json = sb.toString();
		} finally {
		    reader.close();
		}
		JSONObject o = new JSONObject(json); // this will get you the entire JSON node

		try{
		
			out(o.get(jsonObjectKeyName).toString()+"_"+jsonObjectKeyValue);
			softAssertion.assertEquals(o.get(jsonObjectKeyName).toString().contains(jsonObjectKeyValue),true);
			 
		}
		catch(Exception e) {
			
			out("NOT FOUND"+ jsonObjectKeyName +":"+ jsonObjectKeyValue);

		}
	}

	/*Verify is the Json Object Value is present in Json Array , in the Response*/

	public static void verifyJsonArrayObject(String testcase , String jsonObjectParent, String jsonObjectKeyName 
			, String jsonObjectKeyValue )throws ParseException, IOException {
		out("Verifying verifyJsonArrayObject ..");
		
		SoftAssert softAssertion = new SoftAssert();

		
		BufferedReader reader = new BufferedReader(new FileReader(RestConstants.REST_RESPONSE_JSON_PATH+testcase+".txt"));
		String json = "";
		try {
		    StringBuilder sb = new StringBuilder();
		    String line = reader.readLine();

		    while (line != null) {
		        sb.append(line);
		        sb.append("\n");
		        line = reader.readLine();
		    }
		    json = sb.toString();
		} finally {
		    reader.close();
		}
		JSONObject o = new JSONObject(json); // this will get you the entire JSON node
		String jsonObjectInsideArray;
		try{
			JSONArray arr = o.getJSONArray(jsonObjectParent);
			for (int i = 0; i < arr.length(); i++)
			{
			    jsonObjectInsideArray = arr.getJSONObject(i).getString(jsonObjectKeyName);
				out(jsonObjectInsideArray);
				softAssertion.assertEquals(jsonObjectInsideArray.contains(jsonObjectKeyValue),true);
				 
			}
		}
		catch(Exception e) {
			
			out("NOT FOUND .. "+ jsonObjectKeyName+":"+jsonObjectKeyValue);
		}
	}

	
	/*Verify is the Json Element Value is present in a Json Array , in the Response*/

	public static void verifyJsonArrayElement(String testcase , String jsonObjectParent, String jsonObjectKeyName 
			, String jsonObjectKeyValue )throws ParseException, IOException {
		
		out("Verifying verifyJsonArrayElement ..");
		
		SoftAssert softAssertion = new SoftAssert();

		BufferedReader reader = new BufferedReader(new FileReader(RestConstants.REST_RESPONSE_JSON_PATH+testcase+".txt"));
		String json = "";
		try {
		    StringBuilder sb = new StringBuilder();
		    String line = reader.readLine();

		    while (line != null) {
		        sb.append(line);
		        sb.append("\n");
		        line = reader.readLine();
		    }
		    json = sb.toString();
		} finally {
		    reader.close();
		}
		JSONObject o = new JSONObject(json); // this will get you the entire JSON node
		String jsonObjectInsideArray;
		try{
			
			JSONArray arr = o.getJSONArray(jsonObjectParent);
			for (int i = 0; i < arr.length(); i++)
			{
			    jsonObjectInsideArray = arr.get(i).toString();
				out(jsonObjectInsideArray);
				softAssertion.assertEquals(jsonObjectInsideArray.contains(jsonObjectKeyValue),true);
				 
			}
			
		}
		catch(Exception e) {
			
			out("NOT FOUND .. "+ jsonObjectKeyName+":"+jsonObjectKeyValue);

		}
	}
	
	/*Verify is the Json response by reading values from Excel Response sheet*/

	
	public static  void verifyRestResonseValues(String responeExcelSheetName) throws IOException {

		List<List<String>> myList = ExcelRead_Updated.readExcel(RestConstants.TESTDATA_EXCEL_PATH, RestConstants.TESTDATA_REST_FILENAME, responeExcelSheetName);
		
		int size = myList.size();

		Set<String> testCases = new HashSet<>();
		for (int i = 0; i < size; i++) {
			testCases.add(myList.get(i).get(0));
		}		
		
		for (String testCase : testCases) {
			//String testCase = "Test2";
			for (int j = 0; j < size; j++) {		
				if (myList.get(j).get(0).equals("")) {					
					myList.get(j).set(0, ExcelRead_Updated.testId);
				} else {
					ExcelRead_Updated.testId = myList.get(j).get(0);
				}
				if (myList.get(j).get(0).equals(testCase)) {
				////	for(int k =0; k < myList.get(j).size(); k++) {
					try {
						String testCaseName =myList.get(j).get(0).toString();
						String parentCompName =myList.get(j).get(1).toString();
								String parentNodeName =myList.get(j).get(2).toString();
								String nodeName =myList.get(j).get(3).toString();
								String nodeCompName =myList.get(j).get(4).toString();
								String nodeValue =myList.get(j).get(5).toString();
						System.out.println("Specific value from Test Case >> " + myList.get(j).get(0).toString());
						System.out.println("Specific value from Parent Node Comp >> " + myList.get(j).get(1).toString());
						System.out.println("Specific value from Parent Node >> " + myList.get(j).get(2).toString());
						System.out.println("Specific value from Node >> " + myList.get(j).get(3).toString());
						System.out.println("Specific value from Node Comp >> " + myList.get(j).get(4).toString());
						System.out.println("Specific value from Node Value >> " + myList.get(j).get(5).toString());
					// jsonarray jsonobject jsonkey	
						
				if(!parentNodeName.isEmpty()) {
						
						if(parentCompName.equalsIgnoreCase("JsonArray")
								&& nodeCompName.equalsIgnoreCase("JsonObject")) {
							
							RestUtils.verifyJsonArrayObject(testCaseName, parentNodeName,nodeName,nodeValue);
						}
						
						else if(parentCompName.equalsIgnoreCase("JsonArray")
								&& nodeCompName.equalsIgnoreCase("JsonElement")) {
							
							RestUtils.verifyJsonArrayElement(testCaseName, parentNodeName,nodeName,nodeValue);
						}
						else if(parentCompName.equalsIgnoreCase("JsonObject")
								&& nodeCompName.equalsIgnoreCase("JsonElement")){
							
							RestUtils.verifyJsonObjectElement(testCaseName, parentNodeName,nodeName,nodeValue);
						}
				}
				else {
						if(nodeCompName.equalsIgnoreCase("JsonObject")) {
							RestUtils.verifyJsonObject(testCaseName, nodeName,nodeValue);
						}
						else {
							System.out.println("Direct element ..");
							RestUtils.verifyJsonElement(testCaseName, nodeName,nodeValue);
						}
				}
					}
					catch(Exception e){
						System.out.println(e.getMessage());
					}
					softAssertion.assertAll(); // Must be added to all Soft Asserts in Above methods - must make it boolean and assert all boolean values return here// 
				}
			}
		}

	}
	
}	
	
	

