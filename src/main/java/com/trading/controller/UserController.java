package com.trading.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.trading.domain.User;
import com.trading.domain.UserOtp;
import com.trading.dto.UserRoleDto;
import com.trading.services.UserOtpService;
import com.trading.services.UserService;

@RestController
public class UserController {

@Autowired 
private	UserService userService;
	
@Autowired
private UserOtpService userotpService;
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signupdetails(@RequestBody User obj) throws Exception
	{
		return userService.insertDetails(obj);
	}

	@RequestMapping(value = "/userverification", method = RequestMethod.POST)
	public String userverification(@RequestBody UserOtp obj ) throws Exception
	{
		return userotpService.verifyDetails(obj);
	}
	
	@RequestMapping(value = "/getalluser", method = RequestMethod.GET)
	public Iterable <User> getAllUser() throws Exception
	{
		return userService.getDetails();
	}
	
	@RequestMapping(value = "/getbyuserid", method = RequestMethod.GET)
	public Optional<User> getByUserId(@RequestParam("userId") long userId) throws Exception
	{
		return userService.getById(userId);
	}
		
	@RequestMapping(value = "/updateuser", method = RequestMethod.POST)
	public User updateUser(@RequestBody User obj) {
		return userService.updateDetails(obj);
	}
	
	@RequestMapping(value = "/deleteuser", method = RequestMethod.GET)
	public String deleteUser(@RequestParam("userId") long userId)
	{
		return userService.deleteById(userId);
	}
	
	@RequestMapping(value = "/assignrole", method = RequestMethod.POST)
	
	public String assignRole(@RequestBody UserRoleDto userroledto)
	{		
		return userService.assignNewRole(userroledto);
	} 	
}
