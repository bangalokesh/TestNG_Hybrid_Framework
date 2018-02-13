package com.deloitte.testng_hybrid_framework.captureObjects;

public class CaptureObjects {
	
	public static void getPageObjects(String url, RepositoryGenerator sitecom) {
		// TODO Auto-generated method stub
		sitecom = new RepositoryGenerator(url, sitecom+".repository");
		sitecom.buildNewRepository();
//		RepositoryGenerator ebayHelpGen = new RepositoryGenerator("http://ocsnext.ebay.com/ocs/home?", "help.repository");
//		ebayHelpGen.buildNewRepository();
	}
}
