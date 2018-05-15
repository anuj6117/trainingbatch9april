package com.example.demo.controller;


import java.util.ArrayList;
import java.util.List;

import com.example.demo.combinedfields.UserRole;
import com.example.demo.dto.ApprovalRequest;
import com.example.demo.dto.DepositAmountDto;
import com.example.demo.dto.WithDrawAmount;
import com.example.demo.model.UserOtp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
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
		List<User> user=null;
		try {
			user= userService.getallUsers();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return user;
	}


	@RequestMapping(value="/getbyuserid")
	public User getUser(@RequestParam("id") Integer id) {
//		 if(userService.getSingleUser(id)!=null)return
		User user=userService.getSingleUser(id);
		if(user!=null)return user;
		else return null;
	}

	@RequestMapping(value="/updateuser",method=RequestMethod.POST)
	public String updateUser(@RequestBody User updateduser) {
		String result=userService.updateUser(updateduser);
		return result;
	}

	@RequestMapping(value="/deleteuser",method=RequestMethod.GET)
	public String  deleteuser(@RequestParam("userId") Integer id ) {
		return  userService.deleteUser(id);
	}

	@RequestMapping(value="/assignrole",method = RequestMethod.POST)
	public String assignRole(@RequestBody UserRole userRole){
		return	userService.assignRole(userRole);
	}

	@RequestMapping(value = "/verifyuser",method = RequestMethod.POST)
	public String verifyUser(@RequestBody UserOtp userotp){
		return userService.verifyUser(userotp);
	}

	@RequestMapping(value="/depositamount",method = RequestMethod.POST)
	public String depositAmount(@RequestBody  DepositAmountDto depositAmountDto ){
		return userService.depositAmount(depositAmountDto);
	}

	@RequestMapping(value="/withdrawamount",method = RequestMethod.POST)
	public String withDrawAmount(@RequestBody WithDrawAmount withDraw){
		return userService.withDrawAmount(withDraw);
	}

	@RequestMapping(value="/approveRequest",method=RequestMethod.POST)
	public String approveRequest(@RequestBody ApprovalRequest approvalRequest){
		return userService.approveRequest(approvalRequest);
	}

}
