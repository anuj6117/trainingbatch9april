package com.trading.services;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	 
	@Autowired
	private JavaMailSender sender;
	 String home() {
		         try {
		             sendEmail();
		             return "Email Sent!";
		         }catch(Exception ex) {
		             return "Error in sending email: "+ex;
		         }
		     }

	
	    public void sendEmail() throws Exception{
	        MimeMessage message = sender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message);
	         
	        helper.setTo("vanshikamadan97@gmail.com");
	        helper.setText("How are you?");
	        helper.setSubject("Hi");
	         	        sender.send(message);
	    }
	}



