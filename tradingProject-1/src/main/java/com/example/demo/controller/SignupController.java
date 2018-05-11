package com.example.demo.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.service.UserService;



@RestController
public class SignupController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/signup",method=RequestMethod.POST)
	public String addUser(@RequestBody User user) {
		String result=userService.insertUser(user);
		return result;
	}
	
	@RequestMapping(value="/getallusers")
	public List<User> getusers() {
		List<User> users=userService.getallUsers();
		return users;
	}
	
	
	@RequestMapping(value="/getbyuserid")
	public User getUser(@RequestParam("id") Integer id) {
		return userService.getSingleUser(id);
		
	}
	
	@RequestMapping(value="/updateuser",method=RequestMethod.POST)
	public String updateUser(@RequestBody User updateduser) {
		String result=userService.updateUser(updateduser);
		return result;
	}
	
	@RequestMapping(value="/deleteuser",method=RequestMethod.DELETE)
	  public String  deleteuser(@RequestParam("userId") Integer id ) {
		return  userService.deleteUser(id);
	}
	
	
}
