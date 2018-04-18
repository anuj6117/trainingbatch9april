package com.training.demo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training.demo.enums.UserStatus;
import com.training.demo.model.OtpVerification;
import com.training.demo.model.User;
import com.training.demo.repository.JpaRepo;
import com.training.demo.repository.OtpRepository;

@Service
public class SignUpService 
{
	@Autowired
	private JpaRepo jRepo;
	
	@Autowired
	private OTPService otps;
	
	@Autowired
	private EmailService ms;
	
	@Autowired
	private OtpRepository otpRepository;
	
	private OtpVerification otpVerification;
	
	
	public String addUser(User user)
	{
		boolean flag = jRepo.existsByEmail(user.getEmail());
		if(flag == false)
		{
			System.out.println("service hit");
			Random random=new Random();
			int otp=random.nextInt(5255);
			Date date;
			System.out.println("service hit before if");
			user.setStatus(UserStatus.INACTIVE);
			User existingUser=jRepo.save(user);
			date = new Date();
			user.setDate(date);
				if( existingUser != null)
				{
					System.out.println("service hit inside if.");
					otps.sendSms(otp);
					ms.home(otp);
					otpVerification = new OtpVerification();
					otpVerification.setUserId(user.getUserId());
					otpVerification.setOtp(otp);
					otpVerification.setEmail(user.getEmail());
					date = new Date();
					otpVerification.setDate(date);
					otpRepository.save(otpVerification);
					return "Otp sent successfully.";
					}
				else
				{
					System.out.println("Invalid Username.");
					return "Registration Failure.";
				}
		}
		else
		{
			System.out.println("Already existing Email or Username.");
			return "Already existing Email or Username.";
		}
	}
	public void verifyUserWithOtp(String email,Integer otp)
	{
		String t_email = otpVerification.getEmail() ;
		Integer t_otp = otpVerification.getOtp();
	
		OtpVerification otpVerification = otpRepository.findByEmail(email);
		String v_email = otpVerification.getEmail();
		int v_otp = otpVerification.getOtp();
		User t_user = jRepo.findByEmail(email);
		
		if(t_email.equals(v_email))
		{
			System.out.println("email is successfully verified"+t_email);
				if(t_otp.equals(v_otp))
				{
					System.out.println(t_otp+" otp is successfully verified"+t_otp);
				}
			otpRepository.delete(otpVerification);	
			t_user.setStatus(UserStatus.ACTIVE);
			jRepo.save(t_user);
				System.out.println("otpVerification table deleted.");
		}
		else
		{
			System.out.println("Sorry, invalid username or otp");
		}
	}	
	
	public List<User> getAllUsers()
	{
		List<User> l = new ArrayList<User>();
		l = jRepo.findAll();
		ListIterator iterator = l.listIterator();
		while(iterator.hasNext())
		{
			User use = (User)iterator.next();
			System.out.println(use);
			
		}
		return l;
	}
		public Optional<User> getUserById(Integer userId)
		{
		Optional<User> usrid = jRepo.findById(userId);
		if (usrid != null) {
			return usrid;
		} else {
			throw new NullPointerException("Id does not exist.");
		}
	}
}