package com.deloitte.testng_hybrid_framework;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileBrowserType;
import io.appium.java_client.remote.MobileCapabilityType;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Listeners;

import com.deloitte.testng_hybrid_framework.captureObjects.CaptureObjects;
import com.deloitte.testng_hybrid_framework.captureObjects.RepositoryGenerator;
import com.deloitte.testng_hybrid_framework.util.Constants;
import com.deloitte.testng_hybrid_framework.util.CurrentDateAndMonth;
import com.deloitte.testng_hybrid_framework.util.ExtentManager;
import com.deloitte.testng_hybrid_framework.util.Listener;
import com.deloitte.testng_hybrid_framework.util.dbConnect2;
import com.rationaleemotions.GridApiAssistant;
import com.rationaleemotions.pojos.Host;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
@Listeners(Listener.class)

public class GenericKeywords {
	public WebDriver driver;
	public io.appium.java_client.android.AndroidDriver<AndroidElement> adriver;
	public IOSDriver idriver;
	public Properties prop;
	public String browserType;
	public String PlatformName;
	public String ip;
	public String hubPort = "4444";
	public DesiredCapabilities cap = null;
	ExtentTest test;
	public Boolean failflag = false;
	public Boolean fatalflag = false;
	private static final Logger logger = Logger.getLogger(GenericKeywords.class.getName());
	public GenericKeywords(ExtentTest test) {
		this.test=test;
		prop = new Properties();
		try{
			FileInputStream fs;
			fs = new FileInputStream(System.getProperty("user.dir")+"//src//test//resources//project.properties");
			prop.load(fs);
			logInfo("INFO", "Test = " + test + " Loading project properties file");
		}
		catch (IOException e){
			e.printStackTrace();
			logInfo("FATAL", "Test = " + test + " Could not load the project properties file");
		}
	}
	
//********************************GENERIC FUNCTIONS*****************************************************	
//**********************Open Browser Method*************************************************************
	public String openBrowser(String browserType, String platformType) throws IOException, MalformedURLException{
		try{
			if(prop.getProperty("grid").equals("Y")){
				logInfo("INFO", "Test = " + test + " Opening " + browserType + " Remote Browser");
				cap = new DesiredCapabilities();
				if(browserType.toUpperCase().equals("MOZILLA")){
					if(platformType.toUpperCase().equals("WINDOWS")){
						cap = DesiredCapabilities.firefox();
						cap.setBrowserName("firefox");
						cap.setJavascriptEnabled(true);
						cap.setPlatform(org.openqa.selenium.Platform.WINDOWS);
					} else if(platformType.toUpperCase().equals("LINUX")){
						cap = DesiredCapabilities.firefox();
						cap.setBrowserName("firefox");
						cap.setJavascriptEnabled(true);
						cap.setPlatform(org.openqa.selenium.Platform.LINUX);
					} else if (platformType.toUpperCase().equals("MAC")){
						cap = DesiredCapabilities.firefox();
						cap.setBrowserName("firefox");
						cap.setJavascriptEnabled(true);
						cap.setPlatform(org.openqa.selenium.Platform.MAC);	
					}
				}
				else if (browserType.toUpperCase().equals("CHROME")){
					if(platformType.toUpperCase().equals("WINDOWS")){
					cap = DesiredCapabilities.chrome();
					cap.setBrowserName("chrome");
					cap.setPlatform(org.openqa.selenium.Platform.WINDOWS);
					}else if(platformType.toUpperCase().equals("LINUX")){
					cap = DesiredCapabilities.chrome();
					cap.setBrowserName("chrome");
					cap.setPlatform(org.openqa.selenium.Platform.LINUX);
					}else if(platformType.toUpperCase().equals("MAC")){
					cap = DesiredCapabilities.chrome();
					cap.setBrowserName("chrome");
					cap.setPlatform(org.openqa.selenium.Platform.MAC);
					}
				}else if(browserType.toUpperCase().equals("IE")){
					cap = DesiredCapabilities.internetExplorer();
					cap.setBrowserName("ie");
					cap.setPlatform(org.openqa.selenium.Platform.WINDOWS);
				}else if(browserType.toUpperCase().equals("EDGE")){
					cap = DesiredCapabilities.edge();
					cap.setBrowserName("edge");
					cap.setPlatform(org.openqa.selenium.Platform.WINDOWS);
				}
				ip=prop.getProperty("RemoteWebDriver");
				driver = new RemoteWebDriver(new URL("http://" + ip + ":"+hubPort+"/wd/hub"), cap);		
				logInfo("INFO", "Node IP & Port = " + getNode(ip, hubPort));
			}
			else{		
				logInfo("INFO", "Test = " + test + " Opening " + browserType + " local Browser");
				if(browserType.toUpperCase().equals("MOZILLA")){
					System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "//drivers//geckodriver.exe");
					driver = new FirefoxDriver();
				}else if(browserType.toUpperCase().equals("CHROME")){
					System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//drivers//chromedriver.exe");
					driver =  new ChromeDriver();
				}else if(browserType.toUpperCase().equals("EDGE")){
					System.setProperty("webdriver.Edge.driver", System.getProperty("user.dir") + "//drivers//MicrosoftWebDriver.exe");
					driver =  new EdgeDriver();
				}else if(browserType.toUpperCase().equals("IE")){
					System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "//drivers//IEDriverServer.exe");
					driver =  new InternetExplorerDriver();
				}
			}
		PlatformName = platformType;
		this.browserType = browserType;
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//driver.manage().window().maximize();
		return Constants.PASS;}
		catch(MalformedURLException e){
			e.printStackTrace();
			logInfo("FATAL", "Test = " + test + " Could not open browser / application: " + browserType);
			return Constants.FATAL  + " " +  e.getMessage();
		}
	}
	
