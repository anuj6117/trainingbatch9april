package com.example.demo.utilities;

import com.example.demo.model.User;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Account;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import com.twilio.sdk.resource.instance.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;

@Service
public class SendMailNSms {

    @Autowired
    JavaMailSender sender;


    public void  sendSms(User user,Integer otp) throws TwilioRestException {
        String ACCOUNT_SID="ACe8b3b8eee0b2139d36bf1f6cedf7bc29";
        String AUTH_TOKEN="3da80179c158c1544b08045c4d90d079";
        String twilioPhoneNumber="+15759047452";
        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

        Account account = client.getAccount();

        MessageFactory messageFactory = account.getMessageFactory();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("To","+91"+ user.getPhoneNumber()));
        params.add(new BasicNameValuePair("From", twilioPhoneNumber));
        params.add(new BasicNameValuePair("Body","Your otp is"+otp));

        Message message=messageFactory.create(params);
        System.out.println(message.getBody());
    }

    public void sendMail(User user,Integer otp) throws  Exception{
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message);

        try{
            mimeMessageHelper.setTo(user.getEmail());
            mimeMessageHelper.setText("Your otp is "+otp);
            mimeMessageHelper.setSubject("otp for verification");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        sender.send(message);
    }
}
