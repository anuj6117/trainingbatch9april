package com.trading.services;

import java.util.Date;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trading.Enum.UserStatus;
import com.trading.Enum.WalletType;
import com.trading.domain.Role;
import com.trading.domain.User;
import com.trading.domain.UserOtp;
import com.trading.domain.Wallet;
import com.trading.dto.UserRoleDto;
import com.trading.repository.RoleRepository;
import com.trading.repository.UserOtpRepository;
import com.trading.repository.UserRepository;

@Service
public class UserService {

@Autowired
private	UserRepository userrepository;

@Autowired
private RoleRepository rolerepository;

@Autowired
private OtpService otpservice;

@Autowired
private EmailService emailservice;

@Autowired
private UserOtpRepository userotprepository;

Random rnd=new Random();
int otp=rnd.nextInt(10000);
		
	public int getOtp() {
			return otp;
		}

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
		if(userrepository.findByEmail(user.getEmail()) != null) {
			return "Oopss, this email is already registered";
		}
		if(userrepository.findByphoneNumber(user.getPhoneNumber())!= null)
		{
		return "Oopss, this number is already registered";
		}
		if(user.getPassword().equals(user.getConfirmpassword()))
		{
			if(userrepository.save(user)!= null) {
				user.setDate(new Date());
				user.setStatus(UserStatus.INACTIVE);
				//String email = user.getEmail();
				// otpservice.sendSMS(otp);
				// emailservice.sendEmail(otp);
				//userotp.settokenOTP(otp);
				//userotp.setEmail(email);
				//userotprepository.save(userotp);
				Wallet wallet = new Wallet();
				wallet.setwalletType(WalletType.FIATE);
				wallet.setuser(user);
				user.getWallet().add(wallet);
				userrepository.save(user);
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
		return userrepository.findAll();
}
	public Optional<User> getById(long userId)
	{ 
		return userrepository.findById(userId);
	}
	public User updateDetails(User user) {
		User userdb = null;
		userdb = userrepository.findOneByUserId(user.getUserId());
		if(userdb!= null)
		{
			userdb.setUserName(user.getUserName());
			userdb.setCountry(user.getCountry());
			userdb.setEmail(user.getEmail());
			userdb.setPassword(user.getPassword());
			userdb.setPhoneNumber(user.getPhoneNumber());
			return userrepository.save(userdb);
		}
	return null;
	}
	public String deleteById(long userId)
	{
		userrepository.deleteById(userId);
		return "Deleted";
	}
	public String assignNewRole(UserRoleDto userroledto)
	{
		User userdb = null;
		userdb = userrepository.findOneByUserId(userroledto.getuserId());
		Role roledb = rolerepository.findByRoleType(userroledto.getRoleType());
		if(roledb == null)
		{
			Role role = new Role();
			role.setRoleType(userroledto.getRoleType());
			userdb.getRole().add(role);
			userrepository.save(userdb);
			return "New Role has  been added and assigned";
		} 
		else 
		{
			userdb.getRole().add(roledb);
			userrepository.save(userdb);
			return "Existing role has been assigned";
		}
	}
}
