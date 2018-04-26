package com.traningproject1.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;

@Service
public class SmsService {
	public static final String ACCOUNT_SID = "ACfc4aa36add288c23291fa884d4163206";
    public static final String AUTH_TOKEN = "3ddee8399b4cf80fbf30fe3ffd4e7d3c";
    public static final String TWILIO_NUMBER = "+14053478355";
    
    
    public void sendSms(Integer otp) {
    	
		try {
			TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("Body", "Hi Prajesh your otp is : "+otp));
			params.add(new BasicNameValuePair("To", "+918887863206"));
			params.add(new BasicNameValuePair("From", TWILIO_NUMBER));

			MessageFactory messageFactory = client.getAccount().getMessageFactory();
			messageFactory.create(params);
			
		} catch (TwilioRestException e) {
			System.out.println(e.getErrorMessage());
		}
	}
}
