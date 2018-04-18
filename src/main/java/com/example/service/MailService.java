package com.example.service;



import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService
{
	@Autowired
    private JavaMailSender sender;

 

   

     String home() {

        try {

            sendEmail();

            return "Email Sent!";

        }catch(Exception ex) {

            return "Error in sending email: "+ex;

        }

    }
     String otp;
     public void getMailOtp(String otp)
     {
    	 this.otp=otp;
     }
   
 

    private void sendEmail() throws Exception{

        MimeMessage message = sender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message);

         

        helper.setTo("rupesh.sharma@oodlestechnologies.com");

        helper.setText("Your OTP is: "+otp);

        helper.setSubject("Otp Notification");

         

        sender.send(message);
     }
}
