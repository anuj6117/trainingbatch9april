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
	public void userVerification(@RequestBody OtpVerification otpVerification)
	{
		if((otpVerification.getEmail()==null)||(otpVerification.getOtp()==null))
		{
			System.out.println("Inside Controller first if.");
			System.out.println("Email and otp is null");
		}
		else
		{
			signUpService.verifyUserWithOtp(otpVerification.getEmail(),otpVerification.getOtp());
			
		}
	 }
	 @RequestMapping(value="/getAllUsers", method=RequestMethod.GET)
	 public List<User> getAll()
	 {
			return signUpService.getAllUsers();
	  }
	
	 @RequestMapping(value="/update", method=RequestMethod.POST)
	 public String updateUser(@RequestBody User user)
	 {
	    return signUpService.updateUser(user);
	  }

	 @RequestMapping(value = "/getUser", method = RequestMethod.GET)
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

	 @RequestMapping(value="/delete", method=RequestMethod.GET)
	 public String deleteUser(@RequestParam("userId") Integer id)
	 {
		try {
			User t_user = userRepository.findByUserId(id);
			userRepository.delete(t_user);
			return "success";	
		}
		catch(Exception e)
		{
			return "fail";
		}		
	  }
	 
	@RequestMapping(value = "/assignRole", method = RequestMethod.POST)
	public String assignRole(@RequestBody UserRoleDto userRoleDto) 
	{
		System.out.println("----------------Inside assignRole method of signupcontroller");
			User user = null;
			try {
				System.out.println("Inside assignRole method of try signupcontroller--------------");
				user = signUpService.assignRoleToUser(userRoleDto);
			} catch (NullPointerException nullPointerException) {
				System.out.println("Inside assignRole method of-------catch signupcontroller");
				return "Role Cannot be assigned as : "+nullPointerException.getMessage();
			}
			return "Role Is Successfully Assigned.";
	 }
}