//*********************************Open Mobile Browser********************************************
	public String openMobBrowser(String PlatformName, String DeviceName, String PlatformVersion, String browserType) throws MalformedURLException{
		try{
			logInfo("INFO", "Test = " + test + " Opening Mobile Browser: " + browserType);
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
		        //adriver = new io.appium.java_client.android.AndroidDriver<AndroidElement>(new URL("http://192.168.0.13:4723/wd/hub"), cap);
		        driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
			} else if((PlatformName.toUpperCase().equals("IOS"))){
				driver = new IOSDriver(new URL("http://" + ip + ":4723/wd/hub"), cap);
		        //idriver = new IOSDriver(new URL("http://192.168.0.13:4723/wd/hub"), cap);
		        driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
			}
			this.browserType = browserType;
	        return Constants.PASS;
		}catch(MalformedURLException m){
			m.printStackTrace();
			return Constants.FATAL + " " +  m.getMessage();
		}
	}
	
//***************************//*********************************Open Mobile Application********************************************
	public String openMobApp(String PlatformName, String DeviceName,String App, String AppPackage, String AppActivity, String PlatformVersion) throws MalformedURLException{
		try{
			logInfo("INFO", "Test = " + test + " Opening Mobile App");
			cap = new DesiredCapabilities();
			cap.setCapability(MobileCapabilityType.PLATFORM_NAME, PlatformName);
			if(PlatformVersion != null)
				cap.setCapability(MobileCapabilityType.VERSION, PlatformVersion);
			cap.setCapability(MobileCapabilityType.DEVICE_NAME, DeviceName);
			if(App.isEmpty()){
				cap.setCapability(MobileCapabilityType.APP_PACKAGE, AppPackage);
				cap.setCapability(MobileCapabilityType.APP_ACTIVITY, AppActivity);
			} else{
				File application = new File(System.getProperty("user.dir") + "\\src\\test\\resources\\APK\\" + App);
				cap.setCapability(MobileCapabilityType.APP, application.getAbsolutePath());
			}
			String ip=prop.getProperty("AppiumDriver");
			if(PlatformName.toUpperCase().equals("ANDROID")){
				//driver = new AndroidDriver<WebElement>(new URL("http://10.27.56.35:4723/wd/hub"), cap);
		         driver = new io.appium.java_client.android.AndroidDriver<AndroidElement>(new URL("http://" + ip + ":4723/wd/hub"), cap);
		         driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
			}else if((PlatformName.toUpperCase().equals("IOS"))){
				 driver = new IOSDriver(new URL("http://" + ip + ":4723/wd/hub"), cap);
		         driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
			}
	        return Constants.PASS;
		}catch(MalformedURLException m){
			m.printStackTrace();
			return Constants.FATAL + " " +  m.getMessage();
		}
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
			logInfo("FATAL", " " + e.getMessage());
			e.printStackTrace();
			return "Node Failed";
		}
		
	}
	
