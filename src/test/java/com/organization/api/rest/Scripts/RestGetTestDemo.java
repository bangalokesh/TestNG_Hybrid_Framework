package com.organization.api.rest.Scripts;

import java.io.IOException;
import java.net.URISyntaxException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.organization.api.utilities.RestUtils;

public class RestGetTestDemo extends RestUtils{
	
	// This is individual Script file for each method.Below parameter values should be set up when creating.
	
	String uri="http://httpbin.org/post";
	String requestSheetName ="Request Operation 1";
	String responseSheetName = "Response Operation 2";

	@BeforeClass
	void beforeClass()
	{
		try {
			RestUtils.setPostRequestParameters(uri,requestSheetName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	
	void testJsonUtilMethods() throws IOException {
		
	
		RestUtils.verifyRestResonseValues(responseSheetName);

		
	}
	
	
	@AfterClass
	void afterClass() {
		
	}
}
