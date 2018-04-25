package com.training.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.training.demo.dto.DtoUser;
import com.training.demo.model.OtpVerification;
import com.training.demo.model.User;
import com.training.demo.repository.UserRepository;
import com.training.demo.service.SignUpService;

@RestController
public class SignUpController {
	@Autowired
	private SignUpService signUpService;

	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String insertUser(@RequestBody User user) {
		System.out.println("controller hit");
		String password=user.getPassword().trim();
		String username=user.getuserName();
		int length=password.length();
	    int unamelength=username.length();
		if((length!=0)&&(unamelength!=0))
		{
		String newUser = signUpService.addUser(user);
		if (newUser != null) {
			return newUser;
		} else {
			return "failure";
		}
		}
		
		else
		{
			return "enter valid password or user name";
			
		}
	}

	@RequestMapping(value = "/verify", method = RequestMethod.POST)
	public String userVerification(@RequestBody OtpVerification otpVerification) {
		if ((otpVerification.getEmail() == null) || (otpVerification.getOtp() == null)) {
			System.out.println("Inside Controller first if.");
			System.out.println("Email and otp is null");
			return "otp not found";
		} else {
			 return signUpService.verifyUserWithOtp(otpVerification.getEmail(), otpVerification.getOtp());

		}
	}

	@RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
	public List<User> getAll() {
		return signUpService.getAllUsers();
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateUser(@RequestBody User user) {
		return signUpService.updateUser(user);
	}

	@RequestMapping(value = "/assignrole", method = RequestMethod.POST)
	public String assignRole(@RequestBody DtoUser userdto) {
		User user = null;
		try {
			user = signUpService.assignRoleToUser(userdto);
		} catch (Exception e) {
			return "Role Cannot be assigned as : " + e.getMessage();
		}
		return "Role Is Successfully Assigned.";
	}

	@RequestMapping(value = "/getByUserId", method = RequestMethod.GET)
	public Optional<User> getUserById(@RequestParam("userId") Integer userId) {
		Optional<User> obj = null;
		try {
			obj = signUpService.getUserById(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String deleteUser(@RequestParam("userId") Integer id) {
		try {
			User t_user = userRepository.findByUserId(id);
			userRepository.delete(t_user);
			return "success";
		} catch (Exception e) {
			return "fail";
		}
	}
}