//*********************************Navigate to URL Method***********************************************
	public String navigate(String urlkey){
		logInfo("INFO", "Test = " + test + " Navigating to " + prop.getProperty(urlkey));
		try{
		driver.navigate().to(prop.getProperty(urlkey));
		return Constants.PASS;
		}catch(Exception e){
			e.printStackTrace();
			logInfo("FATAL", "Test = " + test + " Could not navigate to " +  prop.getProperty(urlkey));
			return Constants.FATAL  + " " +  e.getMessage();
		}
	}
	
//*********************************Click on Element Method**********************************************
	public String click(String locatorkey){
		logInfo("INFO", "Test = " + test + " Clicking on " + locatorkey );
		try{
			WebElement e = getElement(locatorkey);
			e.click();
			
			return Constants.PASS;
		}catch(Exception e){
			e.printStackTrace();
			logInfo("FATAL", "Test = " + test + " Could not click on " + locatorkey);
			return Constants.FATAL + " " + e.getMessage();
		}
	}
	
//*********************************Input value in Element Method****************************************	
	public String input(String locatorkey, String data){
		logInfo("INFO", "Test = " + test + " Inputting value in field = " + locatorkey );
		try{
			WebElement e = getElement(locatorkey);
			e.sendKeys(data);
			
			return Constants.PASS;
		}catch(Exception e){
			e.printStackTrace();
			logInfo("FATAL", "Test = " + test + " Could not input value in field = " + locatorkey);
			return Constants.FATAL + " " + e.getMessage();
		}
	}
	
//**********************************Select Value in Drop Down Method************************************
	public String selectDropdown(String locatorkey, String key, String data){
		logInfo("INFO", "Test = " + test + " Selecting " + data + " in " + locatorkey );
		Select s =  null;
		try{
			WebElement m = getElement(locatorkey);
			s = new Select(m);
			if(key.endsWith("Value"))
				s.selectByValue(data);
			else if(key.endsWith("Index"))
				s.selectByIndex(Integer.parseInt(data));
			else if(key.endsWith("VisibleText"))
				s.selectByVisibleText(data);
			return Constants.PASS;
		}catch(Exception exception){
			exception.printStackTrace();
			logInfo("FATAL", "Test = " + test + " Could not select " + data + " in " + locatorkey);
			return Constants.FATAL + " Not able to select " + data + "for" + locatorkey;
		}	
	}
		
//*********************************Select value in Check Box Method*************************************
	public String selectCheckbox(String locatorkey){
		logInfo("INFO", "Test = " + test + " Selecting Checkbox " + locatorkey );
		boolean m = false;
		WebElement e = getElement(locatorkey);
		try{
		    m = e.isSelected();
		    if(m)
		    	return Constants.PASS;
		    else 
		    	return Constants.FAIL + m + " Checkbox Not selected " + locatorkey;
		}catch(Exception exception){
			exception.printStackTrace();
			logInfo("FATAL", "Test = " + test + " Not able to select checkbox " + locatorkey);
			return Constants.FATAL + " Not able to select " + locatorkey;
		}
	}
	
//****************************MouseOver Method**********************************************************
	public String mouseOver(String locatorkey){
		logInfo("INFO", "Test = " + test + " Mouseover on " + locatorkey );
		try{
			WebElement m = getElement(locatorkey);
		    Actions action = new Actions(driver);
		    action.moveToElement(m).build().perform();
			return Constants.PASS;
		}catch(Exception exception){
			exception.printStackTrace();
			logInfo("FATAL", "Test = " + test + " failure in finding mouseOverKey " + locatorkey );
			return Constants.FATAL + " failure in finding mouseOverKey " + locatorkey;
		}
	}
	
//*********************Select Radio Button Method*******************************************************
	public String selectRadioButton(String locatorkey,String value) {
		logInfo("INFO", "Test = " + test + " Selecting radio button " + locatorkey );
		try{
			List<WebElement> e = getElements(locatorkey);	
			for(WebElement ele : e) {
				if((ele.getAttribute("value").equalsIgnoreCase(value)) && (!ele.isSelected())) {
					ele.click();
					logInfo("INFO", "Test = " + test + " Selected radio button " + ele.getAttribute("value"));
					break;
				}
			}
			return Constants.PASS;
		}catch(Exception exception){
			exception.printStackTrace();
			logInfo("FATAL", "Test = " + test + " failure in finding RadioButton " + locatorkey + " OR " + value );
			return Constants.FATAL + " failure in finding RadioButton " + locatorkey + " " +  value;
		}
	}
		
