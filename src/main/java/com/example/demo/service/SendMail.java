package com.example.demo.service;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class SendMail  {


	@Autowired
	private  JavaMailSender sender;

	
	public String sendEmail(String msg, String email) throws Exception{		
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setTo(email);
		helper.setText(msg);
		helper.setSubject("verficataion otp");
		sender.send(message);
		return "otp sent";

	}
	
}
