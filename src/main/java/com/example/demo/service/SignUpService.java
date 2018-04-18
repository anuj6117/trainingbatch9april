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
		Random randomNumber=new Random();
		otp=randomNumber.nextInt(10000);
		Date date=new Date();
		user.setDate(date);
		user.setStatus(Status.INACTIVE);
		//user.setOtp(otp);userRepository
		
		if( (userRepository.save(user)!= null))
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
				verifyOtpRepository.delete(verifyOtp);	
				System.out.println("otpVerification table deleted.");
		}
		else
		{
			System.out.println("Sorry, invalid username or otp");
		}
	}	
		
		
		/*String v_email=verifyOtp.getEmailId();
		int  v_otp=Integer.parseInt(verifyOtp.getTokenOtp());
		System.out.println(v_email);
		System.out.println(v_otp);
		
		VerifyOtp v = verifyOtpRepository.findByEmailId(emailId);
		String email = v.getEmailId();
		int otp1 = Integer.parseInt(v.getTokenOtp());
		System.out.println(email+"\t"+otp1);
		System.out.println(v+"++++++++++++++++++++++"+v.getEmailId());
		System.out.println(v.getEmailId().equals(emailId)+"\t"+(v.getTokenOtp().equals(otp)));
		if(emailId.equals(email)&&otp1==v_otp)
		{
			User user123 = userRepository.findByEmail(emailId);
			user123.setStatus(Status.ACTIVE.toString());
			verifyOtpRepository.deleteById(v.getId());
				return "user otp verified";			
		}
		else
		{
			return "Email and Otp verification is failed.";
		}*/
		
	
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
		
		return userRepository.save(user);
	}



	public void delete(Integer id) {
		
		 userRepository.deleteById(id);
	}
	
}
