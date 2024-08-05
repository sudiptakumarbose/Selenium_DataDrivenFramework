package com.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment; 
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;


public class MonitoringMail {

	
	
	public void sendEmail(String server, String from, String[] To, String[] CC, String subject,String messageBody)throws MessagingException,AddressException {
		boolean debug = false;
		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.EnableSSL.enable", "true");
		props.put("mail.smtp.auth", "false");
		props.setProperty("mail.smtp.host", server);
		props.put("mail.debug", "true");
		props.put("mail.smtp.socketFactory.class", "java.net.ssl.SSLSocketFactory");
		props.setProperty("mail.smtp.socketFactory.fallback", "true");
		props.setProperty("mail.smtp.port", "587");
		props.setProperty("mail.smtp.socketFactory.port", "587");
		
		try {
			Session session = Session.getInstance(props, null);
			Transport bus = session.getTransport("smtp");
			bus.connect();
			Message message = new MimeMessage(session);
			message.addHeader("X-priority", "1");
			message.setFrom(new InternetAddress(from));
			InternetAddress[] addressTo = new InternetAddress[To.length];
			for (int i = 0; i < To.length; i++) {
				addressTo[i] = new InternetAddress(To[i]);
			}
			InternetAddress[] addressCC = new InternetAddress[CC.length];
			for (int i = 0; i < CC.length; i++) {
				addressCC[i] = new InternetAddress(CC[i]);
			}
			message.setRecipients(Message.RecipientType.TO, addressTo);
			message.setRecipients(Message.RecipientType.CC, addressCC);
			message.setSubject(subject);
			BodyPart body = new MimeBodyPart();
			
			body.setContent(messageBody, "text/html");
			MimeMultipart multipart = new MimeMultipart();
			multipart.addBodyPart(body);
			message.setContent(multipart);
			Transport.send(message);
			System.out.println("Successfully sent mail to all users");
			bus.close();

		} catch (MessagingException mex) {
			mex.printStackTrace();
		}

	}
}



	
