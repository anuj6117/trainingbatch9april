package com.example.demo.service;



 
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
 
@Service
public class SendOtpMobile {
	public static final String ACCOUNT_SID = "AC996a808dd8eda7576f6af16c001ce0eb";
    public static final String AUTH_TOKEN = "3da3cc94d733eed1ccac6db921374400";
    public static final String TWILIO_NUMBER = "+12034098806";

    
    public String sendSMS(String otp,String mobile) {
        try {
            TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);            
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("Body", otp));
            params.add(new BasicNameValuePair("To", mobile)); //Add real number here
            params.add(new BasicNameValuePair("From", TWILIO_NUMBER));     
            MessageFactory messageFactory = client.getAccount().getMessageFactory();
            Message message = messageFactory.create(params);
            System.out.println(message);
            return "OTP Sent";           
        } 
        catch (TwilioRestException e) {
            System.out.println(e.getErrorMessage());
            return "Not Sent";
        }
    }


	
}