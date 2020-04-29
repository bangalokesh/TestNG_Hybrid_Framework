package com.organization.testng_hybrid_framework;

import static io.appium.java_client.touch.LongPressOptions.longPressOptions;


import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static java.time.Duration.ofSeconds;
import static org.testng.AssertJUnit.assertTrue;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.ini4j.Ini;
import org.ini4j.Wini;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Listeners;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.organization.testng_hybrid_framework.AXE.AXE;
import com.organization.testng_hybrid_framework.captureObjects.CaptureObjects;
import com.organization.testng_hybrid_framework.captureObjects.RepositoryGenerator;
import com.organization.testng_hybrid_framework.util.Constants;
import com.organization.testng_hybrid_framework.util.CurrentDateAndMonth;
import com.organization.testng_hybrid_framework.util.ExtentManager;
import com.organization.testng_hybrid_framework.util.Listener;
import com.organization.testng_hybrid_framework.util.dbConnect2;
import com.rationaleemotions.GridApiAssistant;
import com.rationaleemotions.pojos.Host;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileBrowserType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.offset.PointOption;
@SuppressWarnings("deprecation")
@Listeners(Listener.class)

public class GenericKeywords extends Common{
	public static WebDriver driver;
	private static final URL scriptUrl = GenericKeywords.class.getClassLoader().getResource("axe.min.js");
	public static io.appium.java_client.android.AndroidDriver<MobileElement> aDriver;
	public Properties prop;
	public String browserType;
	public String PlatformName;
	public String ip;
	public String hubPort = "4444";
	public DesiredCapabilities cap = null;
	public static ExtentTest test;
	private int XPATH_LOCATOR_LIMIT=16;
    private int XPATH_CSS_NUM=0;
    boolean elementPresent=false;

	public static ExtentTest getTest() {
		return test;
	}

	public static void setTest(ExtentTest test) {
		GenericKeywords.test = test;
	}

	public static Boolean failflag = false;
	public static Boolean fatalflag = false;
	private static final Logger logger = Logger.getLogger(GenericKeywords.class.getName());
	
	public Keywords key = new Keywords(test);
	
	public GenericKeywords(ExtentTest test) {
		this.test=test;
		prop = new Properties();
		try{
			FileInputStream fs;
			fs = new FileInputStream(System.getProperty("user.dir")+"//src//test//resources//project.properties");
			prop.load(fs);
			logger.info("Test = " + test + " Loading project properties file");		
		}
		catch (IOException e){
			e.printStackTrace();
			logInfo("FATAL", "Test = " + test, " Could not load the project properties file");
		}
	}
	
//********************************GENERIC FUNCTIONS*****************************************************	
//**********************Open Browser Method*************************************************************
	public void openBrowser(Hashtable<String, String> data, Properties suiteProp, String ObjectLocator, String selectKey, String selectData ) throws IOException, MalformedURLException, SQLException{
		try{
			if(prop.getProperty("grid").equals("Y")){
				logger.info("Test = " + test + " Opening " + browserType + " Remote Browser");
				cap = new DesiredCapabilities();
				if(data.get("Browser").toUpperCase().equals("MOZILLA")){
					if(data.get("Platform").toUpperCase().equals("WINDOWS")){
						cap = DesiredCapabilities.firefox();
						cap.setBrowserName("firefox");
						cap.setJavascriptEnabled(true);
						cap.setPlatform(org.openqa.selenium.Platform.WINDOWS);
					} else if(data.get("Platform").toUpperCase().equals("LINUX")){
						cap = DesiredCapabilities.firefox();
						cap.setBrowserName("firefox");
						cap.setJavascriptEnabled(true);
						cap.setPlatform(org.openqa.selenium.Platform.LINUX);
					} else if (data.get("Platform").toUpperCase().equals("MAC")){
						cap = DesiredCapabilities.firefox();
						cap.setBrowserName("firefox");
						cap.setJavascriptEnabled(true);
						cap.setPlatform(org.openqa.selenium.Platform.MAC);	
					}
				}
				else if (data.get("Browser").toUpperCase().equals("CHROME")){
					if(data.get("Platform").toUpperCase().equals("WINDOWS")){
					cap = DesiredCapabilities.chrome();
					cap.setBrowserName("chrome");
					cap.setPlatform(org.openqa.selenium.Platform.WINDOWS);
					}else if(data.get("Platform").toUpperCase().equals("LINUX")){
					cap = DesiredCapabilities.chrome();
					cap.setBrowserName("chrome");
					cap.setPlatform(org.openqa.selenium.Platform.LINUX);
					}else if(data.get("Platform").toUpperCase().equals("MAC")){
					cap = DesiredCapabilities.chrome();
					cap.setBrowserName("chrome");
					cap.setPlatform(org.openqa.selenium.Platform.MAC);
					}
				}else if(data.get("Browser").toUpperCase().equals("IE")){
					System.setProperty("webdriver.ie.driver",
							System.getProperty("user.dir") + "//drivers//IEDriverServer.exe");
					cap = DesiredCapabilities.internetExplorer();
					// Added by Siva
					cap.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
					cap.setCapability(InternetExplorerDriver.ENABLE_ELEMENT_CACHE_CLEANUP, true);
					cap.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
					cap.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
					cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
					cap.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
					cap.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
					cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
					cap.setCapability("ignoreProtectedModeSettings", true); 
					// Ended by Siva
					driver = new InternetExplorerDriver(cap);
					
				}else if(data.get("Browser").toUpperCase().equals("EDGE")){
					cap = DesiredCapabilities.edge();
					cap.setBrowserName("MicrosoftEdge");
					cap.setPlatform(org.openqa.selenium.Platform.WINDOWS);
				}
				ip=prop.getProperty("RemoteWebDriver");
				driver = new RemoteWebDriver(new URL("http://" + ip + ":"+hubPort+"/wd/hub"), cap);		
				logger.info("Node IP & Port = " + getNode(ip, hubPort));
			}
			else{		
				logger.info("Test = " + test + " Opening " + data.get("Browser") + " local Browser");
				if(data.get("Browser").toUpperCase().equals("MOZILLA")){
					System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "//drivers//geckodriver.exe");
					//Ashish added Marionette as True to avoid SSL error
					DesiredCapabilities cap = DesiredCapabilities.firefox();
					cap.setCapability("marionette", true);
					driver = new FirefoxDriver(cap);
				}else if(data.get("Browser").toUpperCase().equals("CHROME")){
					System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//drivers//chromedriver.exe");
					driver =  new ChromeDriver();
				}else if(data.get("Browser").toUpperCase().equals("EDGE")){
					System.setProperty("webdriver.edge.driver", System.getProperty("user.dir") + "//drivers//MicrosoftWebDriver.exe");
					driver =  new EdgeDriver();
				}else if(data.get("Browser").toUpperCase().equals("IE")){
					System.setProperty("webdriver.ie.driver",
							System.getProperty("user.dir") + "//drivers//IEDriverServer.exe");
					cap = DesiredCapabilities.internetExplorer();
					// Added by Siva
					cap.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
					cap.setCapability(InternetExplorerDriver.ENABLE_ELEMENT_CACHE_CLEANUP, true);
					cap.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
					cap.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
					cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
					cap.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
					cap.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
					cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
					cap.setCapability("ignoreProtectedModeSettings", true); 
					// Ended by Siva
					driver = new InternetExplorerDriver(cap);
					
				}
			}
		PlatformName = data.get("Platform");
		//this.browserType = browserType;
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		logger.info("Thread id = " + Thread.currentThread().getId());
		driver.manage().window().maximize();
		key.updateDbTestResults(Constants.PASS, "openBrowser");
		}catch(MalformedURLException e){
			e.printStackTrace();
			logInfo("FATAL", "Test = " + test, " Could not open browser / application: " + data.get("Browser"));
			key.updateDbTestResults(Constants.PASS, "openBrowser");
		}
		
		//insertServerDetailsExtentReport(ExtentManager.getExtentReport(), LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")), "USMECRZWOLINSK1");
	}
	


	
//********************************
	public String getNode(String hubIp, String hubPort) throws IOException{
		try{
			if(prop.getProperty("grid").equals("Y")){
				Host hub = new Host(hubIp, hubPort);
				String sessionId = ((RemoteWebDriver) driver).getSessionId().toString();
				GridApiAssistant assistant = new GridApiAssistant(hub);
				String node = assistant.getNodeDetailsForSession(sessionId).getIpAddress();
				int port = assistant.getNodeDetailsForSession(sessionId).getPort();
				String nodeIpPort = node + ":" + port;
				return nodeIpPort;
			}
			else
				return "N/A - exec without grid";
		}catch(Exception e){
			logInfo("FATAL", "getNode", e.getMessage());
			e.printStackTrace();
			return "Node Failed";
		}
		
	}
	
//*********************************Navigate to URL Method***********************************************
	public void navigate(Hashtable<String, String> data, Properties suiteProp, String objectLocator, String selectKey, String selectData) throws IOException, SQLException{
		logger.info("Test = " + test + " Navigating to " + suiteProp.getProperty(objectLocator));
		try{
			driver.navigate().to(suiteProp.getProperty(objectLocator));
			key.updateDbTestResults(Constants.PASS, "navigate");
		}catch(Exception e){
			e.printStackTrace();
			logInfo("FATAL", "Test = " + test, " Could not navigate to " +  suiteProp.getProperty(objectLocator));
			key.updateDbTestResults(Constants.FATAL, "navigate");
		}
	}

//*********************************Navigate to URL Method***********************************************
	public void navigate(Properties suiteProp, String objectLocator) throws IOException, SQLException{
		logger.info("Test = " + test + " Navigating to " + suiteProp.getProperty(objectLocator));
		try{
			driver.get(suiteProp.getProperty(objectLocator));
			//driver.navigate().to(suiteProp.getProperty(objectLocator));
			key.updateDbTestResults(Constants.PASS, "navigate");
		}catch(Exception e){
			e.printStackTrace();
			logInfo("FATAL", "Test = " + test, " Could not navigate to " +  suiteProp.getProperty(objectLocator));
			key.updateDbTestResults(Constants.FATAL, "navigate");
		}
	}
	
	
//*****************************************Get Current Page URL***********************************************************
	public String getUrl() {
		String url = null;
		try {
			url = driver.getCurrentUrl();
		} catch (Exception e) {
			e.printStackTrace();
			logInfo("FATAL", "Test = " + test, " Could not get current url");
		}
		return url;
	}
	
	
//*********************************Click on Element Method**********************************************
	public void click(Hashtable<String, String> data, Properties suiteProp, String objectLocator, String selectKey, String selectData) throws IOException, SQLException{
		logger.info("Test = " + test + " Clicking on " + objectLocator);
		try{
			WebElement e = getElement(objectLocator, suiteProp);
			e.click();
			key.updateDbTestResults(Constants.PASS, "click");
		}catch(Exception e){
			e.printStackTrace();
			logInfo("FATAL", "Test = " + test, " Could not click on " + objectLocator);
			key.updateDbTestResults(Constants.FATAL, ("click" + e.getMessage()));
		}
	}
	
	
//*********************************Click on Web Element Method**********************************************

	public void click (WebElement element) throws IOException, SQLException {
		logger.info("Test = " + test + " Clicking on " + element.getAttribute("id"));
		try {
			waitExplicit(driver, 60, element, "presence");
			waitExplicit(driver, 60, element, "clickable");
			((JavascriptExecutor) driver).executeScript("arguments[0].focus();", element);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
			key.updateDbTestResults(Constants.PASS, "click");
		} catch (Exception e) {
			e.printStackTrace();
			logInfo("FATAL", "Test = " + test, " Could not click on " + element.getAttribute("id"));
			key.updateDbTestResults(Constants.FATAL, "click" + e.getMessage());
		}
	}
	
//*********************************Input value in Element Method****************************************	
	public void input(Hashtable<String, String> data, Properties suiteProp, String objectLocator, String selectKey, String inputData) throws IOException, SQLException{
		logger.info("Test = " + test + " Inputting value in field = " + objectLocator );
		try{
			WebElement e = getElement(objectLocator, suiteProp);
			e.sendKeys(inputData);
			key.updateDbTestResults(Constants.PASS, "input");
		}catch(Exception e){
			e.printStackTrace();
			logInfo("FATAL", "Test = " + test, " Could not input value in field = " + objectLocator);
			key.updateDbTestResults(Constants.FATAL, "input" + e.getMessage());
		}
	}
	
//**********************************Select Value in Drop Down Method************************************
	public void selectDropdown(Hashtable<String, String> data, Properties suiteProp, String ObjectLocator, String selectKey, String selectData) throws IOException, SQLException{
		logger.info("Test = " + test + " Selecting " + data + " in " + ObjectLocator );
		Select s =  null;
		try{
			WebElement m = getElement(ObjectLocator, suiteProp);
			s = new Select(m);
			if(selectKey.endsWith("Value"))
				s.selectByValue(selectData);
			else if(selectKey.endsWith("Index"))
				s.selectByIndex(Integer.parseInt(selectData));
			else if(selectKey.endsWith("VisibleText"))
				s.selectByVisibleText(selectData);
			key.updateDbTestResults(Constants.PASS, "selectDropdown");
		}catch(Exception e){
			e.printStackTrace();
			logInfo("FATAL", "Test = " + test, " Could not select " + data + " in " + ObjectLocator);
			key.updateDbTestResults(Constants.FATAL, "selectDropdown" + e.getMessage());
		}	
	}
		
