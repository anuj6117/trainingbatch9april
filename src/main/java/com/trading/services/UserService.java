package com.trading.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trading.domain.User;
import com.trading.repository.UserRepo;

@Service
public class UserService {
	
	@Autowired
private	UserRepo userrepo;
	@Autowired
	private OtpService otpservice;
public String insertDetails(User user) {
	if(!(userrepo.save(user)== null)) {
		otpservice.sendSMS();
		return "Success";
	}
	else 
		{
		return "Failure";
		}
	
}

}
