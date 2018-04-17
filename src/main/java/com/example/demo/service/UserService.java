package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.utility.OtpGenerator;


@Service
public class UserService {
	@Autowired
	private UserRepository userrepo;
	@Autowired
	private SendOtpMobile som;
	@Autowired
	private SendMail send;
	
	private String otp = null;
	public String insertData(User user) {
		otp = new String(OtpGenerator.generateOtp(4));
		System.out.println(otp);
		
		try {			
			System.out.println(send.sendEmail(otp,user.getEmail()));
			System.out.println(som.sendSMS(otp,user.getMobile()));
			System.out.print("message sent----successfully---");
		}catch(Exception e) {System.out.println("---not sent--");System.out.println(e);}
			
		
		if(userrepo.save(user)!=null)
			return "Data inserted";
		else
			return "failed to inseted data";
		
	}

}
