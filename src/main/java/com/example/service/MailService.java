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

 

   

     String home(String mail) {

        try {

            sendEmail(mail);

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
   
 

    private void sendEmail(String mailid) throws Exception{

        MimeMessage message = sender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message);

         

        helper.setTo( mailid);

        helper.setText("Your OTP is: "+otp);

        helper.setSubject("Otp Notification");

         

        sender.send(message);
     }
}
