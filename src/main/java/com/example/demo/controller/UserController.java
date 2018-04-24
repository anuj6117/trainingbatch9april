package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.LimsLogger;
import com.example.demo.dto.UserDepositWithdrawDTO;
import com.example.demo.dto.UserRoleDTO;
import com.example.demo.model.User;
import com.example.demo.model.UserOtp;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/trading")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value=("/signup") ,method = RequestMethod.POST)
	public String signUp(@RequestBody User user) {
		LimsLogger.callMeInAppDebug("SignUP Service");		
		if(user.getCountry()!=null && user.getEmail()!=null && user.getUserName()!=null && user.getPhoneNumber()!=null && user.getPassword()!=null)
			return userService.insertData(user);
		else
			return "insufficent data";		
	}
	
	@RequestMapping("/verifyuser")
	public String verifyUser(@RequestBody UserOtp user) {		
		return userService.checkUser(user)?"user verified":"invalid otp";
	}
	
	@RequestMapping("/getallusers")
	public List<User> getAllUser(){		
		return userService.getAllUser();		
	}
	
	@RequestMapping("/deleteuser")
	public String deleteUser(@RequestParam("email")String email) {		
		return userService.deleteUser(email);
	}
	
	@RequestMapping("/getbyuserid")
	public User getByUserId(@RequestParam("id")Integer id) {
			return userService.getByUserId(id);
	}	
	
	@RequestMapping(value="/updateuser", method=RequestMethod.POST)
	public String updateUser(@RequestBody User user) {
		return userService.updateUser(user);
	}
	
	@RequestMapping(value="/assignrole", method=RequestMethod.POST)
	public String assignRole(@RequestBody UserRoleDTO userRoleDto){
		return userService.assignRole(userRoleDto);		
	}
	
	@RequestMapping(value="/deposit", method=RequestMethod.POST)
	public String deposit(@RequestBody UserDepositWithdrawDTO userDeposit) {
		return userService.deposit(userDeposit);		
	}
	
	@RequestMapping(value="/withdraw", method=RequestMethod.POST)
	public String withdraw(@RequestBody UserDepositWithdrawDTO userWithdraw) {
		return userService.withdraw(userWithdraw);		
	}
	
}