//*********************************Select value in Check Box Method*************************************
	public void selectCheckbox(Hashtable<String, String> data, Properties suiteProp, String ObjectLocator, String selectKey, String selectData) throws IOException, SQLException{
		logger.info("Test = " + test + " Selecting Checkbox " + ObjectLocator );
		boolean m = false;
		WebElement e = getElement(ObjectLocator, suiteProp);
		try{
		    m = e.isSelected();
		    if(m)
		    	key.updateDbTestResults(Constants.PASS, "selectCheckbox");
		    else 
		    	key.updateDbTestResults(Constants.FAIL, "selectCheckbox");
		}catch(Exception exception){
			exception.printStackTrace();
			logInfo("FATAL", "Test = " + test, " Not able to select checkbox " + ObjectLocator);
			key.updateDbTestResults(Constants.FATAL, "selectCheckbox" + exception.getMessage());
		}
	}
	
//****************************MouseOver Method**********************************************************
	public void mouseOver(Hashtable<String, String> data, Properties suiteProp, String ObjectLocator, String selectKey, String selectData) throws IOException, SQLException{
		logger.info("Test = " + test + " Mouseover on " + ObjectLocator );
		try{
			WebElement m = getElement(ObjectLocator, suiteProp);
		    Actions action = new Actions(driver);
		    action.moveToElement(m).build().perform();
		    key.updateDbTestResults(Constants.PASS, "mouseOver");
		}catch(Exception exception){
			exception.printStackTrace();
			logInfo("FATAL", "Test = " + test, " failure in finding mouseOverKey " + ObjectLocator );
			key.updateDbTestResults(Constants.FATAL, "mouseOver" + exception.getMessage());
		}
	}
	
//*********************Select Radio Button Method*******************************************************
	public void selectRadioButton(Hashtable <String, String> data, Properties suiteProp, String locatorkey, String selectKey, String selectData) throws IOException, SQLException {
		logger.info("Test = " + test + " Selecting radio button " + locatorkey );
		try{
			List<WebElement> e = getElements(locatorkey,suiteProp);	
			for(WebElement ele : e) {
				if((ele.getAttribute("value").equalsIgnoreCase(selectData)) && (!ele.isSelected())) {
					ele.click();
					logger.info("Test = " + test + " Selected radio button " + ele.getAttribute("value"));
					break;
				}
			}
			key.updateDbTestResults(Constants.PASS, "selectRadioButton");
		}catch(Exception exception){
			exception.printStackTrace();
			logInfo("FATAL", "Test = " + test, " failure in finding RadioButton " + locatorkey + " OR " + selectData );
			key.updateDbTestResults(Constants.FATAL, "mouseOver" + exception.getMessage());
		}
	}
		
//********************************VALIDATION FUNCTIONS**************************************************
//******************************Verify Expected with Element Text Method********************************
	public void verifyText(Hashtable <String, String> data, Properties suiteProp, String locatorkey, String selectKey, String expectedData) throws IOException, SQLException{
		logger.info("Test = " + test + " Verifying " + expectedData + " with " + locatorkey + ".text" );
		try{ 
			WebElement e = getElement(locatorkey, suiteProp);
			String actualText = e.getText();
			if(actualText.equals(expectedData)){
				logInfo("PASS","Test = " + test, " Actual Result is equal to Expected ResultExpected = " + expectedData + " & Actual Result = " + actualText);
				key.updateDbTestResults(Constants.PASS, "verifyText");
			} else {
				logInfo("FAIL","Test = " + test, " Actual Result not equal to Expected Result, Expected = " + expectedData + " & Actual Result = " + actualText);
				key.updateDbTestResults(Constants.FAIL, "verifyText " + "Actual Result not equal to Expected Result, Expected = " + expectedData + " & Actual Result = " + actualText );
			}
		}catch(Exception exception){
			exception.printStackTrace();
			logInfo("FATAL", "Test = " + test, " failure in finding " + locatorkey);
			key.updateDbTestResults(Constants.FATAL, "verifyText" + exception.getMessage());
		}
	}

//******************************Verify Element Present Method*******************************************
	public void verifyElementPresent(Hashtable <String, String> data, Properties suiteProp, String locatorkey, String selectKey, String selectData) throws IOException, SQLException{
		logger.info("Test = " + test + " Verifying if " + locatorkey + " element is present" );
		boolean result = isElementPresent(locatorkey, suiteProp);
		try{
			if(result){
				logInfo("PASS","Test = " + test, " element: " + locatorkey + " is present");
				key.updateDbTestResults(Constants.PASS, "verifyElementPresent");
			}
			else{
				logInfo("FAIL","Test = " + test, " element: " + locatorkey + " is not present");
				key.updateDbTestResults(Constants.FAIL, "verifyElementPresent" + " Not able not find Element - " + locatorkey + ".");
			}
		}catch(Exception e){
			e.printStackTrace();
			logInfo("FATAL","Test = " + test, " failure in finding " + locatorkey);
			key.updateDbTestResults(Constants.FATAL, "verifyElementPresent" + e.getMessage());
		}
	}
	
//******************************Verify Element Not Present Method***************************************	
	public void verifyElementNotPresent(Hashtable <String, String> data, Properties suiteProp, String locatorkey, String selectKey, String selectData) throws IOException, SQLException{
		logger.info("Test = " + test + " Verifying if " + locatorkey + " element is not present" );
		boolean result = isElementPresent(locatorkey, suiteProp);
		try{
			if(!result){
				logInfo("PASS","Test = " + test, " element: " + locatorkey + " is not present");
				key.updateDbTestResults(Constants.PASS, "verifyElementNotPresent");
			}
			else{
				logInfo("FAIL","Test = " + test, " element: " + locatorkey + " is present");
				key.updateDbTestResults(Constants.PASS, "verifyElementNotPresent " + " Was able to find Element - " + locatorkey + ".");
			}
		}catch(Exception e){
			e.printStackTrace();
			logInfo("FATAL","Test = " + test, " failure in finding " + locatorkey);
			key.updateDbTestResults(Constants.FATAL, "verifyElementPresent" + e.getMessage());
		}
	}
	
//***************************************UTILITY FUNCTIONS**********************************************
//*********************************Get Element Method***************************************************
	public WebElement getElement(String locatorkey, Properties suiteProp){
		logger.info("Test = " + test+ " Getting Element: " + locatorkey);
		WebElement e = null;
		try{
			if(locatorkey.endsWith("id")){
				e=driver.findElement(By.id(suiteProp.getProperty(locatorkey)));
			}
			else if(locatorkey.endsWith("name")){
				e=driver.findElement(By.name(suiteProp.getProperty(locatorkey)));
			}
			else if(locatorkey.endsWith("xpath")){
				e=driver.findElement(By.xpath(suiteProp.getProperty(locatorkey)));
			}
			else if(locatorkey.endsWith("cssSelector")){
				e=driver.findElement(By.cssSelector(suiteProp.getProperty(locatorkey)));
			}
		}catch(Exception exception){
			exception.printStackTrace();
			logInfo("FATAL", "Test = " + test, " failure in finding " + locatorkey);
		}
		return e;
	}
	
	
//**********************************Get Elements Method*************************************************
	public List<WebElement> getElements(String locatorkey, Properties suiteProp){
		logger.info("Test = " + test + " Getting Elements: " + locatorkey);
		List<WebElement> e = null;
		try{
			if(locatorkey.endsWith("id")){
				e=driver.findElements(By.id(suiteProp.getProperty(locatorkey)));
			}
			else if(locatorkey.endsWith("name")){
				e=driver.findElements(By.name(suiteProp.getProperty(locatorkey)));
			}
			else if(locatorkey.endsWith("xpath")){
				e=driver.findElements(By.xpath(suiteProp.getProperty(locatorkey)));
			}
			else if(locatorkey.endsWith("cssSelector")){
				e=driver.findElements(By.cssSelector(suiteProp.getProperty(locatorkey)));
			}
		}catch(Exception exception){
			exception.printStackTrace();
			logInfo("FATAL", "Test = " + test, " failure in finding " + locatorkey);
		}
		return e;
	}
	
//************************************Is Element Present Method*****************************************
	public boolean isElementPresent(String locatorkey, Properties suiteProp){
		logger.info("Test = " + test + " Verifying if element is present: " + locatorkey);
		List <WebElement> e = null;
		try{
			if(locatorkey.endsWith("id")){
				e=driver.findElements(By.id(suiteProp.getProperty(locatorkey)));
			}
			else if(locatorkey.endsWith("name")){
				e=driver.findElements(By.name(suiteProp.getProperty(locatorkey)));
			}
			else if(locatorkey.endsWith("xpath")){
				e=driver.findElements(By.xpath(suiteProp.getProperty(locatorkey)));
			}
			else if(locatorkey.endsWith("cssSelector")){
				e=driver.findElements(By.cssSelector(suiteProp.getProperty(locatorkey)));
			}
			if(e.size()==0)
				return false;
			else
				return true;
		}catch(Exception exception){
			exception.printStackTrace();
			logInfo("FATAL", "Test = " + test, " failure in finding " + locatorkey);
			return false;
		}
	}
	
//**********************************Get Element Size Method*********************************************
	public int getElementsSize(String locatorkey, Properties suiteProp){
		logger.info(" Inputting data in " + prop.getProperty(locatorkey));
		int s;
		List<WebElement> e = getElements(locatorkey, suiteProp);
		try{
			s = e.size();			
		}catch(Exception exception){
			logInfo("FATAL", "getElementSize", "Not able to find size of Element " + locatorkey);
			s = 0;
		}
		return s;
	}
	
	//************************************Get Element Present Method*****************************************
    public int getElementPresent(String locatorkey){
          logInfo("INFO", "Test = " + test + " Verifying if element is present: " + locatorkey);
          List <WebElement> e = null;            
                        
          try{
                 if(locatorkey.endsWith("id")){
                        e=driver.findElements(By.id(prop.getProperty(locatorkey)));
                 }
                 if(locatorkey.endsWith("name")){
                        e=driver.findElements(By.name(prop.getProperty(locatorkey)));
                 }
                 if(locatorkey.endsWith("xpath") || locatorkey.contains("xpath_")){
                        e=driver.findElements(By.xpath(prop.getProperty(locatorkey)));                        
                 }
                 if(locatorkey.endsWith("cssSelector") || locatorkey.contains("CSS_")){
                        e=driver.findElements(By.cssSelector(prop.getProperty(locatorkey)));
                 }                   
                 if(e.size()==0) {
                        XPATH_CSS_NUM=XPATH_CSS_NUM+1;                       
                        for(int i=XPATH_CSS_NUM; i<=XPATH_LOCATOR_LIMIT; i++) {       
                              if(elementPresent) {
                                     break;
                              }                                
                              String key[] = locatorkey.split("_");                             
                              getElementPresent(key[0]+"_xpath_"+i);
                              getElementPresent(key[0]+"_CSS_"+i);
                              logInfo("FATAL", "Cannot find the element with locator key");                           
                        }
                 }      
                 else {              
                        elementPresent = true;
                 }                   
          }catch(Exception exception){
                 //exception.printStackTrace();
                 logInfo("FATAL", "Test = " + test + " failure in finding " + locatorkey);                 
           }
          
          return XPATH_CSS_NUM;            
    }


