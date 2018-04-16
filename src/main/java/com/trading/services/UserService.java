package com.trading.services;

import java.util.Date;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trading.Enum.StatusType;
import com.trading.domain.User;
import com.trading.repository.UserRepo;

@Service
public class UserService {
	
	@Autowired
private	UserRepo userrepo;
	@Autowired
	private OtpService otpservice;

Random rnd=new Random();
		int otp=rnd.nextInt(10000);
	private final Logger  logger = LoggerFactory.getLogger(this.getClass());
@Autowired
private EmailService emailservice;
public String insertDetails(User user) throws Exception {
	logger.info("------"+user.getCountry());
	
	if(!(userrepo.save(user)== null)) {
		
		
		user.setDate(new Date());
		user.setStatus(StatusType.INACTIVE);
		userrepo.save(user);
	otpservice.sendSMS(otp);
		emailservice.sendEmail();
		
		return "Success";
	}
	else 
		{
		return "Failure";
		}
	
}

}
