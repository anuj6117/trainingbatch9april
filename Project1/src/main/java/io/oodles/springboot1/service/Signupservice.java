package io.oodles.springboot1.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.oodles.springboot1.enums.Status;
import io.oodles.springboot1.model.StoreOTP;
import io.oodles.springboot1.model.Users;
import io.oodles.springboot1.repository.StoreOTPRepository;
import io.oodles.springboot1.repository.UsersRepository;

@Service
public class Signupservice {

	@Autowired
	public UsersRepository usersRepository;

	@Autowired
	public Mail mail;

	@Autowired
	public OTP otpgenerate;

	@Autowired
	OTPService otpService;
	
	@Autowired
	StoreOTPRepository storeOTPRepository;
	StoreOTP storeOTP=new StoreOTP();
	Users users=new Users();

	public void addUser(Users users) {

		Random rnd = new Random();
		Date date = new Date();
		int otp1 = rnd.nextInt(10000);
		
		System.out.println("::::::::::::::::::users hit:::");
		/*Users userDetails = new Users();
		userDetails.setCountry(users.getCountry());
		userDetails.setDate(users.getDate());
		userDetails.setEmail_id(users.getEmail_id());
		userDetails.setFullName(users.getFullName());
		userDetails.setPassword(users.getPassword());
		userDetails.setMobile_no(users.getMobile_no());*/
        users.setDate(date);
        users.getDate();
        users.setStatus(Status.INACTIVE);
        
		
		usersRepository.save(users);
			otpService.ValueMethod(users, otp1);
			mail.sendMail(otp1);
			otpgenerate.sendSMS(otp1);
			
			
		

	}

	public List<Users> getallusers() {
		
		return usersRepository.findAll();
	}

	public String auth(StoreOTP storeotp1) {
		/*storeOTP=storeOTPRepository.findOnebyOtp(otp);
		if(storeOTP.getUser_Email().equals(email)) {
			System.out.println("success");
		}
		else
			System.out.println("failure");
		*/
		//storeOTP=storeOTPRepository.findByOtp(storeotp1.getUser_otp());
		//users=usersRepository.findByEmailid(storeotp1.getUser_Email());
		if(storeOTP!=null) {
			if(storeotp1.getUserEmail().equals(storeotp1.getUserEmail())) {
				storeOTPRepository.deleteAll();
				users.setStatus(Status.ACTIVE);
				usersRepository.save(users);
				return "Success";
			}
			else
				return "Failure";
		}
		else return "Not found";
		
		
		
		}

	

	public Optional<Users> searchbyid(int id) {
		// TODO Auto-generated method stub
		return usersRepository.findById(id);
	}

	public Users update(Users users, int id) {
		// TODO Auto-generated method stub
		return usersRepository.save(users);
	}

	public void delete(int id) {
		// TODO Auto-generated method stub
		 usersRepository.deleteById(id);;
	}
	
}	
		
	
		
		// TODO Auto-generated method stub
		
	