//*********************************Get Element Method***************************************************
    public WebElement getElement(String locatorkey){
          logInfo("INFO", "Test = " + test + " Getting Element: " + locatorkey);
          WebElement e = null;
          try{
                 if(locatorkey.endsWith("id")){
                        e=driver.findElement(By.id(prop.getProperty(locatorkey)));
                 }
                 if(locatorkey.endsWith("name")){
                        e=driver.findElement(By.name(prop.getProperty(locatorkey)));
                 }
                 if(locatorkey.endsWith("xpath") || locatorkey.contains("xpath_")){
                     logInfo("INFO ", "Get Element Locatory Key "+locatorkey);
                     e=driver.findElement(By.xpath(prop.getProperty(locatorkey)));                         
                     if(locatorkey.endsWith("xpath") && !checkAppendXPaths(locatorkey)) {
                           appendElementFinderMethods(locatorkey, e);                                 
                     }                          
	              }
	              if(locatorkey.endsWith("cssSelector") || locatorkey.contains("CSS_")){
	                     e=driver.findElement(By.cssSelector(prop.getProperty(locatorkey)));
	              }
	              if(locatorkey.contains("xpath_") || locatorkey.contains("CSS_")) {                       
	                     String fileName = System.getProperty("user.dir")+"//src//test//resources//project.properties";
	                     updateElementIdInProperties(locatorkey, e, fileName);
	              }

          }catch(Exception exception){
                 exception.printStackTrace();
                 logInfo("FATAL", "Test = " + test + " failure in finding " + locatorkey);
          }
          return e;
    }
    
    public boolean checkAppendXPaths(String locatorkey) {
        logInfo("INFO", "Check Append XPath Available in properties");                                 
               if(prop.containsKey(locatorkey+"_1")) {                     
                      return true;
               }                          
        return false;
  }
  
  public void appendElementFinderMethods(String locatorkey, WebElement e) {
        String fileName = System.getProperty("user.dir")+"//src//test//resources//project.properties";                                 
        appendXPathByName(locatorkey, e, fileName);                             
        appendXPathFromPreviousElement(locatorkey, e, fileName);
        appendXPathByContains(locatorkey, e, fileName);
        appendXPathByChainedDec(locatorkey, e, fileName);
        appendXPathByFollowing(locatorkey, e, fileName);
        appendXPathByStartsWith(locatorkey, e, fileName);
        appendCSSSelectorByTagClassAttribute(locatorkey, e, fileName);
        appendCSSSelectorByTagContainingText(locatorkey, e, fileName);
        appendCSSSelectorByTagEndingText(locatorkey, e, fileName);
        appendCSSSelectorByTagAttrName(locatorkey, e, fileName);
        appendCSSSelectorByTagAttrTypeAttrName(locatorkey, e, fileName);
        appendCSSSelectorByTagAttrNameAttrContText(locatorkey, e, fileName);          
  }


public void updateElementIdInProperties(String locatorkey, WebElement e, String fileName) {
        logInfo("INFO", "Update old xpathId with new xpathId");
        String elementId = e.getAttribute("id");
        if(elementId == null) {
               return;
        }      
        String key[] = locatorkey.split("_");
        locatorkey = key[0]+"_"+"xpath";
        boolean setId = false;
        try {
               BufferedReader br = new BufferedReader(new FileReader(fileName));
               String line;
               StringBuilder sb = new StringBuilder();
               
               while((line = br.readLine()) != null){
                      if(line.contains(locatorkey) && !line.contains("#") && !setId){                             
                            sb.append("    "+locatorkey+" = //*[@id='"+elementId+"']"+"\n");
                            setId = true;
                            continue;                               
                      }
                      sb.append(line).append("\n");
               }
               br.close();
               BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
               bw.append(sb);
               bw.close();               
        }            
        catch(FileNotFoundException fe) {
               fe.printStackTrace();
               logInfo("FATAL", "Not able to update old xpathId with new xpathId");
        }
        catch(IOException fe) {
               fe.printStackTrace();
               logInfo("FATAL", "Not able to update old xpathId with new xpathId");
        }
  }
  
  public void appendXPathByName(String locatorkey, WebElement e, String fileName) {
        System.out.println("Append xpath by Name");
        String elementName = e.getAttribute("name");
        if(e.getAttribute("name") == null || e.getAttribute("name").isEmpty()) {
               return;
        }
        
        try {
               BufferedReader br = new BufferedReader(new FileReader(fileName));
               String line;
               StringBuilder sb = new StringBuilder();
               boolean setName=false;
               while((line = br.readLine()) != null){
                      if(line.contains(locatorkey) && !line.contains("#") && !setName){
                            sb.append(line).append("\n");
                            sb.append("    "+locatorkey+"_1 = //*[@name='"+elementName+"']"+"\n");
                            setName=true;       
                            continue;                               
                      }
                      sb.append(line).append("\n");
               }
               br.close();
               BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
               bw.append(sb);
               bw.close();               
        }            
        catch(FileNotFoundException fe) {
               fe.printStackTrace();
               logInfo("FATAL", "Not able to append XPath by Name for the element Id during first run");
        }
        catch(IOException fe) {
               fe.printStackTrace();
               logInfo("FATAL", "Not able to append XPath by Name for the element Id during first run");
        }
  }
  
  public void appendXPathFromPreviousElement(String locatorkey, WebElement e, String fileName) {
        System.out.println("Append xpath by Preceding Element");
        String elementId = e.getAttribute("id");             
        if(e.getAttribute("id") == null || e.getAttribute("id").isEmpty()) {
               return;
        }
        
        try {
               BufferedReader br = new BufferedReader(new FileReader(fileName));
               String precedingXPath = "//*[@id='"+elementId+"']/preceding::label[1]";                   
               WebElement ePreced=driver.findElement(By.xpath(precedingXPath));
               String elementIdPreced = ePreced.getAttribute("id");              
               String line;
               StringBuilder sb = new StringBuilder();
               while((line = br.readLine()) != null){
                      if(line.contains(locatorkey+"_1") && !line.contains("#")){
                            sb.append(line).append("\n");
                            sb.append("    "+locatorkey+"_2 = //*[@id='"+elementIdPreced+"']/following::input[1]"+"\n");        
                            continue;                               
                      }
                      sb.append(line).append("\n");
               }
               br.close();
               BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
               bw.append(sb);
               bw.close();
        }            
        catch(FileNotFoundException fe) {
               fe.printStackTrace();
               logInfo("FATAL", "Not able to append XPath by Previous element for the element Id during first run");
        }
        catch(IOException fe) {
               fe.printStackTrace();
               logInfo("FATAL", "Not able to append XPath by Previous element for the element Id during first run");
        }
  }
  
  public void appendXPathByContains(String locatorkey, WebElement e, String fileName) {  
        System.out.println("Append xpath by Contains");
        if(e.getAttribute("id") == null || e.getAttribute("id").isEmpty()) {
               return;
        }
        
        String keyword = prop.getProperty("keyword_contains");
        try {
               BufferedReader br = new BufferedReader(new FileReader(fileName));
               String line;
               StringBuilder sb = new StringBuilder();
               while((line = br.readLine()) != null){
                      if(line.contains(locatorkey+"_2") && !line.contains("#")){
                            sb.append(line).append("\n");
                            sb.append("    "+locatorkey+"_3 = //input[contains(@id,'"+keyword+"')]"+"\n");         
                            continue;                               
                      }
                      sb.append(line).append("\n");
               }
               br.close();
               BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
               bw.append(sb);
               bw.close();
        }            
        catch(FileNotFoundException fe) {
               fe.printStackTrace();
               logInfo("FATAL", "Not able to append XPath by Contains keyword during first run");
        }
        catch(IOException fe) {
               fe.printStackTrace();
               logInfo("FATAL", "Not able to append XPath by Contains keyword during first run");
        }
  }
  
  public void appendXPathByChainedDec(String locatorkey, WebElement e, String fileName) {  
        System.out.println("Append xpath by Chained Declaration");
        if(e.getAttribute("id") == null || e.getAttribute("id").isEmpty()) {
               return;
        }
                      
        String keyword = prop.getProperty("keyword_contains");
        String elementId = e.getAttribute("id");
        String elementName = e.getAttribute("name");                      
        String parentDiv = "//input[@id='"+elementId+"']/parent::div";                    
        WebElement eParentDiv=driver.findElement(By.xpath(parentDiv));
        String parentClass = eParentDiv.getAttribute("class");
        
        String xpath = null;
        for(int i=0; i<=5; i++) {
               xpath = "//div[@class='"+parentClass+"']//input["+i+"]";
               try {
                      WebElement childelement=driver.findElement(By.xpath(xpath));                       
                      if(childelement.getAttribute("name").equals(elementName) || childelement.getAttribute("id").contains(keyword)) {
                            break;
                      }
               }
               catch(Exception ex) {
                      
               }
               
        }            
        
        try {
               BufferedReader br = new BufferedReader(new FileReader(fileName));
               String line;
               StringBuilder sb = new StringBuilder();
               while((line = br.readLine()) != null){
                      if(line.contains(locatorkey+"_3") && !line.contains("#")){
                            sb.append(line).append("\n");
                            sb.append("    "+locatorkey+"_4 = "+xpath+"\n");                                
                            continue;                               
                      }
                      sb.append(line).append("\n");
               }
               br.close();
               BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
               bw.append(sb);
               bw.close();
        }            
        catch(FileNotFoundException fe) {
               fe.printStackTrace();
               logInfo("FATAL", "Not able to append XPath by chained declaration during first run");
        }
        catch(IOException fe) {
               fe.printStackTrace();
               logInfo("FATAL", "Not able to append XPath by chained declaration during first run");
        }
  }
  
  public void appendXPathByFollowing(String locatorkey, WebElement e, String fileName) {  
        System.out.println("Append xpath by Following");
        if(e.getAttribute("id") == null || e.getAttribute("id").isEmpty()) {
               return;
        }
        String[] elementList = {"input", "label"};           
        String elementId = e.getAttribute("id");
        String precedingElementId = null;
        String precedingElementType = null;
        for(int i=0; i<elementList.length; i++) {
               try {
                      String checkPrecedElement = "//input[@id='"+elementId+"']//preceding-sibling::"+elementList[i]+"[1]";
                      WebElement precedElement=driver.findElement(By.xpath(checkPrecedElement));
                      precedingElementId = precedElement.getAttribute("id");
                      precedingElementType = elementList[i];                     
                      break;
               }
               catch(Exception ex) {
                      
               }
        }            
        
        try {
               BufferedReader br = new BufferedReader(new FileReader(fileName));
               String line;
               StringBuilder sb = new StringBuilder();
               while((line = br.readLine()) != null){
                      if(line.contains(locatorkey+"_4") && !line.contains("#")){
                            sb.append(line).append("\n");
                            sb.append("    "+locatorkey+"_5 = //"+precedingElementType+"[@id='"+precedingElementId+"']//following-sibling::input[1]" +"\n");                           
                            continue;                               
                      }
                      sb.append(line).append("\n");
               }
               br.close();
               BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
               bw.append(sb);
               bw.close();
        }            
        catch(FileNotFoundException fe) {
               fe.printStackTrace();
               logInfo("FATAL", "Not able to append XPath by Following-Sibling during first run");
        }
        catch(IOException fe) {
               fe.printStackTrace();
               logInfo("FATAL", "Not able to append XPath by Following-Sibling during first run");
        }
  }
  
  public void appendXPathByStartsWith(String locatorkey, WebElement e, String fileName) {  
        System.out.println("Append xpath by Starts-with");
        if(e.getAttribute("id") == null || e.getAttribute("id").isEmpty()) {
               return;
        }
                      
        String elementId = e.getAttribute("id");
        
        try {
               BufferedReader br = new BufferedReader(new FileReader(fileName));
               String line;
               StringBuilder sb = new StringBuilder();
               while((line = br.readLine()) != null){
                      if(line.contains(locatorkey+"_5") && !line.contains("#")){
                            sb.append(line).append("\n");
                            sb.append("    "+locatorkey+"_6 = //input[starts-with(@id,'"+elementId.substring(0, elementId.length()/2)+"')]" +"\n");                                 
                            continue;                               
                      }
                      sb.append(line).append("\n");
               }
               br.close();
               BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
               bw.append(sb);
               bw.close();
        }            
        catch(FileNotFoundException fe) {
               fe.printStackTrace();
               logInfo("FATAL", "Not able to append XPath by starts-with during first run");
        }
        catch(IOException fe) {
               fe.printStackTrace();
               logInfo("FATAL", "Not able to append XPath by starts-with during first run");
        }
  }
  
  public void appendCSSSelectorByTagClassAttribute(String locatorkey, WebElement e, String fileName) { 
        System.out.println("Append CSS Selector by Tag Class Attribute");
        if(e.getAttribute("id") == null || e.getAttribute("id").isEmpty()) {
               return;
        }
                      
        String cls = e.getAttribute("class");         
        String elementName = e.getAttribute("name");
        try {
               BufferedReader br = new BufferedReader(new FileReader(fileName));
               String line;
               StringBuilder sb = new StringBuilder();
               while((line = br.readLine()) != null){
                      if(line.contains(locatorkey+"_6") && !line.contains("#")){
                            sb.append(line).append("\n");
                            String key[] = locatorkey.split("_");
                            locatorkey = key[0]+"_"+"CSS";
                            sb.append("    "+locatorkey+"_7 = input."+cls+"[name="+elementName+"]" +"\n");                             
                            continue;                               
                      }
                      sb.append(line).append("\n");
               }
               br.close();
               BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
               bw.append(sb);
               bw.close();
        }            
        catch(FileNotFoundException fe) {
               fe.printStackTrace();
               logInfo("FATAL", "Not able to append CSS Selector  by Tag Class Attribute");
        }
        catch(IOException fe) {
               fe.printStackTrace();
               logInfo("FATAL", "Not able to append CSS Selector  by Tag Class Attribute");
        }
  }
  
  public void appendCSSSelectorByTagContainingText(String locatorkey, WebElement e, String fileName) { 
        System.out.println("Append CSS Selector by Tag Containing Text");
        if(e.getAttribute("id") == null || e.getAttribute("id").isEmpty()) {
               return;
        }                          
                      
        String elementId = e.getAttribute("id");
        String elementText=null;
        if(elementId!=null && elementId.length()>=8 ) {
               elementText = elementId.substring(4,elementId.length()-4);
        }
        System.out.println("Locator key "+locatorkey);
        String key[] = locatorkey.split("_");
        locatorkey = key[0]+"_"+"CSS";                
        try {
               BufferedReader br = new BufferedReader(new FileReader(fileName));
               String line;
               StringBuilder sb = new StringBuilder();
               while((line = br.readLine()) != null){
                      if(line.contains(locatorkey+"_7") && !line.contains("#")){
                            sb.append(line).append("\n");
                            sb.append("    "+locatorkey+"_8 = input[id*='"+elementText+"']" +"\n");                             
                            continue;                               
                      }
                      sb.append(line).append("\n");
               }
               br.close();
               BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
               bw.append(sb);
               bw.close();
        }            
        catch(FileNotFoundException fe) {
               fe.printStackTrace();
               logInfo("FATAL", "Not able to append CSS Selector  by Tag Containing Text");
        }
        catch(IOException fe) {
               fe.printStackTrace();
               logInfo("FATAL", "Not able to append CSS Selector  by Tag Containing Text");
        }
  }
  
  public void appendCSSSelectorByTagEndingText(String locatorkey, WebElement e, String fileName) { 
        System.out.println("Append CSS Selector by Tag Ending Text");
        if(e.getAttribute("id") == null || e.getAttribute("id").isEmpty()) {
               return;
        }                          
                      
        String elementId = e.getAttribute("id");
        String elementText=null;
        if(elementId!=null && elementId.length()>=5) {
               elementText = elementId.substring(5,elementId.length());
        }
        String key[] = locatorkey.split("_");
        locatorkey = key[0]+"_"+"CSS";
        try {
               BufferedReader br = new BufferedReader(new FileReader(fileName));
               String line;
               StringBuilder sb = new StringBuilder();
               while((line = br.readLine()) != null){
                      if(line.contains(locatorkey+"_8") && !line.contains("#")){
                            sb.append(line).append("\n");                                                         
                            sb.append("    "+locatorkey+"_9 = input[id$='"+elementText+"']" +"\n");                             
                            continue;                               
                      }
                      sb.append(line).append("\n");
               }
               br.close();
               BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
               bw.append(sb);
               bw.close();
        }            
        catch(FileNotFoundException fe) {
               fe.printStackTrace();
               logInfo("FATAL", "Not able to append CSS Selector  by Tag Ending Text");
        }
        catch(IOException fe) {
               fe.printStackTrace();
               logInfo("FATAL", "Not able to append CSS Selector  by Tag Ending Text");
        }
  }
  
  public void appendCSSSelectorByTagAttrName(String locatorkey, WebElement e, String fileName) { 
        System.out.println("Append CSS Selector by Tag Attribute Name");
        if(e.getAttribute("id") == null || e.getAttribute("id").isEmpty()) {
               return;
        }                          
                      
        String elementName = e.getAttribute("name");
        String key[] = locatorkey.split("_");
        locatorkey = key[0]+"_"+"CSS";
        try {
               BufferedReader br = new BufferedReader(new FileReader(fileName));
               String line;
               StringBuilder sb = new StringBuilder();
               while((line = br.readLine()) != null){
                      if(line.contains(locatorkey+"_9") && !line.contains("#")){
                            sb.append(line).append("\n");                                                         
                            sb.append("    "+locatorkey+"_10 = input[name='"+elementName+"']" +"\n");                            
                            continue;                               
                      }
                      sb.append(line).append("\n");
               }
               br.close();
               BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
               bw.append(sb);
               bw.close();
        }            
        catch(FileNotFoundException fe) {
               fe.printStackTrace();
               logInfo("FATAL", "Not able to append CSS Selector  by Tag Attribute Name");
        }
        catch(IOException fe) {
               fe.printStackTrace();
               logInfo("FATAL", "Not able to append CSS Selector  by Tag Attribute Name");
        }
  }
  
  public void appendCSSSelectorByTagAttrTypeAttrName(String locatorkey, WebElement e, String fileName) { 
        System.out.println("Append CSS Selector by Tag Attribute Type Attribute Name");
        if(e.getAttribute("id") == null || e.getAttribute("id").isEmpty()) {
               return;
        }                          
                      
        String elementName = e.getAttribute("name");
        String elementType = e.getAttribute("type");
        String key[] = locatorkey.split("_");
        locatorkey = key[0]+"_"+"CSS";
        try {
               BufferedReader br = new BufferedReader(new FileReader(fileName));
               String line;
               StringBuilder sb = new StringBuilder();
               while((line = br.readLine()) != null){
                      if(line.contains(locatorkey+"_10") && !line.contains("#")){
                            sb.append(line).append("\n");                                                         
                            sb.append("    "+locatorkey+"_11 = input[type='"+elementType+"'][name='"+elementName+"']"  +"\n");                             
                            continue;                               
                      }
                      sb.append(line).append("\n");
               }
               br.close();
               BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
               bw.append(sb);
               bw.close();
        }  catch(FileNotFoundException fe) {
               fe.printStackTrace();
               logInfo("FATAL", "Not able to append CSS Selector  by Tag Attribute Type Attribute Name");
        }
        catch(IOException fe) {
               fe.printStackTrace();
               logInfo("FATAL", "Not able to append CSS Selector  by Tag Attribute Type Attribute Name");
        }
  }
  
  public void appendCSSSelectorByTagAttrNameAttrContText(String locatorkey, WebElement e, String fileName) {  
        System.out.println("Append CSS Selector by Tag Attribute Name Attribute Containing Text");
        if(e.getAttribute("id") == null || e.getAttribute("id").isEmpty()) {
               return;
        }                          
        String elementId = e.getAttribute("id");             
        String elementName = e.getAttribute("name");
        String elementText=null;
        if(elementId!=null && elementId.length()>=8 ) {
               elementText = elementId.substring(4,elementId.length()-4);
        }
        String key[] = locatorkey.split("_");
        locatorkey = key[0]+"_"+"CSS";
        try {
               BufferedReader br = new BufferedReader(new FileReader(fileName));
               String line;
               StringBuilder sb = new StringBuilder();
               while((line = br.readLine()) != null){
                      if(line.contains(locatorkey+"_11") && !line.contains("#")){
                            sb.append(line).append("\n");                                                         
                            sb.append("    "+locatorkey+"_12 = input[name='"+elementName+"'][id*='"+elementText+"']"  +"\n");                              
                            continue;                               
                      }
                      sb.append(line).append("\n");
               }
               br.close();
               BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
               bw.append(sb);
               bw.close();
        }            
        catch(FileNotFoundException fe) {
               fe.printStackTrace();
               logInfo("FATAL", "Not able to append CSS Selector  by Tag Attribute Name Attribute Containing Text");
        }
        catch(IOException fe) {
               fe.printStackTrace();
               logInfo("FATAL", "Not able to append CSS Selector  by Tag Attribute Name Attribute Containing Text");
        }
  }

    
    public WebElement getElement(int locatorNum){ 
        logInfo("INFO", "Locator Num "+locatorNum);
        WebElement e = null;
        if(locatorNum==0) {                     
               e = getElement("SearchProduct_xpath");
        }
        if(e == null) {
               e = getElement("SearchProduct_xpath_"+locatorNum);
        }
        if(e == null) {
               e = getElement("SearchProduct_CSS_"+locatorNum);
        }
        
        return e;
  }

    


	
