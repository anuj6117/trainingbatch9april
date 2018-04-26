package com.trainingproject.util;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;

@Service
public class SmsUtil {

	public static final String ACCOUNT_SID = "AC96bc2c39bf6de6706774071503540727";
    public static final String AUTH_TOKEN = "096fae2bf0298825849740fdcdfee688";
    public static final String TWILIO_NUMBER = "+13203723220";
    
    JavaMailSender sender;
   
}
  

