package com.trading.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;

@Service
public class OtpService {

	public static final String ACCOUNT_SID = "AC45ab2f03be113aaa4a0c8fad6419ebc3";
	public static final String AUTH_TOKEN = "5208fdc172efe515843a64cd7e805a69";
	public static final String TWILIO_NUMBER = "+13173421074";

	@Autowired
	UserService userservice = new UserService();

	public void sendSMS(int otp) {
		try {
			TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("Body",
					"Your account has been succesfully created. Please verify it using OTP  " + userservice.otp));
			params.add(new BasicNameValuePair("To", "+919999686339")); // Add real number here
			params.add(new BasicNameValuePair("From", TWILIO_NUMBER));
			MessageFactory messageFactory = client.getAccount().getMessageFactory();
			Message message = messageFactory.create(params);
			System.out.println(message.getSid());
		} catch (TwilioRestException e) {
			System.out.println(e.getErrorMessage());
		}
	}
}
