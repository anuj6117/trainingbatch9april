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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.trainingproject.repository.UserRepository;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
@Service
public class UserOTPService {

	// Find your Account Sid and Token at twilio.com/user/account
	 //public static final String ACCOUNT_SID = "AC16db0ba5181539a6bb444b511549d309"; //test sid
	 public static final String ACCOUNT_SID = "AC2e1ebaf4bdd8ee0e0af53802988fdea9";
	 //public static final String AUTH_TOKEN = "154809d683e2eafe73909cf0201bc84a"; //test
	 public static final String AUTH_TOKEN = "a9e72dacc44a2af36079544e013079e6";
	 public static final String TWILIO_NUMBER = "+15136571810";
	 /*@Autowired
	 Mail mail;*/
	 @Autowired
	 JavaMailSender sender;
	
	 //public static final String TWILIO_NUMBER = "+919717562586";+15105647903 (513) 657-4064 
	 /*
	 public void getphone(){
	 Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

	 IncomingPhoneNumber number = IncomingPhoneNumber
	 .creator(new PhoneNumber("+15005550006"))
	 .setVoiceUrl("http://demo.twilio.com/docs/voice.xml")
	 .create();

	 System.out.println(number.getSid());
	 }
	 */
	 static Integer otp;
	 public Integer sendSMS() {
	 try {
		 Random rnd=new Random();
			otp=rnd.nextInt(10000);
		
	 TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
	 
	 // Build a filter for the MessageList
	 List<NameValuePair> params = new ArrayList<NameValuePair>();
	 //params.add(new BasicNameValuePair("Body", "Hello, World!"));
	 params.add(new BasicNameValuePair("Body", "Your first message from Twilio!"+otp));
	 params.add(new BasicNameValuePair("To", "+917065169421")); //Add real number here
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
	 public void sendMail() {
		 //JavaMailSender sender;
		 MimeMessage message = sender.createMimeMessage();
		 MimeMessageHelper helper = new MimeMessageHelper(message);
		 try {
		 helper.setTo("amit.patel@oodlestechnologies.com");
		 helper.setText("Your OTP is "+otp);
		 helper.setSubject("Mail From Spring Boot");
		 } catch (MessagingException e) {
		 e.printStackTrace();
		 //return "Error while sending mail ..";
		 }
		 sender.send(message);
		 //return "Mail Sent Success!";
		 }
	 }//UsersOTPService