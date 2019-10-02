package com.deloitte.testng_hybrid_framework;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Listeners;

import com.deloitte.testng_hybrid_framework.util.Constants;
import com.deloitte.testng_hybrid_framework.util.ExtentManager;
import com.deloitte.testng_hybrid_framework.util.Listener;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
@Listeners(Listener.class)

public class AppKeywords extends GenericKeywords {
	
	public AppKeywords(ExtentTest test) throws IOException {
		super(test);
	}	
	private static final Logger logger = Logger.getLogger(AppKeywords.class.getName());
	
	
	public String filterProductBySearch(Hashtable <String, String> testData,Properties suiteProp, String objectLocator, String selectKey, String selectData) {
        try{
               int num = getElementPresent("SearchProduct_xpath"); 
               getElement(num).sendKeys(testData.get("Product"));               
               getElement("searchButton_xpath").click();
               if(isElementPresent("searchResultText_xpath", suiteProp))
                      return Constants.PASS;
               else
                      return Constants.FAIL;
        }catch(Exception e){
               e.printStackTrace();
               return Constants.FATAL;
        }
  }

	
	public void createlead(Hashtable <String, String> testData,Properties suiteProp, String objectLocator, String selectKey, String selectData) {
		getElement("SearchProduct_xpath", suiteProp).getText();
	}
	
	public void fillForm(Hashtable <String, String> testData, Properties suiteProp, String objectLocator, String selectKey, String selectData) throws IOException, SQLException {
		try {
			navigate(suiteProp, "dlurl");
			for(int i=1; i<5; i++) {
				String quesPath = suiteProp.getProperty("quesPart1_xpath")+"[" + i +"]" + suiteProp.getProperty("quesPart2_xpath");
				List <WebElement> quesEl = driver.findElements(By.xpath(quesPath));
				outerloop:
				for(WebElement qe:quesEl) {
					for(int j = 1; j<25; j++) {
						String question = "Question" + j;
						String answer = "Answer" + j;
						if(qe.getText().contains(testData.get(question))) {
							String ansPath = suiteProp.getProperty("quesPart1_xpath")+"[" + i +"]" + suiteProp.getProperty("ansPart1_xpath");
							List <WebElement> ansEl = driver.findElements(By.xpath(ansPath));
							for(WebElement aE:ansEl) {
								if(aE.getText().equalsIgnoreCase(testData.get(answer))) {
									aE.click();
									break outerloop;
								}
							}
						}
					}
				}
			}
			takeScreenshot("DLTest");
			getElement("continue_id", suiteProp).click();
			key.updateDbTestResults(Constants.PASS, "DLTest");
		} catch (IOException e) {
			key.updateDbTestResults(Constants.FATAL, "DLTest");
			e.printStackTrace();
		} catch (SQLException e) {
			key.updateDbTestResults(Constants.FATAL, "DLTest");
			e.printStackTrace();
		}
	}
}