//********************************VALIDATION FUNCTIONS**************************************************
//******************************Verify Expected with Element Text Method********************************
	public String verifyText(String locatorkey, String expectedText){
		logInfo("INFO", "Test = " + test + " Verifying " + expectedText + " with " + locatorkey + ".text" );
		try{ 
			WebElement e = getElement(locatorkey);
			String actualText = e.getText();
			if(actualText.equals(expectedText)){
				logInfo("PASS","Test = " + test + " Actual Result is equal to Expected ResultExpected = " + expectedText + " & Actual Result = " + actualText);
				return Constants.PASS;
			}
			else {
				logInfo("FAIL","Test = " + test + " Actual Result not equal to Expected Result, Expected = " + expectedText + " & Actual Result = " + actualText);
				return Constants.FAIL + "Actual Result not equal to Expected Result, Expected = " + expectedText + " & Actual Result = " + actualText;
			}
		}catch(Exception exception){
			exception.printStackTrace();
			logInfo("FATAL", "Test = " + test + " failure in finding " + locatorkey);
			return Constants.FATAL + " failure in finding " + locatorkey ;
		}
	}

//******************************Verify Element Present Method*******************************************
	public String verifyElementPresent(String locatorkey){
		logInfo("INFO", "Test = " + test + " Verifying if " + locatorkey + " element is present" );
		boolean result = isElementPresent(locatorkey);
		try{
			if(result){
				logInfo("PASS","Test = " + test + " element: " + locatorkey + " is present");
				return Constants.PASS;
			}
			else{
				logInfo("FAIL","Test = " + test + " element: " + locatorkey + " is not present");
				return Constants.FAIL + " Not able not find Element - " + locatorkey + ".";
			}
		}catch(Exception e){
			e.printStackTrace();
			logInfo("FATAL","Test = " + test + " failure in finding " + locatorkey);
			return Constants.FATAL + " failure in finding " + locatorkey + e.getMessage();
		}
	}
	
//******************************Verify Element Not Present Method***************************************	
	public String verifyElementNotPresent(String locatorkey){
		logInfo("INFO", "Test = " + test + " Verifying if " + locatorkey + " element is not present" );
		boolean result = isElementPresent(locatorkey);
		try{
			if(!result){
				logInfo("PASS","Test = " + test + " element: " + locatorkey + " is not present");
				return Constants.PASS;
			}
			else{
				logInfo("FAIL","Test = " + test + " element: " + locatorkey + " is present");
				return Constants.FAIL + " Was able to find Element - " + locatorkey + ".";
			}
				
		}catch(Exception e){
			e.printStackTrace();
			logInfo("FATAL","Test = " + test + " failure in finding " + locatorkey);
			return Constants.FATAL + " failure in finding " + e.getMessage();
		}
	}
	
//***************************************UTILITY FUNCTIONS**********************************************
//*********************************Get Element Method***************************************************
	public WebElement getElement(String locatorkey){
		logInfo("INFO", "Test = " + test + " Getting Element: " + locatorkey);
		WebElement e = null;
		try{
			if(locatorkey.endsWith("id")){
				e=driver.findElement(By.id(prop.getProperty(locatorkey)));
			}
			else if(locatorkey.endsWith("name")){
				e=driver.findElement(By.name(prop.getProperty(locatorkey)));
			}
			else if(locatorkey.endsWith("xpath")){
				e=driver.findElement(By.xpath(prop.getProperty(locatorkey)));
			}
			else if(locatorkey.endsWith("cssSelector")){
				e=driver.findElement(By.cssSelector(prop.getProperty(locatorkey)));
			}
		}catch(Exception exception){
			exception.printStackTrace();
			logInfo("FATAL", "Test = " + test + " failure in finding " + locatorkey);
		}
		return e;
	}
	
//**********************************Get Elements Method*************************************************
	public List<WebElement> getElements(String locatorkey){
		logInfo("INFO", "Test = " + test + " Getting Elements: " + locatorkey);
		List<WebElement> e = null;
		try{
			if(locatorkey.endsWith("id")){
				e=driver.findElements(By.id(prop.getProperty(locatorkey)));
			}
			else if(locatorkey.endsWith("name")){
				e=driver.findElements(By.name(prop.getProperty(locatorkey)));
			}
			else if(locatorkey.endsWith("xpath")){
				e=driver.findElements(By.xpath(prop.getProperty(locatorkey)));
			}
			else if(locatorkey.endsWith("cssSelector")){
				e=driver.findElements(By.cssSelector(prop.getProperty(locatorkey)));
			}
		}catch(Exception exception){
			exception.printStackTrace();
			logInfo("FATAL", "Test = " + test + " failure in finding " + locatorkey);
		}
		return e;
	}
	
