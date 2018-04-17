package com.trading.services;

import java.util.Date;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trading.Enum.StatusType;
import com.trading.domain.User;
import com.trading.domain.UserOtp;
import com.trading.repository.UserOtpRepo;
import com.trading.repository.UserRepo;

@Service
public class UserService {
	
	@Autowired
private	UserRepo userrepo;
	@Autowired
	private OtpService otpservice;
	
		Random rnd=new Random();
		int otp=rnd.nextInt(10000);
		
	public int getOtp() {
			return otp;
		}

		

	private final Logger  logger = LoggerFactory.getLogger(this.getClass());
@Autowired
private EmailService emailservice;

@Autowired
private UserOtpRepo userotprepo;
//logger.info("jjjjjjjjjjjj"+otp);

private UserOtp userotp = new UserOtp();
public String insertDetails(User user) throws Exception {
	logger.info("------"+user.getCountry());
	logger.info("jjjjjjjjjjjj"+otp);
	System.out.println(userrepo.save(user)!= null);
	if(userrepo.save(user)!= null) {
		
		user.setDate(new Date());
		user.setStatus(StatusType.INACTIVE);
		userrepo.save(user);
		String emailid = user.getEmailId();
	 otpservice.sendSMS(otp);
emailservice.sendEmail(otp);
userotp.settokenOTP(otp);
userotp.setEmailid(emailid);
userotprepo.save(userotp);
 
		return "Success";
	}
	else 
		{
		return "Failure";
		}
	
}



}
