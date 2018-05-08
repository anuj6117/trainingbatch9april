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

		try
		{
			result = signUpService.addUser(user);

			if (result.get("isSuccess").equals(true))
			{  
				return ResponseHandler.generateResponse(HttpStatus.OK, true, result.get("message").toString(), result);
			} 
			else
			{
				String newMessage=result.get("message").toString();
				result.remove("message");
				result.remove("isSuccess");
				//System.out.println(":::::::::::"+result);
				return ResponseHandler.generateResponse(HttpStatus.OK, false, newMessage, result);
			}
		} 
		catch (Exception e) 
		{
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
	public Object getByUserId(@RequestParam("userId") Integer userId)
	{
		return signUpService.getByUserId(userId);
	}

	@RequestMapping(value = "/updateuser", method = RequestMethod.POST)
	public Object updateUserById(@RequestBody User user) 
	{
		return signUpService.update(user);
	}

	@RequestMapping(value = "/deleteuser", method = RequestMethod.GET)
	public String delete(@RequestParam("userId") Integer id) 
	{
		return signUpService.delete(id);
	}
	
	
}