//*************************************Window Handler Method********************************************
	public void windowhandle(String locatorkey) throws IOException, SQLException{
		logger.info(" Window handler to switch to child window");
		try{			
			Set<String>ids = driver.getWindowHandles();
			Iterator<String> itr = ids.iterator();
			String parentwin = itr.next();
			String childwin = itr.next();
			driver.switchTo().window(childwin);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			System.out.println("switched to window with title ->" +  driver.getTitle());
			key.updateDbTestResults(Constants.PASS, "windowhandle");
		}catch(Exception exception){
			exception.printStackTrace();
			logInfo("FATAL", "windowhandle", "Not able to find child window " + locatorkey);
			key.updateDbTestResults(Constants.FATAL, "windowhandle " + "Not able to find child window " + locatorkey + exception.getMessage());		
		}
	}
	


//*******************************************Close Browser Method***************************************
	public void closeBrowser() throws IOException, SQLException {
		logger.info("Closing Browser");
		try{
			if(driver != null){
				//key.updateDbTestResults(Constants.PASS, "closeBrowser");
				driver.quit();
			}
			else if(aDriver != null){ 
				//key.updateDbTestResults(Constants.PASS, "closeBrowser");
				aDriver.quit();
			} /*else 
				key.updateDbTestResults(Constants.FAIL, "closeBrowser");*/
		}
		catch(Exception e){
			e.printStackTrace();
			logInfo("FATAL", "closeBrowser", "not able to close browser");
			key.updateDbTestResults(Constants.FATAL, "closeBrowser " + e.getMessage());
		}
	}

	//********************************* Send Keys ******************************************************
	public void sendKey(String locatorkey, Keys selectkey, Properties suiteProp) throws IOException, SQLException{
		try{
			logger.info("Sending Key - " + locatorkey);
			WebElement e = getElement(locatorkey,suiteProp);
			e.sendKeys(selectkey);
			key.updateDbTestResults(Constants.PASS, selectkey.toString());
		}catch(Exception e){
			e.printStackTrace();
			logInfo("FATAL", "sendKey", "not able send key - " + locatorkey);
			key.updateDbTestResults(Constants.FATAL, selectkey.toString() + e.getMessage());
		}
	}
	
	
	//***************************************Thread.Sleep Wait Method***************************************
	public void wait(String timeout) throws IOException, SQLException {
		logger.info("waiting");
		try{
			Thread.sleep(Integer.parseInt(timeout));
			key.updateDbTestResults(Constants.PASS, "wait");
		}catch(NumberFormatException n){
			n.printStackTrace();
			logInfo("FATAL", "wait", "exception while waiting");
			key.updateDbTestResults(Constants.FATAL, "wait " + n.getMessage());
		}catch(InterruptedException i){
			i.printStackTrace();
			logInfo("FATAL", "wait", "exception while waiting");
			key.updateDbTestResults(Constants.FATAL, "wait " + i.getMessage());
		}
	}
	
	
	
//************************************explicit wait functions******************************************
	
	public void waitExplicit(WebDriver driver, int timeout, String locatorkey, String ExpectedCondition, Properties suiteProp) throws IOException, SQLException{
		Boolean flag = true;
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			if(ExpectedCondition.equals("clickable")){
				WebElement e = wait.until(ExpectedConditions.elementToBeClickable(getElement(locatorkey,suiteProp)));
				flag = false;
			} else if(ExpectedCondition.equals("visible")){
				WebElement e = wait.until(ExpectedConditions.visibilityOf(getElement(locatorkey,suiteProp)));
				flag = false;
			} else if(ExpectedCondition.equals("invisible")){
				wait.until(ExpectedConditions.invisibilityOf(getElement(locatorkey,suiteProp)));
				flag = false;
			} else if(ExpectedCondition.equals("alert")){
				wait.until(ExpectedConditions.alertIsPresent());
				flag = false;
			} else if(ExpectedCondition.equals("presence")){
				wait.until(ExpectedConditions.presenceOfElementLocated(By.id(locatorkey)));
				flag = false;
			}
			if (flag.equals(false))
				key.updateDbTestResults(Constants.PASS, "wait Condition");
			else
				key.updateDbTestResults(Constants.FAIL, "wait Condition " + "not met");
	}
	
	
//********************wait Explicit method***********************
	public void waitExplicit(WebDriver driver, int time, WebElement element, String ExpectedCondition) throws IOException, SQLException {
		Boolean flag = true;
		FluentWait<WebDriver> wait = new WebDriverWait(driver, time);
		if (ExpectedCondition.equals("clickable")) {
			wait.until(ExpectedConditions.elementToBeClickable(element));
			flag = false;
		} else if (ExpectedCondition.equals("visible")) {
			wait.until(ExpectedConditions.visibilityOf(element));
			flag = false;
		} else if (ExpectedCondition.equals("invisible")) {
			wait.until(ExpectedConditions.invisibilityOf(element));
			flag = false;
		} else if (ExpectedCondition.equals("alert")) {
			wait.until(ExpectedConditions.alertIsPresent());
			flag = false;
		}
		if (flag.equals(false))
			key.updateDbTestResults(Constants.PASS, "waitExplicit");
		else
			key.updateDbTestResults(Constants.FAIL, "waitExplicit" + ExpectedCondition + "not met");
	}
	
//************************************LOGGING FUNCTIONS*************************************************
	public void logInfo(String logStatus, String stepName, String details){
		
		if(logStatus.equals("INFO")){
			test.log(LogStatus.INFO, stepName, details);
			logger.info(logStatus + details);
		}
		else if(logStatus.equals("FAIL")){
			test.log(LogStatus.FAIL, stepName, details);
			logger.info(logStatus + details);
		}
		else if(logStatus.equals("FATAL")){
			test.log(LogStatus.FATAL, stepName, details);
			logger.fatal(logStatus + details);
		}
		else if(logStatus.equals("PASS")){
			test.log(LogStatus.PASS, stepName, details);
			logger.info(logStatus + details);
		}
	}

