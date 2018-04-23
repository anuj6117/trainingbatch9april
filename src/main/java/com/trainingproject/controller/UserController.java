package com.trainingproject.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.trainingproject.domain.Role;
import com.trainingproject.domain.User;
import com.trainingproject.domain.UserOTP;
import com.trainingproject.dto.UserRoleDto;
import com.trainingproject.dto.UserWalletDto;
import com.trainingproject.service.UserService;

@RestController
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "signup",method = RequestMethod.POST)
	   public String addUsers(@RequestBody User user) {
		 User userCreated = userService.addUsers(user);
		if(userCreated != null) {
			return "success";
		}
			else
				return "Failure";

		}
	
	/*@RequestMapping(value = "signup",method = RequestMethod.POST)
	public void addUsers(@RequestBody User user) {
		
		userService.addUsers(user);
	}*/
	
	@RequestMapping(value = "/getallusers")
	public List<User> getAllUsers(){
		return userService.getAllUsers();
	}
	
	@RequestMapping(value = "/getbyuserid",method = RequestMethod.GET)
	public Optional<User> getById(Integer userId){
		return userService.getById(userId);
	}
	@RequestMapping(value = "/updateuser",method = RequestMethod.POST)
	public String updateUser(@RequestBody User user){
		userService.updateUser(user);
		return "succes";
	}

	@RequestMapping(value = "/deleteuser",method = RequestMethod.GET)
	public String deleteUser(Integer userId){
		userService.deleteUser(userId);
		return "succes";
	}
	
	@RequestMapping(value = "/assignrole", method = RequestMethod.POST)
	public String assignRole(@RequestBody UserRoleDto userRoleDto) {
	User user = null;
	try {
	user = userService.assignRole(userRoleDto);
	} catch (Exception e) {
	return "Role Cannot be assigned as : "+e.getMessage();
	}
	return "Role Is Successfully Assigned.";
	}
	
	@RequestMapping(value = "/addwallet", method = RequestMethod.POST)
    public String addWallett(@RequestBody UserWalletDto  userWalletDto) {
		userService.addWallet(userWalletDto);
		return "Wallet is successfully added";
	}
	
	@RequestMapping(value = "/verifyuser", method = RequestMethod.POST)
	public String userverification(@RequestBody UserOTP userOTP ) throws Exception {
		return userService.userVerification(userOTP);
	}
	
	@RequestMapping(value = "/depositamount", method = RequestMethod.POST)
	public String depositAmount(@RequestBody UserWalletDto  userWalletDto) throws Exception {
		return userService.depositAmount(userWalletDto);
	}

	@RequestMapping(value = "/withdrawamount", method = RequestMethod.POST)
	public String withdrawAmount(@RequestBody UserWalletDto  userWalletDto) throws Exception {
		return userService.withdrawAmount(userWalletDto);
	}
}
