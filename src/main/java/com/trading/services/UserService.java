package com.trading.services;

import java.util.Date;
import java.util.Optional;
import java.util.Random;


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

		

	//private final Logger  logger = LoggerFactory.getLogger(this.getClass());
@Autowired
private EmailService emailservice;


@Autowired
private UserOtpRepo userotprepo;
//logger.info("jjjjjjjjjjjj"+otp);

private UserOtp userotp = new UserOtp();
public String insertDetails(User user) throws Exception {
	if(user.getPassword()== "")
	{
		return "Please enter a valid password";
	}
	if(user.getUserName()==null)
	{
		return "User name cannot be null";
	}
	if(userrepo.findByEmail(user.getEmail()) != null) {
		return "Oopss, this email is already registered";

	}
	if(userrepo.findByphoneNumber(user.getPhoneNumber())!= null)
	{
		return "Oopss, this number is already registered";
	}
	if(user.getPassword().equals(user.getConfirmpassword()))
	{
		
		
	
	System.out.println(userrepo.save(user)!= null);
	
	if(userrepo.save(user)!= null) {
		
	 user.setDate(new Date());
		user.setStatus(StatusType.INACTIVE);
		userrepo.save(user);
		String email = user.getEmail();
	 otpservice.sendSMS(otp);
	 emailservice.sendEmail(otp);
	 userotp.settokenOTP(otp);
	 userotp.setEmail(email);
	 userotprepo.save(userotp);
	 
	
		return "Success";
	}
	else 
		{
		return "Failure";
		}
	}
	else {
		return "Please re-enter your password";
	}
}
public Iterable <User> getDetails(){
	
	return userrepo.findAll();
}

public Optional<User> getById(long userId)

{ 
	
	return userrepo.findById(userId);
}

User us;
public User updateDetails(User user) {
if(userrepo.findById(user.getUserId())!= null)
{
	user.setUserName(user.getUserName());
	user.setCountry(user.getCountry());
	user.setDate(new Date());
	user.setStatus(StatusType.Active);
	user.setEmail(user.getEmail());
	user.setPassword(user.getPassword());
	user.setPhoneNumber(user.getPhoneNumber());
	return userrepo.save(user);

}

return null;}

public String deleteById(long userId)
{
	userrepo.deleteById(userId);
	return "Deleted";
}
}
