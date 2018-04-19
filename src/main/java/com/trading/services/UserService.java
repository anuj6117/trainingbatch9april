package com.trading.services;

import java.util.Date;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trading.Enum.StatusType;
import com.trading.domain.Role;
import com.trading.domain.User;
import com.trading.domain.UserOtp;
import com.trading.dto.UserRoleDto;
import com.trading.repository.RoleRepo;
import com.trading.repository.UserOtpRepo;
import com.trading.repository.UserRepo;

@Service
public class UserService {
	
	@Autowired
private	UserRepo userrepo;
	@Autowired
private RoleRepo rolerepo;
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
	else 
	{
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
	User userdb = null;
	userdb = userrepo.findOneByUserId(user.getUserId());
	System.out.println(userdb);
if(userdb!= null)
{
	userdb.setUserName(user.getUserName());
	userdb.setCountry(user.getCountry());
	//userdb.setDate(new Date());
	//userdb.setStatus(StatusType.Active);
	userdb.setEmail(user.getEmail());
	userdb.setPassword(user.getPassword());
	userdb.setPhoneNumber(user.getPhoneNumber());
	 return userrepo.save(userdb);

}

return null;}

public String deleteById(long userId)
{
	userrepo.deleteById(userId);
	return "Deleted";
}

public String assignNewRole(UserRoleDto userroledto)
{
	
	System.out.println(userroledto.getUserid()+"hi how r u  "+ userroledto.getRoleType());
	User userdb = null;
	userdb = userrepo.findByUserId(userroledto.getUserid());
	System.out.println("hi vanshika" +userdb);
	Role role = rolerepo.findByRoleType(userroledto.getRoleType());
	System.out.println("........................." +role);
	userdb.getRole().add(role);
	
	
	
	
	
	
	
	userrepo.save(userdb);
return "success";
}
}
