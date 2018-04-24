package com.training.demo.service;

import java.util.Random;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.training.demo.model.User;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;
	    public void sendEmail(int otp,String email) throws Exception{
	    	if(email != null)
	    	{
	        MimeMessage message = mailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message);
	        helper.setTo(email);
	        helper.setText("Hello, Here is your otp : "+otp);
	        helper.setSubject("Otp Verification");
	        mailSender.send(message);
	        System.out.println("Mail Sent"); 
	    	}
	    }
	}