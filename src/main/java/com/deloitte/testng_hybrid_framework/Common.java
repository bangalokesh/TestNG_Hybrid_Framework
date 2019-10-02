package com.deloitte.testng_hybrid_framework;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.exec.ExecuteWatchdog;

import com.deloitte.testng_hybrid_framework.util.dbConnect2;
import com.relevantcodes.extentreports.ExtentReports;

public class Common {
	
	public static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Common.class.getName());
	
	
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
	//**********Random Numbers and String Methods*******************************
	
	public static double getRandomDouble(){
		logInfo("INFO", "Generating Random Double");
    	double random = new Random().nextDouble();
    	return random;
	}
	
	public static double getRandomDouble(int round){
		logInfo("INFO", "Generating Random Double");
    	double random = new Random().nextDouble();
    	double roundedNumber = getRound(random, round);
    	return roundedNumber;
	}
	
	public static double getRandomDouble(double min, double max, int round){
		logInfo("INFO", "Generating Random Double");
    	double random = new Random().nextDouble();
    	double number = min + (random * (max - min));
    	double roundedNumber = getRound(number, round);
    	return roundedNumber;
	}		 
	
	public static double getRound(double value, int numberOfDigitsAfterDecimalPoint) {
		logInfo("INFO", "Generating Random Double");
		BigDecimal bigDecimal = new BigDecimal(value);
	    bigDecimal = bigDecimal.setScale(numberOfDigitsAfterDecimalPoint,
	    BigDecimal.ROUND_HALF_UP);
	    return bigDecimal.doubleValue();
	}
	

	public static boolean getRandomBoolean() {
		logInfo("INFO", "Generating Random Boolean");
		return Math.random() < 0.21;
	}
	
	public static String getRandomDate(int minDateYear, int maxDateYear, boolean currentDateAllowed) {
		logInfo("INFO", "Generating Random Date");
		Calendar gc = new GregorianCalendar();
		int year = randBetween(minDateYear, maxDateYear);
	 	gc.set(Calendar.YEAR, year);
	    int dayOfYear = randBetween(01, gc.getActualMaximum(Calendar.DAY_OF_YEAR));
	    gc.set(Calendar.DAY_OF_YEAR, dayOfYear);
	    String findate = gc.get(Calendar.YEAR) + "-" + (gc.get(Calendar.MONTH) + 1) + "-" + gc.get(Calendar.DAY_OF_MONTH);
	    System.out.println("Random Generated date is->"+findate);
	    String curdte = getCurrentDate();
	    if(currentDateAllowed == false) {
	    	if(findate.equals(curdte)) {
		    	while(findate.equals(curdte)){
			    	 findate = gc.get(Calendar.YEAR) + "-" + (gc.get(Calendar.MONTH)+ 1) + "-" + gc.get(Calendar.DAY_OF_MONTH);
			    	 break;
			     }
		     }	
	    }
	    return findate;
	}
	
	public static int randBetween(int start, int end) {
		logInfo("INFO", "Generating Random integer");
        return start + (int)Math.round(Math.random() * (end - start));
    }
	
	public static String getRandomString(int length, boolean alphanumeric) {
		logInfo("INFO", "Generating Random String");
	    StringBuilder sb = new StringBuilder();
	    Random random = new Random();
	    String upperchar="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	    String lowerchar="abcdefghijklmnopqrstuvwxyz";
	    String num="1234567890";
	    String alphanum="!@#$%^&*()_+=-[] {}|;':?,.<>/";
	    String alphaChars = upperchar+lowerchar+num+alphanum;
	    String Chars = upperchar+lowerchar+alphanum;
	    if(alphanumeric==(true)) {
	    	for (int i = 0; i < length; i++) {
		        sb.append(alphaChars.charAt(random.nextInt(alphaChars.length())));
	    	}
	    }else {
	    	for (int i = 0; i < length; i++) {
		        sb.append(Chars.charAt(random.nextInt(Chars.length())));
	    	}
	    }

	    return sb.toString();
	}
	
	public static String getRandomString(int minLength, int maxLength, boolean alphanumeric) {
		logInfo("INFO", "Generating Random String");
	    StringBuilder sb = new StringBuilder();
	    Random random = new Random();
	    String upperchar="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	    String lowerchar="abcdefghijklmnopqrstuvwxyz";
	    String num="1234567890";
	    String alphanum="!@#$%^&*()_+=-[] {}|;':?,.<>/";
	    String alphaChars = upperchar+lowerchar+num+alphanum;
	    String Chars = upperchar+lowerchar+alphanum;
	    if(alphanumeric==(true)) {
	    	for (int i = minLength; i < maxLength; i++) {
		        sb.append(alphaChars.charAt(random.nextInt(alphaChars.length())));
	    	}
	    }else {
	    	for (int i = minLength; i < maxLength; i++) {
		        sb.append(Chars.charAt(random.nextInt(Chars.length())));
	    	}
	    }

	    return sb.toString();
	}
	
	//**********Date & Time Methods*******************************
	public static String getCurrentDate(){
		logInfo("INFO", "Fetching Current Date");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		String currentDate = sdf.format(new Date());
		return currentDate;
	}
	
	public static Timestamp getTimeStampVal(){
		logInfo("INFO", "Fetching Current Time");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		return timestamp;
	}
	
	public static String getMonth(int month) {
		logInfo("INFO", "Fetching Month Name");
	    return new DateFormatSymbols().getMonths()[month-1];
	}
	
	//**********Screenshot Methods*******************************
	
	public static String takeScreenShotAndAttach(String fileName, String screenShotFilePath){
		logInfo("INFO", "Taking Screenshot");
		String fName = fileName + "_" + getTimeStampVal();
		fName=fName.toString().replace(":", "_").replace(" ", "_").replace(".", "_");
		String execStatus="Pass";
		boolean screenShotFlag=false;
		try{
			BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
			screenShotFlag=ImageIO.write(image, "png", new File(screenShotFilePath+"//"+fName+".png"));
		}catch(IOException | HeadlessException | AWTException  ex){
			System.out.println(ex);
		}	
		if(screenShotFlag==true)
			return execStatus;	
		else
			return null;
	}
	
	public static String takeScreenShotAndAttach(String fileName, String screenShotFilePath, boolean discardScreenShot){
		logInfo("INFO", "Taking Screenshot");
		if(discardScreenShot == false) {
			String fName = fileName + "_" + getTimeStampVal();
			fName=fName.toString().replace(":", "_").replace(" ", "_").replace(".", "_");
			String execStatus="Pass";
			boolean screenShotFlag=false;
			try{
				BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
				screenShotFlag=ImageIO.write(image, "png", new File(screenShotFilePath+"//"+fName+".png"));
			}catch(IOException | HeadlessException | AWTException  ex){
				System.out.println(ex);
			}	
			if(screenShotFlag==true)
				return execStatus;	
			else
				return null;
		}
		else
			return null;
	}
	
	//**********File Methods*******************************
	
	public static File getLatestFilefromDir(File folder){
		logInfo("INFO", "Getting Latest File Name");
        File dir = (folder);
        File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            return null;
        }

        File lastModifiedFile = files[0];
        for (int i = 1; i < files.length; i++) {
           if (lastModifiedFile.lastModified() < files[i].lastModified()) {
               lastModifiedFile = files[i];
           }
        }
        return lastModifiedFile;
    }
	
	//**********Send Mail Methods*******************************
	
	public  static boolean sendMail(
    		final String userName,
    		final String passWord,
    		String host,
    		String starttls,
    		String auth,
    		boolean debug,
    		String socketFactoryClass,
    		String toParam,
    		String ccParam,
    		String bccParam,
    		String subject,
    		String text,
    		String from,
    		String attachmentPath,
    		String attachmentName
    		){
		logInfo("INFO", "Sending Email");

    	Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", starttls);
        props.put("mail.smtp.auth",auth);
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.ssl.trust",host);
        List<String> to,cc = null,bcc=null;
        boolean ccFlag,bccFlag;
        ccFlag=bccFlag=true;
        
        //Validate if a valid email address is entered for the TO field
        //Also split the semicolon separated list of senders and validate the individual list of email addresses
        logInfo("INFO", "Validating Email Content");
        try {
        	if(toParam.isEmpty())
        		throw new Exception("Please enter a receipient email");
        	else
        		 to=splitEmail(toParam);
        	if(!ccParam.isEmpty())
            	cc=splitEmail(ccParam);
            else
            	ccFlag=false;
            if(!bccParam.isEmpty())
            	bcc=splitEmail(bccParam);
            else
            	bccFlag=false;
            
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
                   
    try{
    	logInfo("INFO", "Creating Email");
    	Session session = Session.getInstance(props,null);
        MimeMessage msg = new MimeMessage(session);
        msg.setSubject(subject);
        //attachment start
        
        /*
         * Create Message Part
         */
       
        
        //Footer can be added to the message by appending footer to the MessageBody property.
         
        //String footer="This is an automated Message.";
        String messageBody=text+"\n\nRegards\n"+from;
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText(messageBody);
        
        //Create MultiPart 
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        
        //Attaches the file to the MultiPart
        if (attachmentPath != null && attachmentPath.length() > 0) {
                MimeBodyPart attachPart = new MimeBodyPart();
                try {
                    attachPart.attachFile(attachmentPath+"\\"+attachmentName);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
 
                multipart.addBodyPart(attachPart);
         }
        // Put parts in message
        msg.setContent(multipart);
        msg.setFrom(new InternetAddress(userName));
        
        //Generate the list of email addresses to send the email
        for(String receipient:to){
        	msg.addRecipient(Message.RecipientType.TO, new InternetAddress(receipient));
        }
        if(ccFlag){
        	for(String ccreceipient:cc){
            	msg.addRecipient(Message.RecipientType.CC, new InternetAddress(ccreceipient));
            }
        }
        
        if(bccFlag){
        	for(String bccreceipient:bcc){
            	msg.addRecipient(Message.RecipientType.BCC, new InternetAddress(bccreceipient));
            }
        }
        msg.saveChanges();
        Transport transport = session.getTransport("smtp");
        transport.connect(host, userName, passWord);
        transport.sendMessage(msg, msg.getAllRecipients());
        transport.close();
        return true;
   }catch (Exception exception){
	   exception.printStackTrace();
       return false;
 }
}
    /*
     * Method validates if the email is is valid.
     */
    public static boolean valideEmail(String emailId){
    	logInfo("INFO", "Validating Email ID");
    	final Pattern VALID_EMAIL_REGEX=Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])");
    	Matcher matcher = VALID_EMAIL_REGEX .matcher(emailId);
        return matcher.find();        	
    }
     
    /*
     *Split the list of email id's in the form of a string separated by a semi colon.
     *Post split perform email validation 
     */
    public static List<String> splitEmail(String emailString) throws Exception{
    	logInfo("INFO", "Splitting Email ID based on ;");
    	List<String> emailList=Arrays.asList(emailString.split(";"));
    	for(String emailId:emailList){
    		if(!valideEmail(emailId))
    			throw new Exception("This is an Invalid Email Id : "+emailId);		
    	}
    	return emailList;
    }
    
    
    /*
     * Generate and send email based on the detail mentioned in the input txt document 
     * Input: Email Details File Name and Path
     * Output: True if file was parsed and call to method was successful; Exception thrown if error in file
     * 
     */
    public static boolean sendMail(String fileName, String filePath) throws Exception{
    	logInfo("INFO", "Sending Email");
    	//Create path to the file
    	String file=filePath+"\\"+fileName;
    	//Parse file and store the details in a Hashmap
    	HashMap<String, String> emailParam = null;
		try{
			emailParam = (HashMap<String, String>) Files.lines(Paths.get(file))
					.map(line -> line.split(":",2))
					.collect(Collectors.toMap(e->e[0].toUpperCase().trim(),e->e[1].trim()));

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Check if all mandatory fields are available in the input file. If not throw an exception with the message in errorMessage.
		logInfo("INFO", "Validating Email");
		String keyword=null;
		boolean fileError=false;
		String errorMessage="Some keywords are missing from the file and they need to be available in the format 'Keyword : Value'"
				+ "\nSome default keywords and values are "
				+ "\nHOST : amemail.deloitte.com"
				+ "\nSTARTTLS : TRUE"
				+ "\nAUTH : TRUE"
				+ "\nDEBUG : TRUE"
				+ "SOCKETFACTORYCLASS : javax.net.ssl.SSLSocketFactory"
				+ "\n.The Missing Keywords are as follows:\n";

			if(!emailParam.containsKey("USERNAME")){
				keyword="\nUSERNAME, ";
				fileError=true;
			}
			if(!emailParam.containsKey("PASSWORD")){
				keyword=keyword+"\nPASSWORD, ";
				fileError=true;
			}
			if(!emailParam.containsKey("HOST")){
				keyword=keyword+"\nHOST, ";
				fileError=true;
			}
			if(!emailParam.containsKey("STARTTLS")){
				keyword=keyword+"\nSTARTTLS, ";
				fileError=true;
			}
			if(!emailParam.containsKey("AUTH")){
				keyword=keyword+"\nAUTH, ";
				fileError=true;
			}
			if(!emailParam.containsKey("DEBUG")){
				keyword=keyword+"\nDEBUG, ";
				fileError=true;
			}
			if(!emailParam.containsKey("SOCKETFACTORYCLASS")){
				keyword=keyword+"\nSOCKETFACTORYCLASS, ";
				fileError=true;
			}
			if(!emailParam.containsKey("TO")){
				keyword=keyword+"\nTO, ";
				fileError=true;
			}
			if(!emailParam.containsKey("CC")){
				keyword=keyword+"\nCC, ";
				fileError=true;
			}
			if(!emailParam.containsKey("BCC")){
				keyword=keyword+"\nBCC, ";
				fileError=true;
			}
			if(!emailParam.containsKey("SUBJECT")){
				keyword=keyword+"\nSUBJECT, ";
				fileError=true;
			}
			if(!emailParam.containsKey("MESSAGE")){
				keyword=keyword+"\nMESSAGE, ";
				fileError=true;
			}
			if(!emailParam.containsKey("FROM")){
				keyword=keyword+"\nFROM, ";
				fileError=true;
			}
			if(!emailParam.containsKey("FILEPATH")){
				keyword=keyword+"\nFILEPATH, ";
				fileError=true;
			}
			if(!emailParam.containsKey("FILENAME")){
				keyword=keyword+"\nFILENAME ";
				fileError=true;
			}
			if(fileError)
				throw new Exception(errorMessage+keyword);
		
		
		//Call the sendemail() method with the details provided in the file
			logInfo("INFO", "Calling Send Mail method");
		boolean success=sendMail(
    		emailParam.get("USERNAME"),
    		emailParam.get("PASSWORD"),
    		emailParam.get("HOST"),
    		//Integer.parseInt(emailParam.get("PORT")),
    		emailParam.get("STARTTLS"),
    		emailParam.get("AUTH"),
    		Boolean.valueOf(emailParam.get("DEBUG")),
    		emailParam.get("SOCKETFACTORYCLASS"),
    		emailParam.get("TO"),
    		emailParam.get("CC"),
    		emailParam.get("BCC"),
    		emailParam.get("SUBJECT"),
    		emailParam.get("MESSAGE"),
    		emailParam.get("FROM"),
    		emailParam.get("FILEPATH"),
    		emailParam.get("FILENAME")
	     );
		
		return success;
    }
	
    public boolean sendMailLatestReport(String fileName, String filePath) throws Exception{
    	//Create path to the file
    	String file=filePath+"\\"+fileName;
    	//Parse file and store the details in a Hashmap
    	logInfo("INFO", "Getting email content from mail properties");
    	HashMap<String, String> emailParam = null;
		try{
			emailParam = (HashMap<String, String>) Files.lines(Paths.get(file))
					.map(line -> line.split(":",2))
					.collect(Collectors.toMap(e->e[0].toUpperCase().trim(),e->e[1].trim()));

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Check if all mandatory fields are available in the input file. If not throw an exception with the message in errorMessage.
		logInfo("INFO", "Validating email contents");
		String keyword=null;
		boolean fileError=false;
		String errorMessage="Some keywords are missing from the file and they need to be available in the format 'Keyword : Value'"
				+ "\nSome default keywords and values are "
				+ "\nHOST : amemail.deloitte.com"
				+ "\nSTARTTLS : TRUE"
				+ "\nAUTH : TRUE"
				+ "\nDEBUG : TRUE"
				+ "SOCKETFACTORYCLASS : javax.net.ssl.SSLSocketFactory"
				+ "\n.The Missing Keywords are as follows:\n";

			if(!emailParam.containsKey("USERNAME")){
				keyword="\nUSERNAME, ";
				fileError=true;
			}
			if(!emailParam.containsKey("PASSWORD")){
				keyword=keyword+"\nPASSWORD, ";
				fileError=true;
			}
			if(!emailParam.containsKey("HOST")){
				keyword=keyword+"\nHOST, ";
				fileError=true;
			}
			if(!emailParam.containsKey("STARTTLS")){
				keyword=keyword+"\nSTARTTLS, ";
				fileError=true;
			}
			if(!emailParam.containsKey("AUTH")){
				keyword=keyword+"\nAUTH, ";
				fileError=true;
			}
			if(!emailParam.containsKey("DEBUG")){
				keyword=keyword+"\nDEBUG, ";
				fileError=true;
			}
			if(!emailParam.containsKey("SOCKETFACTORYCLASS")){
				keyword=keyword+"\nSOCKETFACTORYCLASS, ";
				fileError=true;
			}
			if(!emailParam.containsKey("TO")){
				keyword=keyword+"\nTO, ";
				fileError=true;
			}
			if(!emailParam.containsKey("CC")){
				keyword=keyword+"\nCC, ";
				fileError=true;
			}
			if(!emailParam.containsKey("BCC")){
				keyword=keyword+"\nBCC, ";
				fileError=true;
			}
			if(!emailParam.containsKey("SUBJECT")){
				keyword=keyword+"\nSUBJECT, ";
				fileError=true;
			}
			if(!emailParam.containsKey("MESSAGE")){
				keyword=keyword+"\nMESSAGE, ";
				fileError=true;
			}
			if(!emailParam.containsKey("FROM")){
				keyword=keyword+"\nFROM, ";
				fileError=true;
			}
			if(!emailParam.containsKey("FILEPATH")){
				keyword=keyword+"\nFILEPATH, ";
				fileError=true;
			}/*
			if(!emailParam.containsKey("FILENAME")){
				keyword=keyword+"\nFILENAME ";
				fileError=true;
			}*/
			if(fileError)
				throw new Exception(errorMessage+keyword);
			
			logInfo("INFO", "Getting Latest Report");
			File f = new File(emailParam.get("FILEPATH"));
			File latestReport = getLatestFilefromDir(f);
			String latestReportName = latestReport.getName();
			
		
			logInfo("INFO", "Calling Send Mail method");
		//Call the sendemail() method with the details provided in the file
		boolean success=sendMail(
    		emailParam.get("USERNAME"),
    		emailParam.get("PASSWORD"),
    		emailParam.get("HOST"),
    		//Integer.parseInt(emailParam.get("PORT")),
    		emailParam.get("STARTTLS"),
    		emailParam.get("AUTH"),
    		Boolean.valueOf(emailParam.get("DEBUG")),
    		emailParam.get("SOCKETFACTORYCLASS"),
    		emailParam.get("TO"),
    		emailParam.get("CC"),
    		emailParam.get("BCC"),
    		emailParam.get("SUBJECT"),
    		emailParam.get("MESSAGE"),
    		emailParam.get("FROM"),
    		emailParam.get("FILEPATH"),
    		latestReportName
	     );
		
		return success;
    }
    
   public static void logInfo(String logStatus, String details){
	   logger.info(logStatus + details);
	}
    
    
    public static String getRandomDate(
    		int minYear, 
    		int maxYear, 
    		int minMonth, 
    		int maxMonth, 
    		Boolean futureDateAllowed, 
    		Boolean pastDateAllowed, 
    		Boolean currentDateAllowed){
    	try{
    		long maxDate;
        	long minDate;
        	LocalDate currentDate = LocalDate.now();
        	//Check to see if month value is in proper range
        	if (minMonth < 1 || maxMonth > 12)	{
        		return "Invalid month value";
        	}
        	
        	if (futureDateAllowed == false && pastDateAllowed == false && currentDateAllowed == false) 	{
        		return "Invalid Input -- One of the values need to be true.";
        	}  		
    		        	
        	if ((pastDateAllowed == true || currentDateAllowed == true) )  {
        		if (minYear > currentDate.getYear() || minMonth > currentDate.getMonthValue())
        		{
        			return "Invalid Input -- Minimum Year cannot be greater than current Year if pastDateAllowed or currentDateAllowed"
            				+ " flag is true.";        			
        		}
        		
        	}
        	
        
        	//setting minDate and maxDate taking into account the month of February and Leap year.
        	if (((Year.of(maxYear).isLeap()) || (Year.of(minYear).isLeap())) && (maxMonth == 2)) {
        		maxDate = LocalDate.of(maxYear, maxMonth, 29).toEpochDay();
        		minDate = LocalDate.of(minYear, minMonth, 1).toEpochDay();
        	}
        	else if (maxMonth == 2) {
        		maxDate = LocalDate.of(maxYear, maxMonth, 28).toEpochDay();
        		minDate = LocalDate.of(minYear, minMonth, 1).toEpochDay();    		
        	}
        	else {
        		maxDate = LocalDate.of(maxYear, maxMonth, 31).toEpochDay();
        		minDate = LocalDate.of(minYear, minMonth, 1).toEpochDay();    		
        	}
        	//Generate Random Date
        	long randDate = ThreadLocalRandom.current().nextLong(minDate, maxDate);
            LocalDate randomDate = LocalDate.ofEpochDay(randDate);
            
            if (futureDateAllowed == true && pastDateAllowed == true && currentDateAllowed == true){
        		return randomDate.toString();
        	}
            if(futureDateAllowed == false && pastDateAllowed == false) {
        		return currentDate.toString();
        	}
        	if(pastDateAllowed == false && currentDateAllowed == false) {
        		minDate = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), currentDate.getDayOfMonth() + 1).toEpochDay();
            		
            	randDate = ThreadLocalRandom.current().nextLong(minDate, maxDate);
                randomDate = LocalDate.ofEpochDay(randDate);
                
                return randomDate.toString();
        		
        	}
        	
        	if(futureDateAllowed == false && currentDateAllowed == false) {
        		maxDate = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), currentDate.getDayOfMonth() - 1).toEpochDay();
            	randDate = ThreadLocalRandom.current().nextLong(minDate, maxDate);
                randomDate = LocalDate.ofEpochDay(randDate);  
                return randomDate.toString();        		
        	}
        	
            //Check the futureDateAllowed Flag
        	if (futureDateAllowed == false && (randomDate.isAfter(currentDate))){
        		maxDate = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), currentDate.getDayOfMonth()).toEpochDay();
            	randDate = ThreadLocalRandom.current().nextLong(minDate, maxDate);
                randomDate = LocalDate.ofEpochDay(randDate);  
                return randomDate.toString();
        	}
        	
            //Check the pastDateAllowed Flag
        	if (pastDateAllowed == false && (randomDate.isBefore(currentDate)))	{
        		minDate = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), currentDate.getDayOfMonth()).toEpochDay();
            	randDate = ThreadLocalRandom.current().nextLong(minDate, maxDate);
                randomDate = LocalDate.ofEpochDay(randDate);
                return randomDate.toString();
        	}
        	
            //Check the currentDateAllowed Flag
        	if (currentDateAllowed == false && randomDate.isEqual(currentDate)) {
        		randDate = ThreadLocalRandom.current().nextLong(minDate, maxDate);
                randomDate = LocalDate.ofEpochDay(randDate);
                return randomDate.toString();
        	}
        	
        	return null;
    	}
    	catch(Exception e)
    	{
    		return "Invalid Parameters -- Please validate the parameters.";
    	}
    	
    }
    
    
    @SuppressWarnings("finally")
	public static String ConvertCsvToHtmlTable(String csvFileName, String delimiter) {
		BufferedReader br = null;
		StringBuilder htmlString = new StringBuilder();
        try {	
        	String htmlHeadBodyOpenTag = "<html><body>";
        	String htmlHeadBodyCloseTag = "</body></html>";
        	String htmlTableOpenTag = "<table border = \"1\">";
        	String htmlTableCloseTag = "</table>";
        	
            //Reading the csv file
            br = new BufferedReader(new FileReader(csvFileName));
            
            //Append opening html tags
            htmlString.append(htmlHeadBodyOpenTag);
        	htmlString.append(htmlTableOpenTag);
        	
            //Create List for holding the csv lines
            List<String> list = new ArrayList<String>();
            list = br.lines().collect(Collectors.toList());
            
            //Create each row for html table
            for(String row : list) {
            	String csvRow = CsvLineToRow(row, delimiter);
            	htmlString.append(csvRow);
            }
            //append closing tags
            htmlString.append(htmlTableCloseTag);
            htmlString.append(htmlHeadBodyCloseTag);
            br.close();
                        
        } catch(FileNotFoundException fe) {
        	System.out.println("Error file not found\n");
        	fe.printStackTrace();        	
        }
        catch(IOException ie) {
        	System.out.println("Error occured while closing the BufferedReader");
            ie.printStackTrace();        	
        }
        finally {
        	return htmlString.toString();
        }
		
	}
    
	private static String CsvLineToRow(String csvLine, String delimiter) {
		String htmlRowOpenTag = "<tr>";
		String htmlCellOpenTag = "<td>";
		String htmlRowCloseTag = "</tr>";
		String htmlCellCloseTag = "</td>";
		StringBuilder rowString = new StringBuilder();
		rowString.append(htmlRowOpenTag);
		List<String> csvLineSep = Arrays.asList(csvLine.split(delimiter));
		for (String cell : csvLineSep) {
			rowString.append(htmlCellOpenTag);
			rowString.append(cell);
			rowString.append(htmlCellCloseTag);			
		}
		rowString.append(htmlRowCloseTag);
	
		return rowString.toString();
		
	}
	
	/**
	 * Confirms whether the character is valid based on following parameters-
		1) character - If character is a letter return true
		2) includeDigits - If includeDigits is set to true and character is a digit return true
	 * @param character 
	 * @param includeDigits 
	 * @return - true if input character is valid 
	 */
	public static boolean validChar(char character, boolean includeDigits) {
		boolean isCharValid = false;
		isCharValid = Character.isLetter(character);
		if(!isCharValid && includeDigits) {
			isCharValid = Character.isLetterOrDigit(character);
		}
		return isCharValid;
	}
	
	/**
	 * Confirms whether the date is valid based on the following parameters-
		1) date
		2) maxMonths - Check that maxMonths is not exceeded
		3) minMonths - Check that minMonths is not exceeded
		4) currentDateAllowed - If set to false and date is today's date then return false
	 * @param date
	 * @param maxMonths
	 * @param minMonths
	 * @param currentDateAllowed
	 * @return
	 */
	public static boolean validDate(Date date, int maxMonths, int minMonths, boolean currentDateAllowed) {
		boolean isDateValid = false;
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date todayWithZeroTime;
		try {
			if(date != null ) {
				todayWithZeroTime = formatter.parse(formatter.format(new Date()));
				Date minDate = addMonthsToDate(todayWithZeroTime, -minMonths);
				Date maxDate = addMonthsToDate(todayWithZeroTime, maxMonths);
				if(!date.before(minDate) && !date.after(maxDate)) {
					isDateValid = true;
				}
				
				//currentDateAllowed - If set to false and date is today's date then return false
				if(isDateValid && !currentDateAllowed && todayWithZeroTime.compareTo(date) == 0) {
					isDateValid = false;
				}
			}
		} catch (Exception e) {
			System.out.println("Exception occured while checking date validity");
			e.getMessage();
		}
		return isDateValid;
	}
	
	/**
	 * Return full name(last, first middle) of the person using following 3 paramaters- 
		1) last
		2) first
		3) middle
	 * @param last
	 * @param first
	 * @param middle
	 * @return
	 */
	public static String formatFullName(String last, String first, String middle) {
		String formattedName = "";
		formattedName = null != last ? last : "";
		if (formattedName != null && formattedName != ""
				&& ((first != null && first != "") || (middle != null && middle != ""))) {
			formattedName = formattedName + ",";
		}

		if (first != null && first != "") {
			formattedName = formattedName + first + " ";
		}
		if (middle != null && middle != "") {
			formattedName = formattedName + middle;
		}

		return formattedName;
	}

	/**
	 * Returns the first day of the fiscal year for a supplied date
	 * @param date
	 * @return
	 */
	public static Date getBeginOfFiscalYear(Date date) {
		Date firstDayOfFiscalYear = null;
		if (date == null) {
			System.out.println("Input date is mandatory");
		}
		try {
			Calendar inputDateCal = Calendar.getInstance();
			inputDateCal.setTime(date);
			Calendar compareDate = Calendar.getInstance();
			compareDate.set(inputDateCal.get(Calendar.YEAR), 0,1);
			
			Calendar firstFiscalCalendar = compareDate;
			
			switch (compareDate.get(Calendar.DAY_OF_WEEK)) {
			case Calendar.SATURDAY:
				firstFiscalCalendar.add(Calendar.DAY_OF_MONTH, 2);
				break;
			case Calendar.SUNDAY:
				firstFiscalCalendar.add(Calendar.DAY_OF_MONTH, 1);
				break;
			default:
				firstFiscalCalendar = compareDate;
			}
			if(firstFiscalCalendar != null) {
				firstDayOfFiscalYear = firstFiscalCalendar.getTime();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return firstDayOfFiscalYear;
	}

	
	/**
	 * Returns the last day of the fiscal year for a supplied date
	 * @param date
	 * @return
	 */
	public static Date getEndOfFiscalYear(Date date) {
		Date lastDayOfFiscalYear = null;
		if (date == null) {
			System.out.println("Input date is mandatory");
		}
		try {
			Calendar inputDateCal = Calendar.getInstance();
			inputDateCal.setTime(date);
			Calendar compareDate = Calendar.getInstance();
			compareDate.set(inputDateCal.get(Calendar.YEAR), 11,31);
			
			Calendar lastFiscalCalendar = compareDate;
			
			switch (compareDate.get(Calendar.DAY_OF_WEEK)) {
			case Calendar.SATURDAY:
				lastFiscalCalendar.add(Calendar.DAY_OF_MONTH, -1);
				break;
			case Calendar.SUNDAY:
				lastFiscalCalendar.add(Calendar.DAY_OF_MONTH, -2);
				break;
			default:
				lastFiscalCalendar = compareDate;
			}
			if(lastFiscalCalendar != null) {
				lastDayOfFiscalYear = lastFiscalCalendar.getTime();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return lastDayOfFiscalYear;
	}

	
	
	public static Date addMonthsToDate(final Date date, final int amount) {
        if (date == null) {
            System.out.println("The date must not be null");
        }
        final Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, amount);
        return c.getTime();
    }
	 
	public static boolean isNumeric(String text) {
	    try {
	        Long.parseLong(text);
	        return true;
	    } catch (NumberFormatException ex) {
	        return false;
	    }
	} 
	
	public static String CurrentDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy"); 
		String currentDate = sdf.format(new Date());
		return currentDate;
	}
	
	
	public static String CurrentMonth(){
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM/yyyy");
		String currentMonth = sdf1.format(new Date());
		return currentMonth;
	}
	
	public static Timestamp Timestampval(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		return timestamp;
	}
	
	public static Timestamp timeStampVal(){
		@SuppressWarnings("unused")
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		return timestamp;
	}
	
	/**
	 * This method returns date from "n" days before current date.
	 **/
	public static Date getDateBeforeDays(int days) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -days); // -days
		return cal.getTime();
	}
	
	/**
	 * This method returns date from "n" days after current date.
	 **/
	public static Date getDateAfterDays(int days) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, days); // +days
		return cal.getTime();
	}
	
	/**
	 * This method returns date from "n" years before current date.
	 **/
	public static Date getDateBeforeYears(int years) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -years); // -years
		return cal.getTime();
	}
	
	/**
	 * This method returns date from "n" years after current date.
	 **/
	public static Date getDateAfterYears(int years) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, years); // +years
		return cal.getTime();
	}
	
	/**
	 * This method returns date from "n" months after current date.
	 **/
	public static Date getDateAfterMonths(int months) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, months); // +months
		return cal.getTime();
	}
	
	/**
	 * This method returns date from "n" months before current date.
	 **/
	public static Date getDateBeforeMonths(int months) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -months); // -months
		return cal.getTime();
	}
	
	/**
	 * This method returns first date of the current momth.
	 **/
	public static Date getFirstDateOfCurrentMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH,
				cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}
	
	/**
	 * This method returns the host name.
	 **/
	public static String myLocalHostName() throws IOException {	   
		String hostname = "Unknown";
	        try {
	            InetAddress myHost = InetAddress.getLocalHost();
	            hostname =myHost.getHostName();
	            System.out.println("The host name is : "+ hostname);
	            String path = new File(".").getCanonicalPath() + "\\src\\test\\resources\\serverList.txt";
	    		File f = new File(path);
	    	//	if (!f.exists()) {
	    		     try {
	    				System.out.println("FILE CREATION SUCCEEDED!");
	    				FileWriter write = new FileWriter(f);
	    				PrintWriter print_line = new PrintWriter(write);
	    				print_line.println(hostname);
	    				print_line.close();
	    			} catch (IOException e) {
	    				System.out.println("FILE CREATION ERROR: " + e.getMessage());
	    				e.printStackTrace();
	    			}
	    		//}
	    	} catch (UnknownHostException ex) {
	            ex.printStackTrace();
	        }
			return hostname;
	    }
	
	/**
	 * This method adds the host name into the Server test file which Powershell scripts uses.
	 **/
	public static String addHostNametoServerFile(String hostname) throws IOException {	   
	        try {
	            String path = new File(".").getCanonicalPath() + "\\src\\test\\resources\\serverList.txt";
	    		File f = new File(path);
	    	//	if (!f.exists()) {
	    		     try {
	    				FileWriter write = new FileWriter(f);
	    				PrintWriter print_line = new PrintWriter(write);
	    				print_line.println(hostname);
	    				print_line.close();
	    			} catch (IOException e) {
	    				System.out.println("FILE CREATION ERROR: " + e.getMessage());
	    				e.printStackTrace();
	    			}
	    		//}
	    	} catch (UnknownHostException ex) {
	            ex.printStackTrace();
	        }
			return hostname;
	    }
	/**
	 * This method adds the server details to the extent report.
	 **/
	public void insertServerDetailsExtentReport(ExtentReports extent, String callTime, String serverName) throws IOException {
		try {
			String CPULoad = "";
			String MemLoad = "";
			String CDriveStatus = "";

			try {
				//String serverFilePath = System.getProperty("user.dir") + "\\src\\test\\resources";
				String powerShellFilePath = System.getProperty("user.dir") + "\\src\\test\\resources\\getServerDetails.ps1";
				String command = "powershell.exe -ExecutionPolicy Bypass "+ powerShellFilePath + " -serverList " + serverName;	
				//addHostNametoServerFile(serverName);
				ExecuteWatchdog watchdog = new ExecuteWatchdog(20000);
				Process powerShellProcess = Runtime.getRuntime().exec(command);
				if (watchdog != null) {
					watchdog.start(powerShellProcess);
				}
				BufferedReader stdInput = new BufferedReader(new InputStreamReader(powerShellProcess.getInputStream()));
				String line;
				String[] SystemInfo = new String[10];
				String SystemInfoString = "";
				while ((line = stdInput.readLine()) != null) {
					if (line.contains("%")) {
						SystemInfoString = line.replaceAll("\\s{2,}", " ").trim();
						SystemInfo = SystemInfoString.split(" ");
					}
				}
				System.out.println("System Informaiton: " +SystemInfoString);
				CPULoad = SystemInfo[1];
				MemLoad = SystemInfo[2];
				CDriveStatus = SystemInfo[3];

			} catch (Exception e) {
				e.printStackTrace();
			}

			extent.addSystemInfo("CPU Load_"+callTime, CPULoad).addSystemInfo("Memory Load_"+callTime, MemLoad)
					.addSystemInfo("C Drive Memory Status_"+callTime, CDriveStatus);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
					
	/**
	 * This method inserts the server details to Database.
	 **/
	public void insertServerDetailsDB(String testRunid, String testCase, String callTime, String serverName) throws IOException {
		try {
			//Connection conn = getConnection("localhost", "3306", "automation_framework", "root", "root", "false");
			Connection conn = dbConnect2.getCon();
			String CPULoad = "";
			String MemLoad = "";
			String CDriveMemoryStatus = "";
			//addHostNametoServerFile(serverName);
			try {
				String powerShellFilePath = System.getProperty("user.dir") + "\\src\\test\\resources\\getServerDetails.ps1";
				String command = "powershell.exe -ExecutionPolicy Bypass "+ powerShellFilePath + " -serverList " + serverName;	
				ExecuteWatchdog watchdog = new ExecuteWatchdog(20000);
				Process powerShellProcess = Runtime.getRuntime().exec(command);
				if (watchdog != null) {
					watchdog.start(powerShellProcess);
				}
				
				BufferedReader stdInput = new BufferedReader(new InputStreamReader(powerShellProcess.getInputStream()));
				String line;
				String[] SystemInfo = new String[10];
				String SystemInfoString = "";
				while ((line = stdInput.readLine()) != null) {
					if (line.contains("%")) {
						SystemInfoString = line.replaceAll("\\s{2,}", " ").trim();
						SystemInfo = SystemInfoString.split(" ");
					}
				}
				System.out.println(SystemInfoString);
				CPULoad = SystemInfo[1];
				MemLoad = SystemInfo[2];
				CDriveMemoryStatus = SystemInfo[3];

			} catch (Exception e) {
				e.printStackTrace();
			}
			Timestamp TimeStamp = new Timestamp(System.currentTimeMillis());
			String insertServerDetails = "insert into server_details (test_runid, test_case, call_time, server_name, CPU_load, Memory_load, CDrive_MemoryStatus, created_at, modified_at) "
					+ "VALUES ('" + testRunid + "','" + testCase + "','" + callTime + "','" + serverName + "',' " + CPULoad + "',' " + MemLoad + "', '"	+ CDriveMemoryStatus + "', '"+ TimeStamp + "', '"+ TimeStamp + "');";		
			System.out.println("Statement is: " + insertServerDetails);
			PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(insertServerDetails);
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public static Connection getConnection(String host, String port,String databaseName, String user, String password, String useSSL) {
		Connection con = null;
		try {
			Properties properties = new Properties();
			properties.setProperty("user", user);
			properties.setProperty("password", password);
			properties.setProperty("useSSL", useSSL);
			properties.setProperty("autoReconnect", "true");
			String db = "jdbc:mysql://" + host + ":" + port + "/" + databaseName; 
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection(db, properties);
		} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return con;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
