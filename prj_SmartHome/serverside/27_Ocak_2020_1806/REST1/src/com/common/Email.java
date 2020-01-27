package com.common;

import java.util.Properties;

import java.util.Properties;
import java.util.*;
import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.naming.*;
 

public class Email {
	/**
	 * Verification Code için email gönderme işlemleri.
	 * 
	 * using by "Account.java"
	 */
	///Trident Email Server:@{
    private final String senderEmailID   = "smart@trident.com.tr";
    private final String senderPassword  = "DenemE12";
    private final String emailSMTPserver = "mail.trident.com.tr";
    private final String emailServerPort = "465";
    ///Trident Email Server:@}
        
	private String receiverEmail = "";//Verification kodu alacak adress.
	private int verificationCode = 0;//verification code to be sent.
	public Email(String receiverEmail,int verificationCode) {
		this.receiverEmail    = receiverEmail;
		this.verificationCode = verificationCode;
	}
	
	public boolean send(){
	    String emailSubject = "Verification Code-TridentSmart";
	    //String emailBody = "Email content.";//we won't use it for now.Because we use HTML content.
	    String emailBodyAsHtml = "<h1 style=\"color: #FF00FF;\">Verification Code:"+verificationCode +"</h1>"; //#5e9ca0 ,,<font size=\"6\">555556</font>
        Properties properties  = new Properties();
        properties.put("mail.smtp.user",senderEmailID);
        properties.put("mail.smtp.host", emailSMTPserver);
        properties.put("mail.smtp.port", emailServerPort);
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.socketFactory.port", emailServerPort);
        properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback", "false");
        SecurityManager security = System.getSecurityManager();
        try {
            Authenticator authenticator = new SMTPAuthenticator();
            Session session = Session.getInstance(properties, authenticator);
            MimeMessage message = new MimeMessage(session);
            //msg.setText(emailBody); //normal içerik
            message.setContent(emailBodyAsHtml,"text/html");//Html içerik
            message.setSubject(emailSubject);
            message.setFrom(new InternetAddress(senderEmailID));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(receiverEmail));
            Transport.send(message);
            System.out.println("Message send Successfully....");
            return true;
        }
        catch (Exception mex)
        {
            mex.printStackTrace();
            System.out.println("Exception:" + mex.getMessage());
        }
        return false;
	}//send()
	
    private class SMTPAuthenticator extends javax.mail.Authenticator
    {
        public PasswordAuthentication getPasswordAuthentication()
        {
            return new PasswordAuthentication(senderEmailID, senderPassword);
        }
    }
	
}
