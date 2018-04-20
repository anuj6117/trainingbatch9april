package com.example.demo.service;

import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.enums.Status;
import com.example.demo.model.User;
import com.example.demo.model.VerifyOtp;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VerifyOtpRepository;

@Service
public class SignUpService
{
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OTPService otpService;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private VerifyOtpRepository verifyOtpRepository;
	

	private VerifyOtp verifyOtp=new VerifyOtp();
	
	private User user;
	
	private Integer otp;
	
	public String addUser(User user)
	{		
		if((userRepository.findByEmail(user.getEmail()) == null ))
		{
			Random randomNumber=new Random();
			otp=randomNumber.nextInt(10000);
			Date date=new Date();
			user.setDate(date);
			user.setStatus(Status.INACTIVE);
			if((userRepository.save(user) != null))
			{
				otpService.sendSms(otp);
				mailService.sendMail(otp,user.getEmail());
				verifyOtp.setId(user.getUserId());
				verifyOtp.setTokenOtp(otp);
				verifyOtp.setEmailId(user.getEmail());
				verifyOtp.getEmailId();
				verifyOtp.setDate(date);
			
				verifyOtpRepository.save(verifyOtp);
			
				return "Successfully sent";
			}
			else
			{
				return "Failure";
			}
		}
		else
		{
			return "Already existing user";
		}
	}
	
	public void verifyUserWithOtp(String emailId,Integer otp)
	{
		String t_email = verifyOtp.getEmailId();
		Integer t_otp = verifyOtp.getTokenOtp();
	
		VerifyOtp vOtp = verifyOtpRepository.findByEmailId(emailId);
		String v_email = vOtp.getEmailId();
		Integer v_otp = vOtp.getTokenOtp();
		
		if(t_email.equals(v_email))
		{
			System.out.println("email is successfully verified"+t_email);
				if(t_otp.equals(v_otp))
				{
					System.out.println(t_otp+" otp is successfully verified"+t_otp);
				}
				
				User user =userRepository.findByEmail(emailId);
				System.out.println(userRepository.findByEmail(emailId));
				user.setStatus(Status.ACTIVE);
				verifyOtpRepository.delete(verifyOtp);	
				System.out.println("otpVerification table deleted.");
		}
		else
		{
			System.out.println("Sorry, invalid username or otp");
		}
	}	
		
	public Optional<User> getuserById(Integer id) {
		
		Optional<User> usrid = userRepository.findById(id);
		if (usrid != null) {
			return usrid;
		} else {
			throw new NullPointerException("Id doen't be exist");
		}
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
		
	}
	
	public User update(User user){
		
		User userdb =null;
		userdb=userRepository.findOneByUserId(user.getUserId());
		userdb.setUserName(user.getUserName());
		//userdb.setEmail(user.getEmail());
		//userdb.setPhoneNumber(user.getPhoneNumber());
		userdb.setCountry(user.getCountry());
		//userdb.setPassword(user.getPassword());
		
		return userRepository.save(userdb);
	}

	public void delete(Integer id) {
		
		 userRepository.deleteById(id);
	}
	
}
