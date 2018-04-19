package io.oodles.springboot1.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.oodles.springboot1.model.StoreOTP;
import io.oodles.springboot1.model.Users;
import io.oodles.springboot1.repository.StoreOTPRepository;

@Service
public class OTPService {
	@Autowired
	StoreOTPRepository storeOTPRepository;
	Date date=new Date();
	Integer OTPValue;
	String EmailValue;
	public void ValueMethod(Users users,Integer otp )
	{
		this.OTPValue=otp;
		this.EmailValue=users.getEmailid();
		
		StoreOTP storeotp=new StoreOTP(OTPValue, EmailValue, new Date());
		storeOTPRepository.save(storeotp);
	}

}