//*******************************Get Page Objects*******************************************************
	public void captureObjects(String url, RepositoryGenerator sitecom){
		CaptureObjects.getPageObjects(url, sitecom);
	}
	
//********************************REPORTING FUNCTIONS***************************************************
//********************************Take Screenshot Method************************************************
	public void takeScreenshot(String testName) throws IOException{
		logger.info("taking screenshot");
		try{
		// decide name - time stamp
				test = getTest();
				String runName = ExtentManager.getRunName();
				Date d = new Date();
				String screenshotFile=d.toString().replace(":", "_").replace(" ","_")+".png";
				String path=Constants.SCREENSHOT_PATH+runName +"_" +testName +"_"+ screenshotFile;
				// take screenshot
				File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(srcFile, new File(path));
				// embed
				logInfo("INFO", testName, test.addScreenCapture(path));
		}catch(Exception exception){
			exception.printStackTrace();
			logInfo("FATAL", testName, "exception while taking screenshot");
		}
	}
	
//**********************************Reporting Pass Method***********************************************
	public void reportPass(String testname) throws IOException{
		if(driver != null){
			if(prop.getProperty("screenshot").equals("Y"))
				takeScreenshot(testname);
		}
		logInfo("PASS", testname, " test passed");
	}
	
//*********************************Reporting Exception/Fatal Method*************************************
	public void reportException(String testname, String FailureMessage) throws IOException{
		if(driver != null){
			fatalflag=true;
			takeScreenshot(testname);
		}
		logInfo("FATAL", testname, " The test failed due to " + FailureMessage );
	}
	
//*********************************Reporting Fail Method************************************************
	public void reportDefect(String testname, String defectMessage) throws IOException{
		if(driver != null){
			failflag=true;
			takeScreenshot(testname);
		}
		logInfo("FAIL", testname, " Defect Found " + defectMessage);
	}
	
//*************************************DATABASE FUNCTIONS***********************************************
//*************************************Insert into Detail Table*****************************************
	@SuppressWarnings("static-access")
	public void insertDBDetailRecord(String suitename, String tcid, String keyword, String teststatus, String testrunkey, String createdby) throws IOException, SQLException{
		try{
			String Node = "";
			logger.info("Inserting record in DB Detail table");
			dbConnect2 dbcon = new dbConnect2();
			String insmodel = "INSERT INTO test_detail (test_suite,test_case, test_keyword, test_status, test_runkey, node_detail, platform, browser, created_by,created_at,modified_by,modified_at)\r\n" +
					"VALUES (";
			String insmodel2 = ");";
			if (teststatus.equals("SKIPPED"))
				Node = "Not Applicable";
			else 
				Node = getNode(ip, hubPort);
			if (Node.equals("Node Failed"))
				teststatus = "FATAL";
			
				
			Timestamp TimeStamp = CurrentDateAndMonth.timeStampVal();
			String selInsert = insmodel + "'"+ suitename + "'" + ","+ "'" + tcid + "'" + "," +
					"'"+ keyword + "'" + "," + "'" + teststatus + "'" + "," + "'" + testrunkey + "'"+ "," + "'" + Node + "'" + "," + 
					"'" + browserType + "'" + "," + "'" + PlatformName + "'" + "," + "'" + createdby + "'" + "," + "'" + TimeStamp + "'" + "," + "'" + createdby + "'" + "," + "'" + TimeStamp + "'" 
					+ insmodel2;
			System.out.println(selInsert);
		    dbcon.sqlinsert(selInsert);
		}catch (Exception e){
			e.printStackTrace();
			logInfo("FATAL", suitename, "Not able to connect to DB or Error while inserting records in Detail Table");
		}
	}
	
	public String selectDBDetailRecord(String suitename, String testrunid, String Status, String keyword){
		logger.info("Selecting record from DB Detail table with run name");
		String testcount = "";
		try{
			testcount = "select count(test_status) AS test_count from test_detail where test_suite = '" + suitename + "'" + "\r\n" + 
				"and test_status = '" + Status + "' and test_keyword = '" + keyword + "' and test_runkey = " + "'" + testrunid + "'" + ";";
		System.out.println(testcount);
		}catch(Exception e){
			e.printStackTrace();
			logInfo("FATAL", suitename, "Not able to connect to DB or Error while selecting records in Detail Table");
		}
		return testcount;
	}
	
	public String selectDBDetailRecord(String suitename, String testrunid, String testcase, String Status, String keyword){
		logger.info("Selecting record from DB Detail table with test case name");
		String stepcount = "";
		try{
			stepcount = "select count(test_status) AS test_count from test_detail where test_suite = '" + suitename + "'" + "\r\n" + 
				"and test_status = '" + Status + "' and test_keyword != '" + keyword + "' and test_runkey = " + "'" + testrunid + "'" + "and test_case = " + "'" + testcase + "'" + ";";
			System.out.println(stepcount);
		}catch(Exception e){
			e.printStackTrace();
			logInfo("FATAL", suitename, "Not able to connect to DB or Error while selecting records in Detail Table");
		}
		return stepcount;
	}
	
	
	@SuppressWarnings("static-access")
	public void insertDBSummaryRecord(String testrunid, String suitename, String createdBy) throws NumberFormatException, SQLException{
		try{
			logger.info("Inserting record from test_suite_summary table");
			dbConnect2 dbrep = new dbConnect2();
			Timestamp TimeStamp = CurrentDateAndMonth.timeStampVal();
			String insmodel = "INSERT INTO test_suite_summary (test_runid, test_suite, test_planned, test_passed, test_failed, test_skipped, test_exception, created_by, created_at, modified_by, modified_at)\r\n" + 
				"VALUES (";
			String comma = ",";
			String insmodel2 = ");";
			int passcnt = Integer.parseInt(dbrep.sqlcount(selectDBDetailRecord(suitename, testrunid, "PASS", "n/a")));
			int failcnt = Integer.parseInt(dbrep.sqlcount(selectDBDetailRecord(suitename, testrunid, "FAIL", "n/a")));
			int skipcnt = Integer.parseInt(dbrep.sqlcount(selectDBDetailRecord(suitename, testrunid, "SKIPPED", "n/a")));
			int fatalcnt = Integer.parseInt(dbrep.sqlcount(selectDBDetailRecord(suitename, testrunid, "FATAL", "n/a")));
		
			//Summing up for total test cases
			int testplanned = passcnt + failcnt + skipcnt + fatalcnt;
		
			//Insert the values
			String selInsert = insmodel+ "'" + testrunid + "'" + comma + "'" + suitename + "'" + comma + testplanned + comma + passcnt + comma +
				failcnt + comma + skipcnt + comma + fatalcnt + comma + "'" + createdBy + "'" + comma + "'" + TimeStamp + "'" 
				+ comma + "'" + createdBy + "'" + comma + "'" + TimeStamp + "'"+ insmodel2;
			System.out.println(selInsert);
			dbrep.sqlinsert(selInsert);
		}catch(Exception e){
			e.printStackTrace();
			logInfo("FATAL", suitename, "Not able to connect to DB or Error while inserting records in test_run_summary table");
		}
	}
	
	@SuppressWarnings("static-access")
	public void insertDBSummaryRecord(String testrunid, String suitename, String testcase, String createdBy) throws NumberFormatException, SQLException{
		try{
			logger.info("Inserting record from test_case_summary table");
			dbConnect2 dbrep = new dbConnect2();
			Timestamp TimeStamp = CurrentDateAndMonth.timeStampVal();
			String insmodel = "INSERT INTO test_case_summary (test_runid, test_suite, test_case, test_steps_planned, test_steps_passed, test_steps_failed, test_steps_skipped, test_steps_exception, created_by, created_at, modified_by, modified_at)\r\n" + 
				"VALUES (";
			String comma = ",";
			String insmodel2 = ");";
			int passcnt = Integer.parseInt(dbrep.sqlcount(selectDBDetailRecord(suitename, testrunid, testcase, "PASS", "overall")));
			int failcnt = Integer.parseInt(dbrep.sqlcount(selectDBDetailRecord(suitename, testrunid, testcase, "FAIL", "overall")));
			int skipcnt = Integer.parseInt(dbrep.sqlcount(selectDBDetailRecord(suitename, testrunid, testcase, "SKIPPED", "overall")));
			int fatalcnt = Integer.parseInt(dbrep.sqlcount(selectDBDetailRecord(suitename, testrunid, testcase, "FATAL", "overall")));
		
			//Summing up for total test cases
			int teststepsplanned = passcnt + failcnt + skipcnt + fatalcnt;
		
			//Insert the values
			String selInsert = insmodel+ "'" + testrunid + "'" + comma + "'" + suitename + "'" + comma + "'" + testcase + "'" + comma + teststepsplanned + comma + passcnt + comma +
				failcnt + comma + skipcnt + comma + fatalcnt + comma + "'" + createdBy + "'" + comma + "'" + TimeStamp + "'" 
				+ comma + "'" + createdBy + "'" + comma + "'" + TimeStamp + "'"+ insmodel2;
			System.out.println(selInsert);
			dbrep.sqlinsert(selInsert);
		}catch(Exception e){
			e.printStackTrace();
			logInfo("FATAL", suitename, "Not able to connect to DB or Error while inserting records in test_case_summary table");
		}
	}
	
