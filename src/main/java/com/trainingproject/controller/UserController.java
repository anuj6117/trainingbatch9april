package com.trainingproject.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trainingproject.domain.User;
import com.trainingproject.dto.AssignRoleBean;
import com.trainingproject.dto.AssignWalletBean;
import com.trainingproject.dto.BuySellBean;
import com.trainingproject.dto.WithdrawDepositBean;
import com.trainingproject.service.UserService;

@Controller
@RestController
public class UserController{

	@Autowired
	UserService userservice;
	
	@RequestMapping(value="signUp",method=RequestMethod.POST)
	public String addUser(@RequestBody User user) {
		User userCreated=userservice.createUser(user);
		if(userCreated!=null)
		return "Success";
		else
			return "fail";
		
	}
	
	
	@RequestMapping("hello1")
	public String hello() {
		return "hello";
	}
	
	@RequestMapping(value="getallusers",method=RequestMethod.GET)
	public List<User> getAllUsers(){
		return userservice.getAllUsers();
	}
	
	@RequestMapping(value="getById",method=RequestMethod.GET)
	public Optional<User> getById(Integer id){
		return userservice.getUserById(id);
	}
	
	
	@RequestMapping(value="/getById2",method=RequestMethod.GET)
	public Optional<User> getById2(@RequestParam Integer id){
		return userservice.getUserById(id);
	}
	
	
	@RequestMapping(value="update",method=RequestMethod.POST)
	public User update(@RequestBody User user){
		User updateduser=userservice.update(user);
		return updateduser;
	}
	
	@RequestMapping(value="delete",method=RequestMethod.GET)
	public String deleteData(Integer id)
	{
		userservice.deleteData(id);
		return "success";
	}
	
	@RequestMapping(value="assignrole",method=RequestMethod.POST)
	public String assignRole(@RequestBody AssignRoleBean arb) {
		
		return userservice.assignRole(arb);
		
	}
	
	@RequestMapping(value="addwallet",method=RequestMethod.POST)
	public String addWallet(@RequestBody AssignWalletBean awb) {
		
		userservice.addWallet(awb);
		return "success";
	}
	
	@RequestMapping(value="withdrawamount",method=RequestMethod.POST)
	public String withdrawAmount(@RequestBody WithdrawDepositBean wdb) {
		return userservice.withdrawAmount(wdb);
		
	}
	
	@RequestMapping(value="depositamount",method=RequestMethod.POST)
	public String depositAmount(@RequestBody  WithdrawDepositBean wdb) {
		return userservice.depositAmount(wdb);
		
	}
	
	@RequestMapping(value="createbuyorder",method=RequestMethod.POST)
	public String createBuyOrder(@RequestBody BuySellBean bsb) {
		return userservice.createBuyOrder(bsb);
	}
	
	@RequestMapping(value="/createsellorder",method=RequestMethod.POST)
	public String createSellOrder(@RequestBody BuySellBean bsb) {
		return userservice.createSellOrder(bsb);
		
	}
	}

