package com.organization.testng_hybrid_framework.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.organization.testng_hybrid_framework.util.RetrunVal;

public class dbConnect2 {
	// TODO Auto-generated method stub

	private static Connection con;

	static {
		String host = "localhost";
		String port = "3306";
		String databasename = "automation_framework";
		String userid = "root";
		String password = "root";

		try {
			String db = "jdbc:mysql://" + host + ":" + port + "/" + databasename+ "?autoReconnect=true&useSSL=false"; 
			con = DriverManager.getConnection(db, userid, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public RetrunVal sqlselect(String selQuery) throws SQLException {

		Statement s = con.createStatement();
		ResultSet res = s.executeQuery(selQuery);
		RetrunVal rs = null;
		List<RetrunVal> rsList = new ArrayList<RetrunVal>();

		while (res.next()) {
			rs = new RetrunVal();
			//rs.setId(res.getString("test_id"));
			rs.setSuite(res.getString("test_suite"));
			rs.setCases(res.getString("test_cases"));
			rs.setKeyword(res.getString("test_keyword"));
			rs.setStatus(res.getString("test_status"));
			rs.setRunkey(res.getString("test_runkey"));
			rs.setRunkey(res.getString("created_by"));
			rs.setRunkey(res.getString("created_at"));
			rs.setRunkey(res.getString("modified_by"));
			rs.setRunkey(res.getString("modified_at"));
			//System.out.println("testid -->" + rs.getId());
			System.out.println("test_suite-->" + rs.getSuite());
			System.out.println("test_cases-->" + rs.getCases());
			System.out.println("test_keyword-->" + rs.getKeyword());
			System.out.println("test_status-->" + rs.getStatus());
			System.out.println("test_runkey-->" + rs.getRunkey());
			System.out.println("created_by-->" + rs.getCreatedby());
			System.out.println("created_at-->" + rs.getCreatedat());
			System.out.println("modified_by-->" + rs.getModifiedby());
			System.out.println("modified_at-->" + rs.getModifiedat());
			rsList.add(rs);

		}
		System.out.println(rsList.size());

		for (RetrunVal rsLi : rsList) {

			System.out.println(rsLi.getCases());
			System.out.println(rsLi.getKeyword());
		}
		return rs;
	}
	
	public  String sqlcount(String selQuery) throws SQLException {

		Statement s = con.createStatement();
		ResultSet res = s.executeQuery(selQuery); 
		String val = null;
		   while(res.next())
		   {
		    val = res.getString("test_count");
		    System.out.println(res.getString("test_count"));
		   }
		return val;
	}

	public static void sqlinsert (String selInsert) throws SQLException {
		Statement s = con.createStatement();
		s.executeUpdate(selInsert);
	}
	
	public static void main(String[] args){
		Statement stmt = null;
		Statement stmt1 = null;
		String testsuiteq = null;
		String testrunq = null; 
		int plannedCount = 0;
		int passCount =0;
		int failCount =0;
		int skipCount = 0;
		int exceptionCount = 0;
		String testrun = null;
		String testrun1 = null;
        String testrunid1 = null;
        boolean flag = true;
		
		try {
			List <String> runname1 = new ArrayList<String>();
			testrunq = "select test_runid from test_exec_run_summary order by modified_at asc;"; 
			stmt = con.createStatement();
		    ResultSet testrunrs = stmt.executeQuery(testrunq);
		    while (testrunrs.next()) {
		    	testrun = testrunrs.getString("test_runid");
		    	runname1.add(testrun);
		    }
		    stmt.close();
		    List <String> runname2 = new ArrayList<String>();
		    testsuiteq = "select distinct test_runid from test_run_summary order by modified_at asc;";
		    stmt1 = con.createStatement();
		    ResultSet testSuiteRS1 = stmt1.executeQuery(testsuiteq);
		    while (testSuiteRS1.next()) {
		    	testrunid1 = testSuiteRS1.getString("test_runid");
		    	runname2.add(testrunid1);	
		    }
		    stmt1.close();
		    List <String> runname3 = new ArrayList<String>();
		    
		    for(int i =0; i<runname2.size(); i++){
		    	flag = true;
		    	for(int x=0; x<runname1.size(); x++){
		    		if(runname2.get(i).equals(runname1.get(x))){
		    			flag=false;
		    			break;
		    		}
		    	}
		    	if(flag==true)
		    		runname3.add(runname2.get(i));
		    }
		    
		    int i =0;
		    while (runname3.iterator().hasNext() && i<runname3.size()){
		        		testrun1 = runname3.get(i);
	        			String query = "select test_runid, sum(test_planned) AS Planned, sum(test_passed) AS Passed, sum(test_failed) AS Failed, sum(test_exception) AS Exception, sum(test_skipped) AS Skipped, created_by, created_at, modified_by, modified_at from test_run_summary where test_runid = '" + testrun1 + "' order by modified_at desc;";
	        			stmt1 = con.createStatement();
	        			ResultSet testrunstmt = stmt1.executeQuery(query);
	        			while(testrunstmt.next()){
	        				plannedCount = testrunstmt.getInt("Planned");
	        				passCount = testrunstmt.getInt("Passed");
	        				failCount = testrunstmt.getInt("Failed");
	        				exceptionCount = testrunstmt.getInt("Exception");
	        				skipCount = testrunstmt.getInt("Skipped");
	        				String createdby = testrunstmt.getString("created_by");
	        				String createdat = testrunstmt.getString("created_at");
	        				String modifiedby = testrunstmt.getString("modified_by");
	        				String modifiedat = testrunstmt.getString("modified_at");
	        				String insmodel = "INSERT INTO test_exec_run_summary (test_runid, test_planned, test_passed, test_failed, test_skipped, test_exception, created_by, created_at, modified_by, modified_at)\r\n" + 
	        					"VALUES (";
	        				String comma = ",";
	        				String insmodel2 = ");";
	        				String selInsert = insmodel+ "'" + testrun1 + "'" + comma + plannedCount + comma 
	        				+ passCount + comma + failCount + comma + skipCount + comma + exceptionCount + comma + "'" 
	        				+ createdby + "'" + comma + "'" + createdat + "'" + comma + "'" + modifiedby + "'" 
	        				+ comma + "'" + modifiedat + "'"+ insmodel2;
	        				sqlinsert(selInsert);
	        				i++;
	        			}
		        	}
		        stmt1.close();
		}catch(Exception e){
			e.printStackTrace();
			e.getMessage();
		}
	}
	
	public static Connection getCon() {
			String host = "localhost";
			String port = "3306";
			String databasename = "automation_framework";
			String userid = "root";
			String password = "root";
			try {
				String db = "jdbc:mysql://" + host + ":" + port + "/" + databasename+ "?autoReconnect=true&useSSL=false"; 
				con = DriverManager.getConnection(db, userid, password);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return con;
	}
}	
