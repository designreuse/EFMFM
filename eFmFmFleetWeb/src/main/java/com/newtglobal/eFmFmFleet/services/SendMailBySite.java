package com.newtglobal.eFmFmFleet.services;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMailBySite {
	
	public  void mail(String to,String confCode) {
      // Sender's email ID needs to be mentioned
      String from = "fmfm@newtglobal.com";
      final String username = "fmfm@newtglobal.com";//change accordingly
      final String password = "8sjfcgEM";//change accordingly

      // Assuming you are sending email through relay.jangosmtp.net
      String host = "smtp.bizmail.yahoo.com";

      Properties props = new Properties();
      props.put("mail.smtp.auth", "true");
      props.put("mail.smtp.starttls.enable", "true");
      props.put("mail.smtp.host", host);
      props.put("mail.smtp.port", "25");

      // Get the Session object.
      Session session = Session.getInstance(props,
         new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(username, password);
	   }
         });

      try {
	   // Create a default MimeMessage object.
	   Message message = new MimeMessage(session);
	
	   // Set From: header field of the header.
	   message.setFrom(new InternetAddress(from));
	
	   // Set To: header field of the header.
	   message.setRecipients(Message.RecipientType.TO,
               InternetAddress.parse(to));
	
	   // Set Subject: header field
	   message.setSubject("Approval For Cab Request");
	
	   // Now set the actual message
	   message.setText("http://localhost:8080/eFmFmFleetWeb/");

	   // Send message
	   Transport.send(message);
	   

      } catch (MessagingException e) {
         throw new RuntimeException(e);
      }
   }
}