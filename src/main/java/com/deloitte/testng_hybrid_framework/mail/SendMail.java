package com.deloitte.testng_hybrid_framework.mail;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.deloitte.testng_hybrid_framework.util.Constants;
 

public class SendMail{
    public static void main(String[] args) throws Exception{
		//report folder - extent reports
		//date
		String reportFolder=Constants.REPORT_PATH;
        FileFilterDateIntervalUtils filter = new FileFilterDateIntervalUtils("2010-01-04", "2050-01-20");
        File folder =  new File(reportFolder);
        File files[] = folder.listFiles(filter);
        
        //get last modified file
        LastModifiedFilter l = new LastModifiedFilter();
        File latesFile = l.getLatestFilefromDir(folder);
        String fileName = latesFile.getName();
        String extentFilePath=reportFolder+fileName;
         
         // mail extent reports
                String[] to={"abc@deloitte.com","xyz@deloitte.com"};
                String[] cc={};
                String[] bcc={};

                //This is for gmail

                sendMail("xyz.deloitte.com",
                		            "Password",
                		            "smtp.deloitte.com",
                		            "587",
                		            "true",
                		            "true",
                		            true,
                		            "javax.net.ssl.SSLSocketFactory",
                		            "true",
                		            to,
                		            cc,
                		            bcc,
                		            "Automation Test Reports - Extent",
                		            "Please find the reports attached.\n\n Regards\nWebMaster",
                		            extentFilePath,
                		            fileName);
                
                // mail the xslt reports
                /*String xsltReportPath=reportFolder+"Reports.zip";
                Zip.zipDir(System.getProperty("user.dir")+"//XSLT_Reports", xsltReportPath);
                SendMail.sendMail(
                		"xyz@deloitte.com",
    		            "Password",
    		            "smtp.deloitte.com",
    		            "587",
    		            "true",
    		            "true",
    		            true,
    		            "javax.net.ssl.SSLSocketFactory",
    		            "false",
    		            to,
    		            cc,
    		            bcc,
    		            "Automation Test Reports - XSLT",
    		            "Please find the reports attached.\n\n Regards\nWebMaster",
    		            xsltReportPath,
    		            "Reports.zip");*/
    }

        public  static boolean sendMail(
        		final String userName,
        		final String passWord,
        		String host,
        		String port,
        		String starttls,
        		String auth,
        		boolean debug,
        		String socketFactoryClass,
        		String fallback,
        		String[] to,
        		String[] cc,
        		String[] bcc,
        		String subject,
        		String text,
        		String attachmentPath,
        		String attachmentName){

        	Properties props = new Properties();
            props.put("mail.smtp.starttls.enable", starttls);
            props.put("mail.smtp.auth",auth);
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", port);

        try{
        	Session session = Session.getInstance(props,
        	new javax.mail.Authenticator() {
        		protected PasswordAuthentication getPasswordAuthentication() {
        			return new PasswordAuthentication(userName, passWord);
        		}
        	});
            MimeMessage msg = new MimeMessage(session);
            msg.setText(text);
            msg.setSubject(subject);
            //attachment start
            // create the message part 
            Multipart multipart = new MimeMultipart();
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            DataSource source = 
            new FileDataSource(attachmentPath);
            messageBodyPart.setDataHandler(
            new DataHandler(source));
            messageBodyPart.setFileName(attachmentName);
            multipart.addBodyPart(messageBodyPart);
            // attachment ends

            // Put parts in message
            msg.setContent(multipart);
            msg.setFrom(new InternetAddress(userName));
            for(int i=0;i<to.length;i++){
            	msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to[i]));
            }
            for(int i=0;i<cc.length;i++){
            	msg.addRecipient(Message.RecipientType.CC, new InternetAddress(cc[i]));
            }
            for(int i=0;i<bcc.length;i++){
            	msg.addRecipient(Message.RecipientType.BCC, new InternetAddress(bcc[i]));
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
}