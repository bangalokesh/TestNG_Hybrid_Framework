package com.deloitte.testng_hybrid_framework;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Listeners;

import com.deloitte.testng_hybrid_framework.util.Constants;
import com.deloitte.testng_hybrid_framework.util.Listener;
import com.relevantcodes.extentreports.ExtentTest;
@Listeners(Listener.class)

public class AppKeywords extends GenericKeywords{
	
	public AppKeywords(ExtentTest test) throws IOException {
		super(test);
	}	
	private static final Logger logger = Logger.getLogger(AppKeywords.class.getName());

	public String isDeloittePage() throws IOException{
		try{
			logInfo("INFO", "Executing isDeloittePage function");
			if (isElementPresent("IsdeloitteLaptop_id")){
				getElement("IsdeloitteLaptop_id").click();
				driver.navigate().refresh();
				logInfo("INFO", "is Deloitte Page Handled");
			}
			return Constants.PASS;
		}
		catch(Exception e){
			e.printStackTrace();
			logInfo("FATAl", "isDeloittePage" + e.getMessage());
			return Constants.FATAL + "isDeloittePage" + e.getMessage();
		}
	}
	
	public String EnterTime(Hashtable<String, String> testData) throws IOException {
		boolean flag = false;
		String res = "";
		logInfo("INFO", "Entering Time");
		try{
			isDeloittePage();
			String [] hours = {testData.get("Sun"),testData.get("Mon"),testData.get("Tue"),testData.get("Wed"),testData.get("Thu"),testData.get("Fri"),testData.get("Sat")} ;
			//if(getElement("ExpandJurisdictionimg_xpath").getAttribute("alt").equals("Expand FLORIDA tax jurisdiction section"))
				getElement("ExpandJurisdiction_xpath").click();
				List<WebElement> l = getElements("WBSCode_xpath");
				for(int i=0;i<l.size();i++){
					String e = l.get(i).getAttribute("wbscode");
					if (e.equals(testData.get("Project Code"))){
						String inputid = driver.findElement(By.xpath("//*[@id='CONTENT-FL']/div[3]/div[" + (i+1) +"]/div/table/tbody/tr/td[3]/div")).getAttribute("id");
						int n = 0;
						int m = 3;
						while(n<7){
							String inputxpathid1 = "//*[@id='" + inputid + "']/table/tbody/tr/td[" + m + "]";
							String inputxpathid2 = "//*[@id='" + inputid + "-INPUT-" + n + "']";
							driver.findElement(By.xpath(inputxpathid1)).click();
							driver.findElement(By.xpath(inputxpathid2)).sendKeys(Keys.chord(Keys.CONTROL, "a"), hours[n]);
							n++;
							m=m+2;
						}
						flag = true;
					}
				}
				if (flag==false){
					res = addChargeCode(testData);
				}
				if(res.equals("")){
					getElement("saveButton_xpath").click();
					logInfo("PASS", "Entering Time");
					return Constants.PASS;
				}
			return res;
		}catch (Exception e){
			e.printStackTrace();
			logInfo("FATAl", "Could not Enter Time" + e.getMessage());
			return Constants.FATAL + e.getMessage();
		}
	}
	
	public String addChargeCode(Hashtable<String, String> testData) throws IOException{
		logInfo("INFO", "Adding New Charge Code");
		String returnmsg = null;
		try{
			Actions action = new Actions(driver);
			WebDriverWait wait = new WebDriverWait(driver, 10);
			String Parent_Window = driver.getWindowHandle();
			List<WebElement> searchresult =null;
			if (isElementPresent("ChargeCodeSearchIcon_xpath")){
				getElement("ChargeCodeSearchIcon_xpath").click();
			}
			driver.getWindowHandles();  
			System.out.println(driver.getWindowHandles().size());	
			driver.switchTo().frame(0);
			WebElement input = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='ChargeCodeFields1_txtChargeCode']")));
			input.click();
			input.sendKeys(testData.get("Project Code"));
			WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.id("ChargeCodeFields1_btnSearch")));
			button.click();
			if(isElementPresent("NoSearchResult_xpath")){
				if((getElement("NoSearchResult_xpath").getText()).equals(testData.get("Error-1"))){
				returnmsg = Constants.FAIL + "Project Code = " + testData.get("Project Code") + " was not found in the system.";
				reportDefect("addChargeCode", "Project Code = " + testData.get("Project Code") + " was not found in the system.");
				}
			}
			else{
				searchresult = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[@id='Form1']/table[2]/tbody/tr[2]/td/div/table")));
				for (WebElement search:searchresult){
					String y = search.findElement(By.xpath("//*[@id='ChargeCodeGrid1_SearchResultDataGrid']/tbody/tr[2]/td[1]")).getText();
					if(y.equals(testData.get("Project Code"))){
						action.moveToElement(searchresult.get(0).findElement(By.xpath("//*[@id='ChargeCodeGrid1_SearchResultDataGrid']/tbody/tr[2]/td[1]"))).doubleClick().build().perform();
						driver.switchTo().window(Parent_Window); 
						returnmsg = Constants.PASS;
						reportPass("addChargeCode");
						EnterTime(testData);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			logInfo("FATAL", "addChargeCode" + e.getMessage());
			returnmsg = Constants.FATAL + e.getMessage();	
		}
		return returnmsg;
		
	}
	
	public String filterProductBySearch(Hashtable<String, String> testData){
		try{
			getElement("SearchProduct_xpath").sendKeys(testData.get("Product"));
			getElement("searchButton_xpath").click();
			if(isElementPresent("searchResultText_xpath"))
				return Constants.PASS;
			else
				return Constants.FAIL;
		}catch(Exception e){
			e.printStackTrace();
			return Constants.FATAL;
		}
	}
}
