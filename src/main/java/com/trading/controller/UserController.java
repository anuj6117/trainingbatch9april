package com.trading.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.trading.domain.User;
import com.trading.domain.UserOtp;
import com.trading.services.UserOtpService;
import com.trading.services.UserService;

@RestController
public class UserController {

	@Autowired
private	UserService userservice;
	
	@Autowired
	private UserOtpService userotpservice;
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signupdetails(@RequestBody User obj) throws Exception
	{
		return userservice.insertDetails(obj);
	}

	@RequestMapping(value = "/userverification", method = RequestMethod.POST)
	public String userverification(@RequestBody UserOtp obj ) throws Exception
	{
		return userotpservice.verifyDetails(obj);
	}
}
