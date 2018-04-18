package com.example.service;


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
	/*public static final String ACCOUNT_SID = "ACa73a82b97b13021b975d3efce7460b63";
    public static final String AUTH_TOKEN = "c1ddb549bba6ddc0f6b9a991c1438a8c";
    public static final String TWILIO_NUMBER = "+16672135566";
    */
	public static final String ACCOUNT_SID = "AC78c562f962e41ad679b96a2eb34e720a";
    public static final String AUTH_TOKEN = "5f43f85db0a85d9f4b549940d6915d80";
    public static final String TWILIO_NUMBER = "+16022231950";
    
    public void sendSms(int otp) {
    	System.out.println("the number is 9540263680");

		try {
			TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

			// Build a filter for the MessageList
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("Body", "Here's the otp= " +otp));
			params.add(new BasicNameValuePair("To", "+919742913034")); 
			params.add(new BasicNameValuePair("From", TWILIO_NUMBER));

			MessageFactory messageFactory = client.getAccount().getMessageFactory();
			Message message = messageFactory.create(params);
			System.out.println("message otp "+message);
			
		} catch (TwilioRestException e) {
			System.out.println(e.getErrorMessage());
		}
	}
}
