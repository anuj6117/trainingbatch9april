package com.trainingproject.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
@Service
public class UserOTPService {
	
	 public static final String ACCOUNT_SID = "AC2e1ebaf4bdd8ee0e0af53802988fdea9";
	 public static final String AUTH_TOKEN = "a9e72dacc44a2af36079544e013079e6";
	 public static final String TWILIO_NUMBER = "+15136571810";
	 @Autowired
	 JavaMailSender sender;
	
	 
	 static Integer otp;
	 public Integer sendSMS() {
	 try {
		 Random rnd = new Random();
			otp = rnd.nextInt(100000);
		
	 TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
	 
	 List<NameValuePair> params = new ArrayList<NameValuePair>();
	 params.add(new BasicNameValuePair("Body", "Your first message from Twilio!"+otp));
	 params.add(new BasicNameValuePair("To", "+917065169421")); 
	 params.add(new BasicNameValuePair("From", TWILIO_NUMBER));
	 
	 MessageFactory messageFactory = client.getAccount().getMessageFactory();
	 Message message = messageFactory.create(params);
	 System.out.println(message.getSid());
	 } 
	 catch (TwilioRestException e) {
	 System.out.println(e.getErrorMessage());
	 }
	 return otp;
	 }
	 public void sendMail(String email) {
		 //JavaMailSender sender;
		 MimeMessage message = sender.createMimeMessage();
		 MimeMessageHelper helper = new MimeMessageHelper(message);
		 try {
		 helper.setTo(email);//
		 helper.setText("Your OTP is "+otp);
		 helper.setSubject("Mail From Spring Boot");
		 } catch (MessagingException e) {
		 e.printStackTrace();
		 }
		 sender.send(message);
		 }
	 }