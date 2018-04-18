package com.example.demo.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.enums.StatusEnum;
import com.example.demo.model.User;
import com.example.demo.model.UserOtp;
import com.example.demo.repository.UserOtpRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.utility.OtpGenerator;


@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private SendOtpMobile sendOtpMobile;
	@Autowired
	private SendMail send;
	
	private UserOtp userOtp;
	//private StatusEnum status;
	@Autowired
	private UserOtpRepository userOtpRepository;
	private User user;
	
		
	
	public String insertData(User user) {
		this.user=user;
		if((userRepository.findByEmail(user.getEmail()))!=null)
			return "user already exist";
		else
			{
			String otp = new String(OtpGenerator.generateOtp(4));
			try {
					send.sendEmail(otp,user.getEmail());
				}
			catch(Exception e) {System.out.println("mail not sent\t"+e);}
			try {
					sendOtpMobile.sendSMS(otp,user.getPhoneNumber());
			}
			catch(Exception e) {System.out.println("sms not sent\t"+e);}
			
			if((userRepository.save(user))!=null){
				if(userOtpRepository.save(new UserOtp(user.getEmail(),otp,user.getStatus().toString()))!=null)
						return "Data inserted";
			}
		}
		return "data not inserted";
	}		
		
	
	
	public boolean checkUser(UserOtp userOtp) {
		if((this.userOtp=userOtpRepository.findByOtp(userOtp.getOtp()))!=null) {
			UserOtp userotp = userOtpRepository.findByOtp(userOtp.getOtp());
			if((userotp.getOtp()).equals(userOtp.getOtp())){
				userOtpRepository.deleteById(userotp.getUserid());
				user=userRepository.findByEmail(userotp.getEmail());
				user.setStatus(StatusEnum.ACTIVE);
				userRepository.save(user);
				return true;
			}
			else
				return false;			
		}
		else
			return false;
	
	
		
	}

	public List<User> getAllUser() {
		return userRepository.findAll();
	}
	
	public String deleteUser(String email) {
			if((user = userRepository.findByEmail(email))!=null){
				userRepository.delete(user);
				return "user deleted sucesfully";
			}
			
			else 
				return ("user not exist");
		
	}

	public User getByUserId(Integer id) {
		if((user=userRepository.findOneById(id))!=null)
			return user;
		else
			return null ;
	}

	public String updateUser(User user) {
		if(userRepository.findById(user.getId())!=null) {
			userRepository.save(user);
			return "User Updated Successfully";
		}
		else
			return "User not Exist";
	}
}
