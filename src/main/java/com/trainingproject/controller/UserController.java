package com.trainingproject.controller;

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

import com.trainingproject.domain.User;
import com.trainingproject.domain.UserOTP;
import com.trainingproject.dto.UserRoleDto;
import com.trainingproject.dto.UserWalletDto;
import com.trainingproject.service.UserService;
import com.trainingproject.utils.ResponseHandler;

@RestController
public class UserController {
	@Autowired
	private UserService userService;
	
	
	@RequestMapping(value = "signup",method = RequestMethod.POST)
	   public ResponseEntity<Object> addUsers(@RequestBody User user) {
		Map<String,Object> result = null;
		try {
			result = userService.addUsers(user);	
			if(result.get("isSuccess").equals(true)) {
				return ResponseHandler.generateResponse(HttpStatus.OK, true, result.get("message").toString(), result);
			}
			else {
				return ResponseHandler.generateResponse(HttpStatus.OK, false, result.get("message").toString(), result);
			}
		}
		catch(Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}
}
	
	
	
	@RequestMapping(value = "/getallusers")
	public List<User> getAllUsers(){
		return userService.getAllUsers();
	}
	
	@RequestMapping(value = "/getbyuserid",method = RequestMethod.GET)
	public User getById(@RequestParam("userId") Integer userId){
		return userService.getById(userId);
		  
	}
	@RequestMapping(value = "/updateuser",method = RequestMethod.POST)
	public String updateUser(@RequestBody User user){
		userService.updateUser(user);
		return "Update success";
	}

	@RequestMapping(value = "/deleteuser",method = RequestMethod.GET)
	public String deleteUser(@RequestParam("userId") Integer userId){
		userService.deleteUser(userId);
		return "Delete success";
	}
	
	@RequestMapping(value = "/assignrole", method = RequestMethod.POST)
	public String assignRole(@RequestBody UserRoleDto userRoleDto) {
	try {
		userService.assignRole(userRoleDto);
	} catch (Exception e) {
	return "Role Cannot be assigned as : "+e.getMessage();
	}
	return "Role has Successfully Assigned.";
	}
	
	@RequestMapping(value = "/addwallet", method = RequestMethod.POST)
    public String addWallett(@RequestBody UserWalletDto  userWalletDto) {
		return userService.addWallet(userWalletDto);
	}
	
	@RequestMapping(value = "/verifyuser", method = RequestMethod.POST)
	public String userverification(@RequestBody UserOTP userOTP ) throws Exception {
		return userService.userVerification(userOTP);
	}	
}
