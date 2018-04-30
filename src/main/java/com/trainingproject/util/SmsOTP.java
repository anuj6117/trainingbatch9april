package com.trainingproject.util;

	import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailSenderValidatorAutoConfiguration;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
	
	
	@Service
	public class SmsOTP {
		public static final String ACCOUNT_SID = "AC96bc2c39bf6de6706774071503540727";
	    public static final String AUTH_TOKEN = "096fae2bf0298825849740fdcdfee688";
	    public static final String TWILIO_NUMBER = "+13203723220";
		@Autowired
		JavaMailSender sender;
		
		
		static Integer otp;
		
		public Integer sendSMS() {
		try {
		Random rnd = new Random();
		otp = rnd.nextInt(10000);
		
		TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
		
		// Build a filter for the MessageList
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		//params.add(new BasicNameValuePair("Body", "Hello, World!"));
		params.add(new BasicNameValuePair("Body", "Your first message from Twilio!"+otp));
		params.add(new BasicNameValuePair("To", "+919560457797")); //Add real number here
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
	
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		try {
		helper.setTo(email);//
		helper.setText("Your OTP is "+otp);
		helper.setSubject("Mail From Spring Boot");
		} 
		catch (MessagingException e) {
		e.printStackTrace();
	
		}
		try {
	
		sender.send(message);
		}
		catch(MailSendException e) {
			
			
		}
		}
		}