//***************************************THROW AWAY FUNCTIONS*******************************************
	public String verifyCalc(Hashtable<String, String> data, Properties suiteProp, String objectLocator, String selectKey, String selectData) throws InterruptedException{
		logger.info("verify calculation method");
		int calc = Integer.parseInt(data.get("Data1"))*Integer.parseInt(data.get("Data2"));	
		
		try {
			logger.info("calling runAxe Method");
			runAXE("Microsoft");
			wait(120);
			runValidator("Microsoft");
			
			if(calc == Integer.parseInt(data.get("CalculatedValue")))
				return Constants.PASS;
			else{
				return Constants.FAIL + " Calculation is wrong";
			} 
		}catch (Exception e) {
				// TODO: handle exception
			logInfo("FATAL", "verifyCalc", "Not able to calculate");
			return Constants.FATAL + "Not able to calculate ";
		}
	}
	
	
	/**
	 * Description: This method will run AXE @author Tejeshvi @throws
	 */
	public void runAXE(String screenName) {
		JSONObject responseJSON = new AXE.Builder(driver, scriptUrl).analyze();
		JSONArray violations = responseJSON.getJSONArray("violations");

		if (violations.length() == 0) {
			assertTrue("No violations found", true);
		} else {
			String JSONName = ExtentManager.getJSONName(screenName) + "AXE";
			JSONName = "JSONFiles/" + JSONName;
			AXE.writeResults(JSONName, responseJSON);
		}
	}

	/**
	 * Description: This method will run the validator @author Tejeshvi @throws
	 */
	public void runValidator(String screenName) {
		// validator code
		try {
			JsonNode response = null;
			String source = driver.getPageSource();
			com.mashape.unirest.http.HttpResponse<JsonNode> uniResponse;
			uniResponse = Unirest.post("http://localhost:8080/vnu").header("User-Agent",
					"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.101 Safari/537.36")
					.header("Content-Type", "text/html; charset=UTF-8").queryString("out", "json").body(source)
					.asJson();
			response = uniResponse.getBody();
			String JSONName = ExtentManager.getJSONName(screenName) + "Validator";
			JSONName = "JSONFiles/" + JSONName;
			AXE.writeResults(JSONName, response);
			System.out.println(response);
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ***************************************Today's
	// Date*******************************************
	public String[] getTodaysDate() {
		Calendar dateToday = Calendar.getInstance();
		String date[] = new String[3];

		int Month = dateToday.get(Calendar.MONTH) + 1;
		int Day = dateToday.get(Calendar.DAY_OF_MONTH);
		int Year = dateToday.get(Calendar.YEAR);
		String monthFormat = Integer.toString(Month);
		;
		String dayFormat = Integer.toString(Day);
		NumberFormat f = new DecimalFormat("00");
		if (Day < 10) {
			dayFormat = String.valueOf(f.format(Day));
			System.out.println(dayFormat);
		}
		if (Month < 10) {
			monthFormat = String.valueOf(f.format(Month));
			System.out.println(monthFormat);
		}

		date[0] = monthFormat;
		date[1] = dayFormat;
		date[2] = Integer.toString(Year);
		return date;

	}

	/**
	 * Description: This method will Add or subtract months from current
	 * Date @author Tejeshvi @throws
	 */
	public String addSubMonth(int months) {
		String nextDate = "";

		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("America/New_York"));
		Date dateObj;
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, months);
		String mm = sdf.format(calendar.getTime()).toString().split("-")[0].trim();
		String dd = sdf.format(calendar.getTime()).toString().split("-")[1].trim();
		String yyyy = sdf.format(calendar.getTime()).toString().split("-")[2].trim();
		nextDate = mm + "-" + dd + "-" + yyyy;
		return nextDate;
	}

	/**
	 * Description: This method will capture a screenshot @author Tejeshvi @throws
	 */
	public void captureScreenShot(String browser, String screenName, String status, String messageDetails) {
		logger.info("taking screenshot");
		try {
			// decide name - time stamp
			String testName = BaseTest.testname;
			String runName = ExtentManager.getRunName();
			Date d = new Date();
			String screenshotFile = d.toString().replace(":", "_").replace(" ", "_");
			String path = Constants.SCREENSHOT_PATH + runName + "_" + testName + "_" + screenshotFile + "_" + browser
					+ "_" + screenName + ".png";
			// take screenshot
			File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			FileUtils.copyFile(srcFile, new File(path));
			logInfo(status, screenName, messageDetails);
			// embed
			logger.info("attaching screenshot to logs" + test.addScreenCapture(path));
		} catch (Exception exception) {
			exception.printStackTrace();
			logInfo("FATAL", screenName, "exception while taking screenshot");
		}

	}

	// ***************************************Generate random
	// number*******************************************
	/*
	 * Description: This method is to generate a random number of specified length
	 * 
	 * @author Priya
	 * 
	 * @Parameters: length - length of number to generate
	 * 
	 * @Return Type: String
	 */
	public String getRandomNumber(int length) {
		StringBuffer resultNumber = new StringBuffer();
		for (int i = 0; i < length; i++) {
			resultNumber.append((int) Math.ceil(Math.random() * 9));
		}
		return resultNumber.toString();
	}

	

	// *************************************Get Random Number
	// Method*****************************************
	public String genRandomNumber(Integer length) throws Exception {
		int bound = 1;
		for (int i = 0; i < length; i++) {
			bound = bound * 10;
		}
		int min = bound / 10;
		int max = bound - 1;
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return String.valueOf(randomNum);
	}
	// ************************************Set Data
	// INI*****************************************

	public void setDataINI(String key, String value, String testname) {

		try {
			File f = getOutputFile();
			Wini a = new Wini(f);
			a.load(f);
			a.put(testname, key, value);
			a.store();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ************************************Get Data
	// INI*****************************************

	public String getDataINI(String testname, String key) {

		String value = "";
		try {
			File f = getOutputFile();
			Ini a = new Ini();
			a.load(f);
			value = a.get(testname, key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;

	}

	// ************************************File
	// creation*****************************************

	private File getOutputFile() throws IOException {
		String path = new File(".").getCanonicalPath() + Constants.OUTPUT_DATA;
		File f = new File(path);

		if (!f.exists()) {
			try {
				f.getParentFile().mkdirs();
				f.createNewFile();
				System.out.println("FILE CREATION SUCCEEDED!");
			} catch (IOException e) {
				System.out.println("FILE CREATION ERROR: " + e.getMessage());
				e.printStackTrace();
			}
		}
		return f;
	}

	// ************************************Write Data In OutPutData
	// File*****************************************

	public void writeDataToOutputFile(String testname, String browser) {
		int count = driver.findElements(By.xpath("//table[@id='grid0']/tbody/tr")).size() - 1;
		String[] PTR = { "PRIMARY", "SECOND", "THIRD", "FOURTH", "FIFTH", "SIXTH", "SEVENTH", "EIGHTH", "NINTH",
				"TENTH" };
		String[] TYPE = { "DETAILS", "SSN", "DOB" };
		String value;
		for (int col = 0; col < 3; col++) {
			value = driver
					.findElement(By.xpath("//table[@id='grid0']/tbody/tr[" + (count + 1) + "]/td[" + (3 + col) + "]"))
					.getAttribute("innerHTML");
			setDataINI(PTR[count - 1] + "_INDV_" + TYPE[col], value, testname + "_" + browser);
		}

		value = driver.findElement(By.xpath("//table[@id='g_ApplicationHeader']/tbody/tr[2]/td/div/a"))
				.getAttribute("innerHTML");
		setDataINI("APP_NUMBER", value, testname + "_" + browser);
		String caseNum = value.trim().replace("T", "1");
		setDataINI("CASE_NUMBER", caseNum, testname + "_" + browser);

	}

	public String waitFluent(WebDriver driver, int time, String locatorkey) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(time, TimeUnit.SECONDS)
				.pollingEvery(1, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);

		WebElement element = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				return driver.findElement(By.xpath(prop.getProperty(locatorkey)));
			}
		});
		return locatorkey;

	}

	public void waitForLoad(WebDriver driver) {
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(pageLoadCondition);
	}

	// ************************************FullWebPageScreenShot*****************************************

	public void FullWebPageScreenShot(WebDriver driver, String screenName, int count, String browserName) throws IOException, SQLException {
		if (browserName.toUpperCase().equals("SAFARI")) {
			wait("200");
			JavascriptExecutor je = (JavascriptExecutor) driver;
			je.executeScript("window.scrollTo(0, -document.body.scrollHeight);");
			wait("200");
			captureScreenShot(browserName, "_" + screenName + "_1", "Pass", "Passed");
			wait("200");
			for(int i=2;i<=count;i++)
			{
				je.executeScript("window.scrollBy(0,450)", "");
				if(i==count)
					je.executeScript("window.scrollTo(0, document.body.scrollHeight);");
				wait("200");
				captureScreenShot(browserName, "_" + screenName + "_"+i, "Pass", "Passed");
				wait("200");
			}
		} else {
			JavascriptExecutor je = (JavascriptExecutor) driver;
			je.executeScript("window.scrollTo(0, -document.body.scrollHeight);");
			captureScreenShot(browserName, "_" + screenName + "_1", "Pass", "Passed");
			for(int i=2;i<=count;i++)
			{
				je.executeScript("window.scrollBy(0,450)", "");
				if(i==count)
					je.executeScript("window.scrollTo(0, document.body.scrollHeight);");
				captureScreenShot(browserName, "_" + screenName + "_"+i, "Pass", "Passed");
			}
		}
	}

	public boolean textIsEqual(String locatorkey, String expectedText, Properties suiteProp) throws IOException, SQLException {
		if (isElementPresent(locatorkey, suiteProp) == false)
			return false;

		WebElement e = getElement(locatorkey,suiteProp);
		waitExplicit(driver, 10, e, "visible");
		String actualText = e.getText();
		return actualText.equals(expectedText);
	}
	
    public void passIEWarningsIfPresent(String browser) throws IOException, SQLException {
        wait("500");
        if (browser != null && browser.toUpperCase().equals("IE")) {
               if (driver.findElements(By.id("invalidcert_mainTitle")).size() > 0) {
                     driver.findElement(By.xpath(".//div[@id='moreInformationAlign']/table/tbody/tr[1]/td[1]/a")).click();
                     wait("500");
                     driver.findElement(By.id("overridelink")).click();
               }
        }
    }
    
    public void navigateBack(Hashtable <String, String> data, Properties suiteProp, String ObjectLocator, String selectKey, String selectData) throws IOException, SQLException {
		logger.info("Navigating Back");
		try{
			driver.navigate().back();
			key.updateDbTestResults(Constants.PASS, "navigateBack");
		} catch(Exception e){
			e.printStackTrace();
			logInfo("FATAL", "Test = " + test, "Could not navigate Back");
			key.updateDbTestResults(Constants.FATAL, "navigateBack " + e.getMessage());
		}
	}
	
	public void navigateForward(Hashtable <String, String> data, Properties suiteProp, String ObjectLocator, String selectKey, String selectData) throws IOException, SQLException {
		logger.info("Navigating Forward");
		try{
			driver.navigate().forward();
			key.updateDbTestResults(Constants.PASS, "navigateForward");
		} catch(Exception e){
			e.printStackTrace();
			logInfo("FATAL", "Test = " + test, "Could not navigate Forward");
			key.updateDbTestResults(Constants.FATAL, "navigateForward " + e.getMessage());
		}
	}
	
	public void pageRefresh(Hashtable <String, String> data, Properties suiteProp, String ObjectLocator, String selectKey, String selectData) throws IOException, SQLException {
		logger.info("Refresh the page");
		try{
			driver.navigate().refresh();
			key.updateDbTestResults(Constants.PASS, "pageRefresh");
		} catch(Exception e){
			e.printStackTrace();
			logInfo("FATAL", "Test = " + test, "Could not Refresh the page");
			key.updateDbTestResults(Constants.FATAL, "pageRefresh " + e.getMessage());
		}
	}
	
