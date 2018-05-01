package com.example.demo.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.model.VerifyOtp;
import com.example.demo.service.SignUpService;
import com.example.demo.utility.ResponseHandler;

@RestController
public class SignUpController {
	@Autowired
	private SignUpService signUpService;
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ResponseEntity<Object> saveUser(@RequestBody User user)
	{
		Map<String, Object> result = null;

		try {
			result = signUpService.addUser(user);

			if (result.get("isSuccess").equals(true))
			{
				System.out.println("if block of Controller.");
				return ResponseHandler.generateResponse(HttpStatus.OK, true, result.get("message").toString(), result);
			} 
			else
			{
				System.out.println("else block of controller.");
				return ResponseHandler.generateResponse(HttpStatus.OK, false, result.get("message").toString(), result);
			}

		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}
	}
	
	@RequestMapping(value = "/verifyuser", method = RequestMethod.POST)
	public String verifyUser(@RequestBody VerifyOtp obj) 
	{
		if (((obj.getEmailId() != null) && (obj.getTokenOtp() != null)))
		{
			return signUpService.verifyUserWithOtp(obj.getEmailId(), obj.getTokenOtp());

		} 
		else
		{
			return "Otp or email is null.";
		}
	}

	@RequestMapping(value = "/getallusers", method = RequestMethod.GET)
	public List<User> getAllUsers() 
	{
		return signUpService.getAllUsers();
		
	}

	@RequestMapping(value = "/getbyuserid", method = RequestMethod.GET)
	public Optional<User> getUserById(@RequestParam("userId") Integer id) 
	{
		Optional<User> obj = null;
		try {
			obj = signUpService.getuserById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	@RequestMapping(value = "/updateuser", method = RequestMethod.POST)
	public User updateUserById(@RequestBody User user) {
		return signUpService.update(user);
	}

	@RequestMapping(value = "/deleteuser", method = RequestMethod.GET)
	public String delete(@RequestParam("userId") Integer id) 
	{
		if (id != null)
		{
			signUpService.delete(id);
			return "User deleted of given id.";
			
		}
		return "User does not exist of given id.";
	}
	
	
}