//************************************Is Element Present Method*****************************************
	public boolean isElementPresent(String locatorkey){
		logInfo("INFO", "Test = " + test + " Verifying if element is present: " + locatorkey);
		List <WebElement> e = null;
		try{
			if(locatorkey.endsWith("id")){
				e=driver.findElements(By.id(prop.getProperty(locatorkey)));
			}
			else if(locatorkey.endsWith("name")){
				e=driver.findElements(By.name(prop.getProperty(locatorkey)));
			}
			else if(locatorkey.endsWith("xpath")){
				e=driver.findElements(By.xpath(prop.getProperty(locatorkey)));
			}
			else if(locatorkey.endsWith("cssSelector")){
				e=driver.findElements(By.cssSelector(prop.getProperty(locatorkey)));
			}
			if(e.size()==0)
				return false;
			else
				return true;
		}catch(Exception exception){
			exception.printStackTrace();
			logInfo("FATAL", "Test = " + test + " failure in finding " + locatorkey);
			return false;
		}
	}
	
//**********************************Get Element Size Method*********************************************
	public int getElementsSize(String locatorkey){
		logInfo("INFO"," Inputting data in " + prop.getProperty(locatorkey));
		int s;
		List<WebElement> e = getElements(locatorkey);
		try{
			s = e.size();			
		}catch(Exception exception){
			logInfo("FATAL", "Not able to find size of Element " + locatorkey);
			s = 0;
		}
		return s;
	}
	
//*************************************Window Handler Method********************************************
	public String windowhandle(String locatorkey){
		logInfo("INFO"," Window handler to switch to child window");
		try{			
			Set<String>ids=driver.getWindowHandles();
			Iterator<String> itr = ids.iterator();
			String parentwin = itr.next();
			String childwin = itr.next();
			driver.switchTo().window(childwin);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			System.out.println("switched to window with title ->"+driver.getTitle());
			return Constants.PASS;
		}catch(Exception exception){
			/*Assert.fail("failure in switching windows");*/
			exception.printStackTrace();
			logInfo("FATAL", "Not able to find child window " + locatorkey);
			return Constants.FATAL + "Not able to find child window " + locatorkey;		
		}
	}
	
//*************************************Random Number & String Functions*********************************
//*************************************Get Random Number Method*****************************************
	public int getRandomNumber(){
		logInfo("INFO", "Generating Random Number");
		int aNumber = 0;
		try{
		aNumber = (int)((Math.random() * 90000000)+10000000);
		}catch (Exception exception){
			exception.printStackTrace();
			logInfo("FATAL", "not able to get Random Number");
		}
		return aNumber;
	}

//*************************************Get Random Number Min - Max Method*******************************
	public int getRandomNumber(int min, int max) {
		logInfo("INFO", "Generating Random Number");
		Random rand = new Random();
		int randomNum = 0;
		try{
	    randomNum = rand.nextInt((max - min) + 1) + min;
		}
		catch(Exception exception){
			exception.printStackTrace();
			logInfo("FATAL", "not able to get Random Number min & max");
		}
		return randomNum;
	}

	//*************************************Get Random String Method*************************************	
	public String getRandomString(){
		logInfo("INFO", "Generating Random String");
		String randomString = null;
		try{
			char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
			StringBuilder sb = new StringBuilder();
			Random random = new Random();
			for (int i = 0; i < 5; i++) {
				char c = chars[random.nextInt(chars.length)];
				sb.append(c);
			}
			randomString = sb.toString();
		}catch(Exception exception){
			exception.printStackTrace();
			logInfo("FATAL", "not able to get Random String");
		}
		return randomString;
	}

//*******************************************Close Browser Method***************************************
	public String closeBrowser() {
		logInfo("INFO", "Closing Browser");
		try{
			if(driver != null){
				driver.quit();
				return Constants.PASS;
			}
			else if(adriver != null){
				adriver.quit();
				return Constants.PASS;
			}
			else 
				return Constants.FAIL; 
		}
		catch(Exception e){
			e.printStackTrace();
			logInfo("FATAL", "not able to close browser");
			return Constants.FATAL + e.getMessage();
		}
	}

