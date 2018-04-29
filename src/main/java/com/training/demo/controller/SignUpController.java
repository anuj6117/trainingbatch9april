package com.training.demo.controller;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.training.demo.dto.UserRoleDto;
import com.training.demo.model.OtpVerification;
import com.training.demo.model.User;
import com.training.demo.repository.UserRepository;
import com.training.demo.service.SignUpService;

@RestController
public class SignUpController 
{
	@Autowired
	private SignUpService signUpService;
	
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping(value="/signup", method=RequestMethod.POST)
	public String insertUser(@RequestBody User user)
	{ 
		System.out.println("controller hit");
		String newUser=signUpService.addUser(user);
		if(newUser != null)
		{
			return newUser;
		}
		else
		{
			return "failure";
		}
	 }
	
	@RequestMapping(value = "/verify", method = RequestMethod.POST)
	public String userVerification(@RequestBody OtpVerification otpVerification)
	{
		if((otpVerification.getEmail()!=null) && (otpVerification.getTokenOTP()!=null))
		{
			return signUpService.verifyUserWithOtp(otpVerification.getEmail(),otpVerification.getTokenOTP());		
		}
		else
		{
			throw new NullPointerException("insufficient information.");
		}
		
	 }
	 @RequestMapping(value="/getallusers", method=RequestMethod.GET)
	 public List<User> getAll()
	 {
			return signUpService.getAllUsers();
	  }
	
	 @RequestMapping(value="/updateuser", method=RequestMethod.POST)
	 public String updateUser(@RequestBody User user)
	 {
	    return signUpService.updateUser(user);
	  }

	 @RequestMapping(value = "/getbyuserid", method = RequestMethod.GET)
	 public Optional<User> getUserById(@RequestParam("userId") Integer userId) 
	 {
			Optional<User> obj = null;
			try {
				obj = signUpService.getUserById(userId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return obj;
	  }

	 @RequestMapping(value="/deleteuser", method=RequestMethod.GET)
	 public String deleteUser(@RequestParam("userId") Integer id)
	 {
		try {
			User t_user = userRepository.findByUserId(id);
			userRepository.delete(t_user);
			return "Your Account has been succesfully deleted.";	
		}
		catch(Exception e)
		{
			return "Invalid user id.";
		}		
	  }
	 
	@RequestMapping(value = "/assignrole", method = RequestMethod.POST)
	public String assignRole(@RequestBody UserRoleDto userRoleDto) 
	{
		String msg = null;
			if(userRoleDto != null) {
			msg = signUpService.assignRoleToUser(userRoleDto);
			}
			return msg; 
	}
}