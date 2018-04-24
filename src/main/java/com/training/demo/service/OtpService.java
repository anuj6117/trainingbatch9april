package com.training.demo.service;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training.demo.model.OtpVerification;
import com.training.demo.model.User;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
	@Service
	class OTPService 
	{
		
		private static User user = new User();
		private static OtpVerification otpVerification = new OtpVerification();
		public static final String ACCOUNT_SID = "AC78c562f962e41ad679b96a2eb34e720a";
	    public static final String AUTH_TOKEN = "5f43f85db0a85d9f4b549940d6915d80";
	    public static final String TWILIO_NUMBER = "+16022231950";
	    
	    
	    public void sendSms(int otp, String phoneNo) {
			try {
					
				TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

				// Build a filter for the MessageList
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				
				params.add(new BasicNameValuePair("Body", "your Otp is = "+otp+" ,Sent by Rohit Godara."));
				params.add(new BasicNameValuePair("To", "+91"+phoneNo)); // Add real number here
				params.add(new BasicNameValuePair("From", TWILIO_NUMBER));				
				MessageFactory messageFactory = client.getAccount().getMessageFactory();
				Message message = messageFactory.create(params);
								
			} catch (TwilioRestException e) {
				System.out.println(e.getErrorMessage());
			}
		}
	}