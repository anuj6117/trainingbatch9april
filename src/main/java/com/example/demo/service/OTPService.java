package com.example.demo.service;

import java.util.*;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;

@Service
public class OTPService
{
	public static final String ACCOUNT_SID = "AC78c562f962e41ad679b96a2eb34e720a";
    public static final String AUTH_TOKEN = "5f43f85db0a85d9f4b549940d6915d80";
    public static final String TWILIO_NUMBER = "+16022231950";
    
    
    public void sendSms(int otp) {
    	

		try {
			TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

			// Build a filter for the MessageList
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("Body", "Your otp is : "+otp));
			params.add(new BasicNameValuePair("To", "+919742913034")); // Add real number here
			params.add(new BasicNameValuePair("From", TWILIO_NUMBER));

			MessageFactory messageFactory = client.getAccount().getMessageFactory();
			Message message = messageFactory.create(params);
			
		} catch (TwilioRestException e) {
			System.out.println(e.getErrorMessage());
		}
	}
}
