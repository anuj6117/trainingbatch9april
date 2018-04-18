package com.example.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.User;
import com.example.model.UserOtpTable;
import com.example.repository.OTPjpaRepository;

@Service
public class OtpTableService 
{
	@Autowired
	OTPjpaRepository otpjparepository;
	
	Date date =new Date();
	String str=new Date()+"M";
	
//private User user = new User();
String OtpValue;
String EmailValue;
public void valuemethod(User user,String otp)
{
	this.OtpValue=otp;
	this.EmailValue=user.getEmail();
	UserOtpTable userOtpTable=new UserOtpTable(OtpValue,EmailValue,str);
	//System.out.println(userOtpTable);
	//System.out.println(userOtpTable);
	//UserOtpTable userOtpTable1=
			otpjparepository.save(userOtpTable);
}

}
