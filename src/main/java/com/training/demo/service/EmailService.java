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
	 void home(int otp) {
		         try {
		             sendEmail(otp);
		         }
		         catch(Exception ex) {
		        	 System.out.println("email could not be verified as : "+ex);
		         }
		     }

	    public void sendEmail(int otp) throws Exception{
	        User user = new User();
	    	//String email = user.getEmail();
	        MimeMessage message = mailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message);
	        helper.setTo("rohit.rff.godara@gmail.com");
	        helper.setText("Hi dear,/n Here is your otp : "+otp);
	        helper.setSubject("Hi");
	         	        mailSender.send(message);
	         	        System.out.println("Mail Sent"); 
	    }
	}