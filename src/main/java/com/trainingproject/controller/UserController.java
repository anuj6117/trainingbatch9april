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

import com.trainingproject.domain.SignUpOTP;
import com.trainingproject.domain.User;
import com.trainingproject.domain.UserOrder;
import com.trainingproject.dto.AssignRoleBean;
import com.trainingproject.dto.AssignWalletBean;
import com.trainingproject.dto.WithdrawDepositBean;
import com.trainingproject.service.UserService;

@Controller
@RestController
public class UserController{

	@Autowired
	UserService userservice;
	
	@RequestMapping(value="signup",method=RequestMethod.POST)
	public String addUser(@RequestBody User user) {
		return userservice.createUser(user);
			
	}
	
	
	@RequestMapping(value="verifyuser",method=RequestMethod.POST)
	public String verifyUser(@RequestBody SignUpOTP OTPobj) {
		return userservice.verifyUser(OTPobj);
	}
	
	@RequestMapping(value="getallusers",method=RequestMethod.GET)
	public List<User> getAllUsers(){
		return userservice.getAllUsers();
	}
	
	@RequestMapping(value="getbyuserid",method=RequestMethod.GET)
	public Optional<User> getById(@RequestParam Integer userId){
		
		return userservice.getUserById(userId);
		
	}
	
	
//	@RequestMapping(value="/getById2",method=RequestMethod.GET)
//	public Optional<User> getById2(@RequestParam Integer id){
//		return userservice.getUserById(id);
//	}
	
	
	@RequestMapping(value="updateuser",method=RequestMethod.POST)
	public String update(@RequestBody User user){
		return userservice.update(user);
		
	}
	
	@RequestMapping(value="deleteuser",method=RequestMethod.GET)
	public String deleteData(Integer userId)
	{
		return userservice.deleteUser(userId);
		
	}
	
	@RequestMapping(value="assignrole",method=RequestMethod.POST)
	public String assignRole(@RequestBody AssignRoleBean arb) {
		
		return userservice.assignRole(arb);
		
	}
	
	@RequestMapping(value="addwallet",method=RequestMethod.POST)
	public String addWallet(@RequestBody AssignWalletBean awb) {
		
		return userservice.addWallet(awb);
		
	}
	
	@RequestMapping(value="withdrawamount",method=RequestMethod.POST)
	public String withdrawAmount(@RequestBody WithdrawDepositBean wdb) {
		return userservice.withdrawAmount(wdb);
		
	}
	
	@RequestMapping(value="depositamount",method=RequestMethod.POST)
	public String depositAmount(@RequestBody  WithdrawDepositBean wdb) {
		return userservice.depositAmount(wdb);
		
	}
	
	@RequestMapping(value="getorderbyuserid",method=RequestMethod.GET)
	public List<UserOrder> getAllOrderByUserId(@RequestParam Integer userId) {
		
		 return  userservice.getAllOrdersByUserId(userId);
	}
}
	
//	  @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "please enter valid data")
//	   @ExceptionHandler(HttpMessageNotReadableException.class)
//	   public String handleException() {
//	        //Handle Exception Here...
//		  try{
//			  
//		  }
//		  catch(ConstraintViolationException e) {
//			  return e.getMessage();
//		  }
//		  return "";
//	    }
//	



