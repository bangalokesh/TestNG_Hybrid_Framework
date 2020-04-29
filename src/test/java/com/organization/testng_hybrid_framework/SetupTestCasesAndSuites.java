package com.organization.testng_hybrid_framework;

public class SetupTestCasesAndSuites extends AllTest{

	public static void main(String[] args) throws InterruptedException {
		setSuiteRunMode("exampletestsuite_amazonsuite", "Y");
		setTestRunModes("Amazon_FilterProduct_Test", "exampletestsuite_amazonsuite", "Y");
		setTestRunModes("Calc_Test", "exampletestsuite_amazonsuite", "Y");
		setTestRunModes("DisneyBookAResortTest", "exampletestsuite_disneysuite", "Y");
		setSuiteRunMode("exampletestsuite_disneysuite", "Y");
		setSuiteRunMode("exampletestsuite_dlsuite", "Y");
		setTestRunModes("DLTest", "exampletestsuite_dlsuite", "Y");
	}
}