/**
 ******************************************************************** Mobile Keywords 
 * @throws SQLException 
 * @throws IOException *****************************************************************
 */
	
	//*********************************Open Mobile Browser********************************************
		public void openMobBrowser(Hashtable <String, String> data, Properties suiteProp, String ObjectLocator, String selectKey, String selectData) throws IOException, SQLException{
			try{
				String PlatformName = data.get("PlatformName");
				String DeviceName = data.get("DeviceName");
				String PlatformVersion = data.get("PlatformVersion");
				String browserType = data.get("Browser");
				logger.info("Test = " + test+ " Opening Mobile Browser: " + browserType);
				cap = new DesiredCapabilities();
				cap.setCapability("PLATFORM", PlatformName);
				cap.setCapability("platformName", PlatformName);
				if(PlatformVersion != null)
					cap.setCapability("version", PlatformVersion);
				if(browserType.toUpperCase().equals("CHROME"))
					cap.setCapability("browserName", MobileBrowserType.CHROME);
				else if (browserType.toUpperCase().equals("SAFARI"))
					cap.setCapability("browserName", MobileBrowserType.SAFARI);
				else
					cap.setCapability("browserName", MobileBrowserType.BROWSER);
				cap.setCapability("deviceName", DeviceName);
				String ip=prop.getProperty("AppiumDriver");
				if(PlatformName.toUpperCase().equals("ANDROID")){
					driver = new AndroidDriver<AndroidElement>(new URL("http://" + ip + ":4723/wd/hub"), cap);
			        driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
				} else if((PlatformName.toUpperCase().equals("IOS"))){
					driver = new IOSDriver(new URL("http://" + ip + ":4723/wd/hub"), cap);
			        driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
				}
				key.updateDbTestResults(Constants.PASS, "openMobBrowser");
			}catch(MalformedURLException m){
				m.printStackTrace();
				key.updateDbTestResults(Constants.FATAL, "openMobBrowser " + m.getMessage());
			}
		}
		
	//***************************//*********************************Open Mobile Application********************************************
		public void openMobApp(Hashtable <String, String> data, Properties suiteProp, String ObjectLocator, String selectKey, String selectData) throws IOException, SQLException{
			try{
				String PlatformName = data.get("PlatformName");
				String DeviceName = data.get("DeviceName");
				String PlatformVersion = data.get("PlatformVersion");
				String App = data.get("Application");
				logger.info("Test = " + test+ " Opening Mobile App");
				cap = new DesiredCapabilities();
				cap.setCapability(MobileCapabilityType.PLATFORM, PlatformName);
				cap.setCapability(MobileCapabilityType.APPIUM_VERSION, "1.8.0");
				if(PlatformVersion != null)
					cap.setCapability(MobileCapabilityType.VERSION, PlatformVersion);
				cap.setCapability(MobileCapabilityType.DEVICE_NAME, DeviceName);
					File application = new File(System.getProperty("user.dir") + "\\src\\test\\resources\\APK\\" + App);
					cap.setCapability(MobileCapabilityType.APP, application.getAbsolutePath());
				String ip = prop.getProperty("AppiumDriver");
				String url = "http://" + ip + ":4723/wd/hub";
				if(PlatformName.toUpperCase().equalsIgnoreCase("ANDROID")){
					aDriver = new AndroidDriver<MobileElement>(new URL(url), cap);
					aDriver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
				}else if((PlatformName.toUpperCase().equalsIgnoreCase("IOS"))){
					 driver = new IOSDriver(new URL("http://" + ip + ":4723/wd/hub"), cap);
			         driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
				}
				key.updateDbTestResults(Constants.PASS, "openMobApp");
			}catch(MalformedURLException m){
				m.printStackTrace();
				key.updateDbTestResults(Constants.PASS, "openMobApp " + m.getMessage());
			}
		}
		
		//*********************************Get MobileElement Method***************************************************
	public MobileElement getMobileElement(String locatorkey, Properties suiteProp) {
		logger.info("Test = " + test+ " Getting Element: " + locatorkey);
		MobileElement e = null;
		try {
			if (locatorkey.endsWith("id")) {
				e = aDriver.findElement(By.id(suiteProp.getProperty(locatorkey)));
			} else if (locatorkey.endsWith("name")) {
				e = aDriver.findElement(By.name(suiteProp.getProperty(locatorkey)));
			} else if (locatorkey.endsWith("xpath")) {
				e = aDriver.findElement(By.xpath(suiteProp.getProperty(locatorkey)));
			} else if (locatorkey.endsWith("cssSelector")) {
				e = aDriver.findElement(By.cssSelector(suiteProp.getProperty(locatorkey)));
			} else if (locatorkey.endsWith("className")) {
				e = aDriver.findElement(By.className(suiteProp.getProperty(locatorkey)));
			} else if (locatorkey.endsWith("androidUIAutomator")) {
				e = aDriver.findElementByAndroidUIAutomator(suiteProp.getProperty(locatorkey));
			} else if (locatorkey.endsWith("UISelector")) {
				e = aDriver.findElementByAndroidUIAutomator("new UiSelector()."+suiteProp.getProperty(locatorkey));
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			logInfo("FATAL", "Test = " + test, "failure in finding " + locatorkey);
		}
		return e;
	}
	
	//*********************************************Get MobileElements Method************************************************
	
	public List<MobileElement> getMobileElements(String locatorkey, Properties suiteProp) {
		logger.info("Test = " + test + " Getting Elements: " + locatorkey);
		List<MobileElement> e = null;
		try {
			if (locatorkey.endsWith("id")) {
				e = aDriver.findElements(By.id(suiteProp.getProperty(locatorkey)));
			} else if (locatorkey.endsWith("name")) {
				e = aDriver.findElements(By.name(suiteProp.getProperty(locatorkey)));
			} else if (locatorkey.endsWith("xpath")) {
				e = aDriver.findElements(By.xpath(suiteProp.getProperty(locatorkey)));
			} else if (locatorkey.endsWith("cssSelector")) {
				e = aDriver.findElements(By.cssSelector(suiteProp.getProperty(locatorkey)));
			} else if (locatorkey.endsWith("className")) {
				e = aDriver.findElements(By.className(suiteProp.getProperty(locatorkey)));
			} else if (locatorkey.endsWith("androidUIAutomator")) {
				e = aDriver.findElementsByAndroidUIAutomator(suiteProp.getProperty(locatorkey));
			} else if (locatorkey.endsWith("UISelector")) {
				e = aDriver.findElementsByAndroidUIAutomator(suiteProp.getProperty(locatorkey));
			}			
		} catch (Exception exception) {
			exception.printStackTrace();
			logInfo("FATAL", "Test = " + test, "failure in finding " + locatorkey);
		}
		return e;
	}
	
		
	 //*********************************************Method for tapping on Andriod element************************************************
	 
		
	public void mobileTapOnElement (Hashtable <String, String> data, Properties suiteProp, String ObjectLocator, String selectKey, String selectData) throws IOException, SQLException {
		TouchAction action = new TouchAction<>(aDriver);
		try {
			MobileElement element = getMobileElement(ObjectLocator, suiteProp);
	        int xCordinates = element.getLocation().getX();
	        int yCordinates = element.getLocation().getY();
	        action.tap(PointOption.point(xCordinates, yCordinates)).perform();
			logger.info("Succesfully tapped on the element");
			key.updateDbTestResults(Constants.PASS, "mobileTapOnElement");
		} catch (Exception e) {
			e.printStackTrace();
			logInfo("FATAl", "mobileTapOnElement", "Error while tapping on the element" + e.getMessage());
			key.updateDbTestResults(Constants.FATAL, "mobileTapOnElement " + e.getMessage());
		}
	}
	
	
	//*********************************************Method for swiping window  - Right Or Left************************************************
	 
	public void mobileSwipeLeftOrRight(Hashtable <String, String> data, Properties suiteProp, String ObjectLocator, String selectKey, String selectData) throws Exception {
		TouchAction action = new TouchAction<>(aDriver);
		try {
			double anchorPercentage = 0.5;
			double startPercentage = 0.9;
			double finalPercentage = 0.01;
		    Dimension size = aDriver.manage().window().getSize();
		    String direction = data.get("Swipe Direction");
		    int anchor = (int) (size.height * anchorPercentage);
		    int startPoint = (int) (size.width * startPercentage);
		    int endPoint = (int) (size.width * finalPercentage);
			if (direction.equalsIgnoreCase("Right")) {
				action.press(PointOption.point(startPoint, anchor)).waitAction(waitOptions(Duration.ofSeconds(2))).moveTo(PointOption.point(endPoint, anchor)).release().perform();
				logger.info("Succesfully swiped towards right");
			} else if (direction.equalsIgnoreCase("Left")) {
			    action.press(PointOption.point(endPoint, anchor)).waitAction(waitOptions(Duration.ofSeconds(2))).moveTo(PointOption.point(startPoint, anchor)).release().perform();
				logger.info("Successfully swiped towards left");
			}
			key.updateDbTestResults(Constants.PASS, "mobileSwipeLeftOrRight");
		}
		catch(Exception e) {
			e.printStackTrace();
			logInfo("FATAl", "mobileSwipeLeftOrRight", "Error while swiping the window" + e.getMessage());
			key.updateDbTestResults(Constants.FATAL, "mobileSwipeLeftOrRight " + e.getMessage());
		}
	}

	
	//*********************************************Method for scrolling window - up or down************************************************
	 
	
	public void mobileScrollUpOrDown(Hashtable <String, String> data, Properties suiteProp, String ObjectLocator, String selectKey, String selectData) throws Exception {
		TouchAction action = new TouchAction<>(aDriver);
		try {
			double xPercentage = 0.5;
			double yStartPercentage = 0.1;
			double yfinalPercentage = 0.9;
			String direction = data.get("Scroll Direction");
		    Dimension size = aDriver.manage().window().getSize();
		    int xCordinates = (int) (size.width * xPercentage);
		    int yStartPoint = (int) (size.height * yStartPercentage);
		    int yEndPoint = (int) (size.height * yfinalPercentage);
			if (direction.equalsIgnoreCase("Up")) {
				action.press(PointOption.point(xCordinates, yStartPoint)).waitAction(waitOptions(Duration.ofSeconds(2))).moveTo(PointOption.point(xCordinates, yEndPoint)).release().perform();
				logger.info("Succesfully scrolled upwards");
			} else if (direction.equalsIgnoreCase("Down")) {
			    action.press(PointOption.point(xCordinates, yEndPoint)).waitAction(waitOptions(Duration.ofSeconds(2))).moveTo(PointOption.point(xCordinates, yStartPoint)).release().perform();
				logger.info("Successfully scrolled downwards");
			}
			key.updateDbTestResults(Constants.PASS, "mobileScrollUpOrDown");
		}
		catch(Exception e) {
			e.printStackTrace();
			logInfo("FATAl", "mobileScrollUpOrDown", "Error while scrolling the window" + e.getMessage());
			key.updateDbTestResults(Constants.FATAL, "mobileScrollUpOrDown " + e.getMessage());
		}
	}
	
	//*********************************************mobileScrollTillElement************************************************
	
	public void mobileScrollTillElementFound(Hashtable <String, String> data, Properties suiteProp, String ObjectLocator, String selectKey, String selectData)
			throws Exception {
		try {
			for (int i = 1; i <= 10; i++) {
				try {
					aDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					boolean varVisible = getMobileElement(ObjectLocator, suiteProp).isDisplayed();
					if (varVisible) {
						getMobileElement(ObjectLocator, suiteProp).click();
					}
					logger.info("Succesfully scrolled to the element");
					key.updateDbTestResults(Constants.PASS, "mobileScrollTillElementFound");
				} catch (Exception e) {
					mobileScrollUpOrDown(data, suiteProp, ObjectLocator, selectKey, selectData);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logInfo("FATAl", "mobileScrollTillElementFound", "Error while scrolling" + e.getMessage());
			key.updateDbTestResults(Constants.FATAL, "mobileScrollTillElementFound " + e.getMessage());
		}
	}
	
	//*********************************************Method for drag and Drop************************************************
	 
	
	public void mobileDragAndDrop(Hashtable <String, String> data, Properties suiteProp, String ObjectLocator, String selectKey, String selectData) throws IOException, SQLException {
		TouchAction action = new TouchAction<>(aDriver);
		try {
			MobileElement moveFromElement = getMobileElement(data.get("moveFromElement"), suiteProp);
			MobileElement moveToElement = getMobileElement(data.get("moveToElement"), suiteProp);
			action.longPress(element(moveFromElement)).waitAction(waitOptions(Duration.ofSeconds(2))).moveTo(element(moveToElement)).release().perform();
			logger.info("Succesfully dropped the element");
			key.updateDbTestResults(Constants.PASS, "mobileDragAndDrop");
		} 
		catch(Exception e) {
			e.printStackTrace();
			logInfo("FATAl", "mobileDragAndDrop","Error while dragging and dropping" + e.getMessage());
			key.updateDbTestResults(Constants.FATAL, "mobileDragAndDrop " + e.getMessage());
		}
	}
	
	
	//*********************************************Method for press and release************************************************
	 
	
	public void mobilePressAndRelease(Hashtable <String, String> data, Properties suiteProp, String objectLocator, String selectKey, String selectData) throws IOException, SQLException {
		TouchAction action = new TouchAction<>(aDriver);
		try {
			MobileElement element = getMobileElement(objectLocator, suiteProp);
			action.press(element(element)).waitAction(waitOptions(Duration.ofSeconds(2))).release().perform();
			logger.info("Succesfully Pressed and released the element");
			key.updateDbTestResults(Constants.PASS, "mobilePressAndRelease");
		} catch (Exception e) {
			e.printStackTrace();
			logInfo("FATAl", "mobilePressAndRelease", "Error while press and Release" + e.getMessage());
			key.updateDbTestResults(Constants.FATAL, "mobilePressAndRelease " + e.getMessage());
		}
	}
	
	//*********************************************mobileScrollDowntoElement************************************************
	
	public void mobileScrollDowntoElement(Hashtable <String, String> data, Properties suiteProp, String objectLocator, String selectKey, String selectData) throws IOException, SQLException {
		try {
			aDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			aDriver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView("+suiteProp.getProperty(objectLocator)+");");
			logger.info("Succesfully scrolled to the element");
			key.updateDbTestResults(Constants.PASS, "mobileScrollDowntoElement");
		} catch (Exception e) {
			e.printStackTrace();
			logInfo("FATAl", "mobilePressAndRelease", "Error while scrolling" + e.getMessage());
			key.updateDbTestResults(Constants.FATAL, "mobileScrollDowntoElement " + e.getMessage());
		}
	}
	
	//*********************************************mobileGetCurrentActivity************************************************

	public void mobileGetCurrentActivity(Hashtable <String, String> data, Properties suiteProp, String objectLocator, String selectKey, String selectData) throws IOException, SQLException {
		try {
			String currentActivity = aDriver.currentActivity();
			System.out.println(currentActivity);
			logger.info("Succesfully obtained the current activity");
			key.updateDbTestResults(Constants.PASS, "mobileGetCurrentActivity");
		} catch (Exception e) {
			e.printStackTrace();
			logInfo("FATAl", "mobileGetCurrentActivity", "Error while obtaining the current activity" + e.getMessage());
			key.updateDbTestResults(Constants.FATAL, "mobileGetCurrentActivity " + e.getMessage());
		}
	}
	
	//*********************************************mobileGetContext************************************************

		public String mobileGetContext(Hashtable <String, String> data, Properties suiteProp, String objectLocator, String selectKey, String selectData) {
			String currentContext = null;
			try {
				currentContext = aDriver.getContext();
				logInfo("PASS", "mobileGetContext", "Current Context is :" + currentContext);
			} catch (Exception e) {
				e.printStackTrace();
				logInfo("FATAl", "mobileGetContext", "Error while obtaining the current context" + e.getMessage());
			}
			return currentContext;
		}
		
		//*********************************************mobileChangeOrientation************************************************

	public void mobileChangeOrientation(Hashtable <String, String> data, Properties suiteProp, String objectLocator, String selectKey, String selectData) throws IOException, SQLException {
		try {
			//ScreenOrientation currentOrientation = aDriver.getOrientation();
			String mode = data.get("Mode");
			if(mode.equalsIgnoreCase("Landscape")){			
				aDriver.rotate(ScreenOrientation.LANDSCAPE);
				logger.info("Succesfully changed the orientation to Landscape");
			} else if(mode.equalsIgnoreCase("Potrait")){
				aDriver.rotate(ScreenOrientation.PORTRAIT);
				logger.info("Succesfully changed the orientation to Potrait");
			}
			key.updateDbTestResults(Constants.PASS, "mobileChangeOrientation");
		} catch (Exception e) {
			e.printStackTrace();
			logInfo("FATAl", "mobileChangeOrientation", "Error while changing the orientation" + e.getMessage());
			key.updateDbTestResults(Constants.FATAL, "mobileChangeOrientation " + e.getMessage());
		}
	}
	
	//*********************************************mobileChangeOrientation************************************************

		public String mobilegetOrientation(Hashtable <String, String> data, Properties suiteProp, String objectLocator, String selectKey, String selectData) {
			String currentOrientation = null;
			try {
				ScreenOrientation orientation = aDriver.getOrientation();
				currentOrientation = orientation.toString();
				logInfo("PASS", "mobilegetOrientation", "Current orientation is: " + currentOrientation);
			} catch (Exception e) {
				e.printStackTrace();
				logInfo("FATAl", "mobilegetOrientation", "Error while changing the orientation" + e.getMessage());
			}
			return currentOrientation;
		}
	
	//*********************************************mobileisLocked************************************************

	public boolean mobileisLocked(Hashtable <String, String> data, Properties suiteProp, String objectLocator, String selectKey, String selectData) {
		boolean isLocked = false;
		try {
			isLocked = aDriver.isDeviceLocked();
			if (isLocked == true) {
				logger.info("Device is locked");
			} else {
				logger.info("Device is unlocked");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logInfo("FATAl", "mobileisLocked", "Error while obtaining the status of device locked" + e.getMessage());
		}
		return isLocked;
	}
		
		//*********************************************mobileHideKeyboard************************************************

	public void mobileHideKeyboard(Hashtable <String, String> data, Properties suiteProp, String objectLocator, String selectKey, String selectData) throws IOException, SQLException {
		try {
			aDriver.hideKeyboard();
			logger.info("Keyboard was hid successfully");
			key.updateDbTestResults(Constants.PASS, "mobileHideKeyboard");
		} catch (Exception e) {
			e.printStackTrace();
			logInfo("FATAl", "mobileHideKeyboard", "Error while hiding the keyboard" + e.getMessage());
			key.updateDbTestResults(Constants.FATAL, "mobileHideKeyboard " + e.getMessage());
		}
	}
	
	//*********************************************mobileGetKeyboard************************************************

	public void mobileGetKeyboard(Hashtable <String, String> data, Properties suiteProp, String objectLocator, String selectKey, String selectData) throws IOException, SQLException {
		try {
			aDriver.getKeyboard();
			logger.info("Keyboard was opened successfully");
			key.updateDbTestResults(Constants.PASS, "mobileGetKeyboard");
		} catch (Exception e) {
			e.printStackTrace();
			logInfo("FATAl", "mobileGetKeyboard", "Error while opening the keyboard" + e.getMessage());
			key.updateDbTestResults(Constants.FATAL, "mobileGetKeyboard " + e.getMessage());
		}
	}
	
	//*********************************************mobilePresskey************************************************

	public void mobilePresskey(Hashtable <String, String> data, Properties suiteProp, String objectLocator, String selectKey, String selectData) throws IOException, SQLException {
		try {
			String presskey = data.get("Key");
			if (presskey.equalsIgnoreCase("Back")) {
				aDriver.pressKeyCode(AndroidKeyCode.BACK);
				logInfo("INFO ", "mobilePresskey", "Successfully pressed the back key");
			} else if (presskey.equalsIgnoreCase("Enter")) {
				aDriver.pressKeyCode(AndroidKeyCode.ENTER);
				logInfo("INFO ", "mobilePresskey", "Successfully pressed the enter key");
			} else if (presskey.equalsIgnoreCase("Home")) {
				aDriver.pressKeyCode(AndroidKeyCode.HOME);
				logInfo("INFO ", "mobilePresskey", "Successfully pressed the home key");
			}
			key.updateDbTestResults(Constants.PASS, "mobilePresskey");
		} catch (Exception e) {
			e.printStackTrace();
			logInfo("FATAl", "mobilePresskey", "Error while pressing the key" + e.getMessage());
			key.updateDbTestResults(Constants.FATAL, "mobilePresskey " + e.getMessage());
		}
	}
	
	//*********************************************mobileElementisEnabled************************************************
	
	public boolean mobileElementisEnabled(Hashtable <String, String> data, Properties suiteProp, String objectLocator, String selectKey, String selectData) {
		boolean element = false;
		try {
			element = getElement(objectLocator, suiteProp).isEnabled();
			if (element == true) {
				System.out.println("The element is enabled: " + element);
				logger.info("Element is enabled");
			} else {
				System.out.println("The element is disabled: " + element);
				logger.info("Element is disabled");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logInfo("FATAl", "mobileElementisEnabled", "Error while checking the element is enabled or no" + e.getMessage());
		}
		return element;
	}
	
	// *********************************************mobileOpenNotification************************************************

	public void mobileOpenNotification(Hashtable <String, String> data, Properties suiteProp, String objectLocator, String selectKey, String selectData) throws IOException, SQLException {
		try {
			aDriver.openNotifications();
			logger.info("Successfully opened the notification");
			key.updateDbTestResults(Constants.PASS, "mobileOpenNotification");
		} catch (Exception e) {
			e.printStackTrace();
			logInfo("FATAl", "mobileOpenNotification", "Error while opening the notification" + e.getMessage());
			key.updateDbTestResults(Constants.FATAL, "mobileOpenNotification " + e.getMessage());
		}
	}
	
	// *********************************************mobileValidateNotification************************************************

	public void mobileValidateNotification(Hashtable <String, String> data, Properties suiteProp, String objectLocator, String selectKey, String selectData) throws IOException, SQLException {
		try {
			mobileOpenNotification(data, suiteProp, objectLocator, selectKey, selectData);
			getMobileElements(objectLocator, suiteProp);
			List<MobileElement> allnotifications = getMobileElements(objectLocator, suiteProp);
			for (WebElement webElement : allnotifications) {
				System.out.println(webElement.getText());
				if (webElement.getText().contains("something")) {
					System.out.println("Notification found");
					logger.info("Successfully validated the notification");
					key.updateDbTestResults(Constants.PASS, "mobileValidateNotification");
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logInfo("FATAl", "mobileValidateNotification", "Error while validating the notification" + e.getMessage());
			key.updateDbTestResults(Constants.FATAL, "mobileValidateNotification " + e.getMessage());
		}
	}
	
	// *********************************************mobileCloseApp************************************************

	public void mobileCloseApp(Hashtable <String, String> data, Properties suiteProp, String objectLocator, String selectKey, String selectData) throws IOException, SQLException {
		try {
			aDriver.closeApp();
			logger.info("Successfully closed the app");
			key.updateDbTestResults(Constants.PASS, "mobileCloseApp");
		} catch (Exception e) {
			e.printStackTrace();
			logInfo("FATAl", "mobileCloseApp", "Error while closing the app" + e.getMessage());
			key.updateDbTestResults(Constants.FATAL, "mobileCloseApp " + e.getMessage());
		}
	}
	
	// *********************************************mobileResetApp************************************************

	public void mobileResetApp(Hashtable <String, String> data, Properties suiteProp, String objectLocator, String selectKey, String selectData) throws IOException, SQLException {
		try {
			aDriver.resetApp();
			logger.info("Successfully reset the app");
			key.updateDbTestResults(Constants.PASS, "mobileResetApp");
		} catch (Exception e) {
			e.printStackTrace();
			logInfo("FATAl", "mobileResetApp", "Error while resetting the app" + e.getMessage());
			key.updateDbTestResults(Constants.FATAL, "mobileResetApp " + e.getMessage());
		}
	}
	
	// *********************************************mobileRemoveApp************************************************

	public void mobileRemoveApp(Hashtable <String, String> data, Properties suiteProp, String objectLocator, String selectKey, String selectData) throws IOException, SQLException {
		try {
			String packagename = data.get("Package Name");
			aDriver.removeApp(packagename);
			logger.info("Successfully removed the app");
			key.updateDbTestResults(Constants.PASS, "mobileRemoveApp");
		} catch (Exception e) {
			e.printStackTrace();
			logInfo("FATAl", "mobileRemoveApp", "Error while removing the app" + e.getMessage());
			key.updateDbTestResults(Constants.FATAL, "mobileRemoveApp " + e.getMessage());
		}
	}
	
	// *********************************************mobileElementisChecked************************************************

	public boolean mobileElementisChecked(Hashtable <String, String> data, Properties suiteProp, String objectLocator, String selectKey, String selectData) {
		boolean element = false;
		try {
			element = getElement(objectLocator, suiteProp).isSelected();
			if (element == true) {
				logger.info("Element is enabled");
			} else {
				logger.info("Element is disabled");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logInfo("FATAl", "mobileElementisChecked","Error while checking the element is selected or no" + e.getMessage());
		}
		return element;
	}

	// *********************************************mobileLock************************************************

	public void mobileLock(Hashtable <String, String> data, Properties suiteProp, String objectLocator, String selectKey, String selectData) throws IOException, SQLException {
		try {
			aDriver.lockDevice();
			logger.info("Successfully locked the device");
			key.updateDbTestResults(Constants.PASS, "mobileLock");
		} catch (Exception e) {
			e.printStackTrace();
			logInfo("FATAl", "mobileLock", "Error while locking the device" + e.getMessage());
			key.updateDbTestResults(Constants.FATAL, "mobileLock " + e.getMessage());
		}
	}
	
	// *********************************************mobileExplicitWait************************************************
	
	public void mobileExplicitWait(String locatorkey, String ExpectedCondition, int time, Properties suiteProp) throws IOException, SQLException {
		try {
			FluentWait<WebDriver> wait = new WebDriverWait(aDriver, time);
			if (ExpectedCondition.equalsIgnoreCase("CLICKABLE")) {
				wait.until(ExpectedConditions.elementToBeClickable(getElement(locatorkey, suiteProp)));
		    } else if (ExpectedCondition.equalsIgnoreCase("VISIBLE")) {
				wait.until(ExpectedConditions.visibilityOf(getElement(locatorkey, suiteProp)));
			} else if (ExpectedCondition.equalsIgnoreCase("INVISIBLE")) {
				wait.until(ExpectedConditions.invisibilityOf(getElement(locatorkey, suiteProp)));
			} else if (ExpectedCondition.equalsIgnoreCase("ALERT")) {
				wait.until(ExpectedConditions.alertIsPresent());
			} else if (ExpectedCondition.equalsIgnoreCase("PRESENCE")) {
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(suiteProp.getProperty(locatorkey))));
			}
			key.updateDbTestResults(Constants.PASS, "mobileExplicitWait");
		} catch (Exception e) {
			e.printStackTrace();
			logInfo("FATAL", "mobileExplicitWait", "Error while waiting for the expected condition" + e.getMessage());
			key.updateDbTestResults(Constants.FATAL, "mobileExplicitWait " + e.getMessage());
		}
	}
	
	// *********************************************mobileRemoveApp************************************************

		public void mobileSwipeRightToElement(Hashtable <String, String> data, Properties suiteProp, String objectLocator, String selectKey, String selectData) throws IOException, SQLException {
			try {
				String swipeFrom = data.get("swipe from");
				String swipeTo = data.get("swipe to");
				MobileElement swipeOne = getMobileElement(swipeFrom, suiteProp);
				MobileElement swipeTwo = getMobileElement(swipeTo, suiteProp);
				TouchAction action = new TouchAction<>(aDriver);
				action.longPress(longPressOptions().withElement(element(swipeOne)).withDuration(ofSeconds(2))).moveTo(element(swipeTwo)).release().perform();
				logger.info("Successfully swiped till element found");
				key.updateDbTestResults(Constants.PASS, "mobileSwipeRightToElement");
			} catch (Exception e) {
				e.printStackTrace();
				logInfo("FATAL", "mobileSwipeRightToElement", "Error while swiping" + e.getMessage());
				key.updateDbTestResults(Constants.FATAL, "mobileSwipeRightToElement " + e.getMessage());
			}
		}
		
		// *********************************************mobileLock************************************************

	public void mobileisCheckable(Hashtable <String, String> data, Properties suiteProp, String objectLocator, String selectKey, String selectData) throws IOException, SQLException {
		try {
			int count = aDriver.findElementsByAndroidUIAutomator("new UiSelector().checkable(true)").size();
			logger.info("Successfully obtained the size of Checkable");
			key.updateDbTestResults(Constants.PASS, "mobileisCheckable");
		} catch (Exception e) {
			e.printStackTrace();
			logInfo("FATAl", "mobileisCheckable", "Error while obtaining the size" + e.getMessage());
			key.updateDbTestResults(Constants.FATAL, "mobileisCheckable " + e.getMessage());
		}
	}
		
		// *********************************************mobileLock************************************************

	public void mobileisFocusable(Hashtable <String, String> data, Properties suiteProp, String objectLocator, String selectKey, String selectData) throws IOException, SQLException {
		try {
			int count = aDriver.findElementsByAndroidUIAutomator("new UiSelector().focusable(true)").size();
			logger.info("Successfully obtained the size of Focusable");
			key.updateDbTestResults(Constants.PASS, "mobileisFocusable");
		} catch (Exception e) {
			e.printStackTrace();
			logInfo("FATAl", "mobileisFocusable", "Error while obtaining the size" + e.getMessage());
			key.updateDbTestResults(Constants.FATAL, "mobileisFocusable " + e.getMessage());
		}
	}
		
		// *********************************************mobileLock************************************************

	public void mobileisFocused(Hashtable <String, String> data, Properties suiteProp, String objectLocator, String selectKey, String selectData) throws IOException, SQLException {
		try {
			int count = aDriver.findElementsByAndroidUIAutomator("new UiSelector().focusable(true)").size();
			logger.info("Successfully obtained the size of Focused");
			key.updateDbTestResults(Constants.PASS, "mobileisFocused");
		} catch (Exception e) {
			e.printStackTrace();
			logInfo("FATAl", "mobileisFocused", "Error while obtaining the size" + e.getMessage());
			key.updateDbTestResults(Constants.FATAL, "mobileisFocused " + e.getMessage());
		}
	}
}
