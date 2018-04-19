package com.trainingproject.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trainingproject.domain.userOTP;
import com.trainingproject.domain.User;
import com.trainingproject.enums.Status;
import com.trainingproject.repository.UserOTPRepository;
import com.trainingproject.repository.UserRepository;
import com.trainingproject.domain.User;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserOTPRepository otpRepository;
	@Autowired
	private UserOTPService userOTPService;
	private userOTP otp =new userOTP();
	
	public List<User> getAllUsers() {
		List<User> list = new ArrayList<User>();
		userRepository.findAll()
		.forEach(list::add);
		return list;			
	}
	
	static Integer otp1;
	public void addUsers(User user) {
		user.setCreatedOn(new Date());
		user.setStatus(Status.INACTIVE);
		userRepository.save(user);
		
		String str=user.getEmail();
		
		if(!(userRepository.save(user)==null)) {
			otp1=userOTPService.sendSMS();
			userOTPService.sendMail();
			otp.setDate(new Date());
			otp.setUserEmail(str);
			otp.setOtp(otp1);
			otpRepository.save(otp);
		}
		
	}
	
	public Optional<User> getById(Integer userId) {
		// TODO Auto-generated method stub
		return userRepository.findById(userId);
	}
	
	public void updateUsers(User user) {
		userRepository.save(user);
	}
	
	public void deleteUsers(Integer userId) {
		userRepository.deleteById(userId);
	}
	
}
