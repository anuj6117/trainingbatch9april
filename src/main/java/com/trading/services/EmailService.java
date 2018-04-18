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
	 

	
	    public void sendEmail(int otp) throws Exception{
	        MimeMessage message = sender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message);
	         
	        helper.setTo("vanshikamadan97@gmail.com");
	        helper.setText("Your account has been succesfully created. Please verify it using OTP  " +otp);
	        helper.setSubject("Verification");
	         	        sender.send(message);
	    }
	}



