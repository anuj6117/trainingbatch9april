package com.trainingproject.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.trainingproject.domain.User;
import com.trainingproject.service.UserService;

@RestController
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="signup",method=RequestMethod.POST)
	public void addUsers(@RequestBody User user) {
		
		userService.addUsers(user);
	}
	
	@RequestMapping(value="/getallusers")
	public List<User> getAllUsers(){
		return userService.getAllUsers();
	}
	
	@RequestMapping(value="/getbyuserid",method=RequestMethod.GET)
	public Optional<User> getById(Integer userId){
		return userService.getById(userId);
	}
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public void updateUsers(@RequestBody User user){
		userService.updateUsers(user);
	}

	@RequestMapping(value="/delete",method=RequestMethod.GET)
	public void deleteUsers(Integer userId){
		userService.deleteUsers(userId);
	}
	
	

	
}
