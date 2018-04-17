package com.example.demo.service;

import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.enums.Status;
import com.example.demo.model.User;
import com.example.demo.model.VerifyOtp;
import com.example.demo.repository.JpaRepo;
import com.example.demo.repository.VerifyOtpRepository;

@Service
public class SignUpService
{
	@Autowired
	private JpaRepo jpaRepository;
	
	@Autowired
	private OTPService otpService;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private VerifyOtpRepository verifyOtpRepository;
	
	@Autowired
	private VerifyOtp verifyOtp;
	
	
	public String addUser(User user)
	{
		Random randomNumber=new Random();
		int otp=randomNumber.nextInt(10000);
		Date date=new Date();
		user.setDate(date);
		user.setSt(Status.INACTIVE);
		
		if( (jpaRepository.save(user) != null))
		{
			otpService.sendSms(otp);
			mailService.sendMail(otp,user.getEmail());
			
			
			
			return "Successfully sent";
		}
		else
		{
			return "Failure";
		}
	}
}
