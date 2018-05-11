package io.oodles.springboot1.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
@Service
public class Mail {
	@Autowired
	private JavaMailSender sender;
	
	public void sendMail(int otp1,String email) {
 MimeMessage message = sender.createMimeMessage();
 MimeMessageHelper helper = new MimeMessageHelper(message);
 try {
 helper.setTo(email);
 helper.setText("Your OTP is "+otp1);
 helper.setSubject("Mail From Spring Boot");
 } catch (MessagingException e) {
 e.printStackTrace();
 
 }
 sender.send(message);

 }

	

}