// Send Keys
	public String sendEnterKey(String locatorkey, Keys key){
		try{
			logInfo("INFO", "Sending Key - " + locatorkey);
			WebElement e = getElement(locatorkey);
			e.sendKeys(key);
			return Constants.PASS;
		}catch(Exception e){
			e.printStackTrace();
			logInfo("FATAL", "not able send key - " + locatorkey);
			return Constants.FATAL + e.getMessage();
		}
	}
	
	
//***************************************Thread.Sleep Wait Method***************************************
	public String wait(String timeout) {
		logInfo("INFO", "waiting");
		try{
			Thread.sleep(Integer.parseInt(timeout));
			return Constants.PASS;
		}catch(NumberFormatException n){
			n.printStackTrace();
			logInfo("FATAL", "exception while waiting");
			return Constants.FATAL + n.getMessage();
		}catch(InterruptedException i){
			i.printStackTrace();
			logInfo("FATAL", "exception while waiting");
			return Constants.FATAL + i.getMessage();
		}
	}
	
//************************************explicit wait functions******************************************
	
	public String waitExplicit(WebDriver driver, int time, String locatorkey, String ExpectedCondition){
		Boolean flag = true;
			WebDriverWait wait = new WebDriverWait(driver, 10);
			if(ExpectedCondition.equals("clickable")){
				WebElement e = wait.until(ExpectedConditions.elementToBeClickable(getElement(locatorkey)));
				flag = false;
			} else if(ExpectedCondition.equals("visible")){
				WebElement e = wait.until(ExpectedConditions.visibilityOf(getElement(locatorkey)));
				flag = false;
			} else if(ExpectedCondition.equals("invisible")){
				wait.until(ExpectedConditions.invisibilityOf(getElement(locatorkey)));
				flag = false;
			} else if(ExpectedCondition.equals("alert")){
				wait.until(ExpectedConditions.alertIsPresent());
				flag = false;
			} else if(ExpectedCondition.equals("presence")){
				wait.until(ExpectedConditions.presenceOfElementLocated(By.id(locatorkey)));
				flag = false;
			}
			if (flag.equals(false))
				return Constants.PASS;
			else
				return Constants.FAIL + "wait Condition" + ExpectedCondition + "not met" ;
	}
	
