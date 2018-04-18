package com.example.service;

import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.enums.UserStatus;
import com.example.model.User;
//import com.example.model.UserOtpTable;
import com.example.repository.jpaRepository;

@Service
public class SignUpService 
{
	@Autowired
	private jpaRepository jRepository;
//	@Autowired
//	private UserOtpTable userotptable;
	private Integer otpNum;
	
	@Autowired
	private OTPService otps;
	
	@Autowired
	private MailService mailController;
	@Autowired
	private OtpTableService otptableservice;
	private User user;
	
	public String addUser(User user)
	{
	
       String date=new Date()+"";
       
	    
		Random random=new Random();
		 otpNum=random.nextInt(8999)+1000;
		 String otpval=""+otpNum;
		 System.out.println(user);
		 user.setDate(date);
		 user.setStatus((UserStatus.INACTIVE));
		
		 
		 if( !(jRepository.save(user) == null))
		{
			
			otps.sendSms(otpNum);
			mailController.getMailOtp(otpval);
			otptableservice.valuemethod(user,otpval);
			mailController.home();
			
			return "Sent Successfully ";
		}
		else
		{
			return "Failure";
		}
	}
	public String updateuser(Integer id)
	 {  
		System.out.println("checking user "+user);
		user=jRepository.findByUserId(id);
		 String date=new Date()+"";
		 user.setDate(date);
		 user.setStatus(user.getStatus());
		
		if(!(jRepository.save(user)==null))
		{
			return "updates successfully";
		}
		else
			return "Not updated";
		
	 }
	
}

