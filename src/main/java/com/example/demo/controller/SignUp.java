package com.example.demo.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.enums.StatusEnum;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

@RestController
public class SignUp {
	
//	private User u = new User(StatusEnum.INACTIVE,new Date().toString());
	@Autowired
	private UserService userService;
	@RequestMapping(value=("/register") ,method = RequestMethod.POST)
	public String signUp(@RequestBody User user) {
	
		System.out.println("date---"+new Date().toString());
		user.setStatus(StatusEnum.INACTIVE);
		user.setDate(new Date().toString());
		if(user.getCountry()!=null||user.getEmail()!=null||user.getFullName()!=null||user.getMobile()!=null||user.getPassword()!=null)
			return userService.insertData(user);
		else
			return "insufficent data";
	}
}