//************************************LOGGING FUNCTIONS*************************************************
	public void logInfo(String logStatus, String details){
		if(logStatus.equals("INFO")){
			test.log(LogStatus.INFO, details);
			logger.info(logStatus + details);
		}
		else if(logStatus.equals("FAIL")){
			test.log(LogStatus.FAIL, details);
			logger.info(logStatus + details);
		}
		else if(logStatus.equals("FATAL")){
			test.log(LogStatus.FATAL, details);
			logger.fatal(logStatus + details);
		}
		else if(logStatus.equals("PASS")){
			test.log(LogStatus.PASS, details);
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
		logInfo("INFO", "taking screenshot");
		try{
		// decide name - time stamp
				String runName = ExtentManager.getRunName();
				Date d = new Date();
				String screenshotFile=d.toString().replace(":", "_").replace(" ","_")+".png";
				String path=Constants.SCREENSHOT_PATH+runName +"_" +testName +"_"+ screenshotFile;
				// take screenshot
				File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(srcFile, new File(path));
				// embed
				logInfo("INFO", "attaching screenshot to logs" + test.addScreenCapture(path));
		}catch(Exception exception){
			exception.printStackTrace();
			logInfo("FATAL", "exception while taking screenshot");
		}
	}
	
//**********************************Reporting Pass Method***********************************************
	public void reportPass(String testname) throws IOException{
		if(driver != null){
			if(prop.getProperty("screenshot").equals("Y"))
				takeScreenshot(testname);
		}
		logInfo("PASS", testname + " test passed");
	}
	
//*********************************Reporting Exception/Fatal Method*************************************
	public void reportException(String testname, String FailureMessage) throws IOException{
		if(driver != null){
			fatalflag=true;
			takeScreenshot(testname);
		}
		logInfo("FATAL", testname + " The test failed due to " + FailureMessage );
	}
	
//*********************************Reporting Fail Method************************************************
	public void reportDefect(String testname, String defectMessage) throws IOException{
		if(driver != null){
			failflag=true;
			takeScreenshot(testname);
		}
		logInfo("FAIL", testname + " Defect Found " + defectMessage);
	}
	
//*************************************DATABASE FUNCTIONS***********************************************
//*************************************Insert into Detail Table*****************************************
	@SuppressWarnings("static-access")
	public void insertDBDetailRecord(String suitename, String tcid, String keyword, String teststatus, String testrunkey, String createdby) throws IOException, SQLException{
		try{
			String Node = "";
			logInfo("INFO", "Inserting record in DB Detail table");
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
			logInfo("FATAL", "Not able to connect to DB or Error while inserting records in Detail Table");
		}
	}
	
	public String selectDBDetailRecord(String suitename, String testrunid, String Status, String keyword){
		logInfo("INFO", "Selecting record from DB Detail table with run name");
		String testcount = "";
		try{
			testcount = "select count(test_status) AS test_count from test_detail where test_suite = '" + suitename + "'" + "\r\n" + 
				"and test_status = '" + Status + "' and test_keyword = '" + keyword + "' and test_runkey = " + "'" + testrunid + "'" + ";";
		System.out.println(testcount);
		}catch(Exception e){
			e.printStackTrace();
			logInfo("FATAL", "Not able to connect to DB or Error while selecting records in Detail Table");
		}
		return testcount;
	}
	
	public String selectDBDetailRecord(String suitename, String testrunid, String testcase, String Status, String keyword){
		logInfo("INFO", "Selecting record from DB Detail table with test case name");
		String stepcount = "";
		try{
			stepcount = "select count(test_status) AS test_count from test_detail where test_suite = '" + suitename + "'" + "\r\n" + 
				"and test_status = '" + Status + "' and test_keyword != '" + keyword + "' and test_runkey = " + "'" + testrunid + "'" + "and test_case = " + "'" + testcase + "'" + ";";
			System.out.println(stepcount);
		}catch(Exception e){
			e.printStackTrace();
			logInfo("FATAL", "Not able to connect to DB or Error while selecting records in Detail Table");
		}
		return stepcount;
	}
	
	
	public void insertDBSummaryRecord(String testrunid, String suitename, String createdBy) throws NumberFormatException, SQLException{
		try{
			logInfo("INFO", "Inserting record from test_suite_summary table");
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
			logInfo("FATAL", "Not able to connect to DB or Error while inserting records in test_run_summary table");
		}
	}
	
	public void insertDBSummaryRecord(String testrunid, String suitename, String testcase, String createdBy) throws NumberFormatException, SQLException{
		try{
			logInfo("INFO", "Inserting record from test_case_summary table");
			dbConnect2 dbrep = new dbConnect2();
			Timestamp TimeStamp = CurrentDateAndMonth.timeStampVal();
			String insmodel = "INSERT INTO test_case_summary (test_runid, test_suite, test_case, test_steps_planned, test_steps_passed, test_steps_failed, test_steps_skipped, test_steps_exception, created_by, created_at, modified_by, modified_at)\r\n" + 
				"VALUES (";
			String comma = ",";
			String insmodel2 = ");";
			int passcnt = Integer.parseInt(dbrep.sqlcount(selectDBDetailRecord(suitename, testrunid, testcase, "PASS", "n/a")));
			int failcnt = Integer.parseInt(dbrep.sqlcount(selectDBDetailRecord(suitename, testrunid, testcase, "FAIL", "n/a")));
			int skipcnt = Integer.parseInt(dbrep.sqlcount(selectDBDetailRecord(suitename, testrunid, testcase, "SKIPPED", "n/a")));
			int fatalcnt = Integer.parseInt(dbrep.sqlcount(selectDBDetailRecord(suitename, testrunid, testcase, "FATAL", "n/a")));
		
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
			logInfo("FATAL", "Not able to connect to DB or Error while inserting records in test_case_summary table");
		}
	}
	
//***************************************THROW AWAY FUNCTIONS*******************************************
	public String verifyCalc(int data,String locatorkey){
		logInfo("INFO", "verify calculation method");
		int calc = 2*6;		
		try {
			if(calc == data )
				return Constants.PASS;
			else{
				return Constants.FAIL + " Calculation is wrong";
			} 
		}catch (Exception e) {
				// TODO: handle exception
			logInfo("FATAL", "Not able to find " + locatorkey);
			return Constants.FATAL + "Not able to find " + locatorkey;
		}
	}
}
