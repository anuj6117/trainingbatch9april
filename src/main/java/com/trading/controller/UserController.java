package com.trading.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.trading.domain.User;
import com.trading.services.UserService;

@RestController
public class UserController {

	@Autowired
private	UserService userservice;
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signupdetails(@RequestBody User obj) throws Exception
	{
		User user = new User();
		
		return userservice.insertDetails(user);
	}
}
