package com.example.demo.controller;


import com.example.demo.combinedfields.UserRole;
import com.example.demo.dto.ApprovalRequest;
import com.example.demo.dto.DepositAmountDto;
import com.example.demo.dto.WithDrawAmount;
import com.example.demo.model.User;
import com.example.demo.model.UserOtp;
import com.example.demo.service.UserService;
import com.example.demo.utilities.DataObj;
import com.example.demo.utilities.ResponseFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/trading")

public class SignupController {

	@Autowired
	private UserService userService;

	@RequestMapping(value="/signup",method=RequestMethod.POST)
	public ResponseFormatter addUser(@RequestBody User user) {
		try {
			String result = userService.insertUser(user);
			if(result.equalsIgnoreCase("user created suucessfully please verify it by using otp"))
				return 	new ResponseFormatter(result,new Date(),HttpStatus.OK,true,new DataObj("suceess"));
			else
				return 	new ResponseFormatter(result,new Date(),HttpStatus.INTERNAL_SERVER_ERROR,false,new DataObj("failure"));
		}catch (Exception e){
			System.out.println(e.getMessage());
			return 	new ResponseFormatter(e.getMessage(),new Date(),HttpStatus.BAD_REQUEST,false,new DataObj("failure"));
		}
	}

	@RequestMapping(value="/getallusers",method = RequestMethod.GET)
	public List<User> getusers() {
		List<User> user=null;
		try {
			user= userService.getallUsers();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		if(user!=null) return user;
		else return null;
	}


	@RequestMapping(value="/getbyuserid",method = RequestMethod.GET)
	public User getUser(@RequestParam("id") Integer id) {
		User user=userService.getSingleUser(id);
		if(user!=null)return user;
		else return null;
	}

	@RequestMapping(value="/updateuser",method=RequestMethod.POST)
	public ResponseFormatter updateUser(@RequestBody User updateduser) {
		try {
			String result = userService.updateUser(updateduser);
			if(result.equalsIgnoreCase("succesfully updated"))
				return new ResponseFormatter(result,new Date(),HttpStatus.OK,true,new DataObj("success"));
			else
				return new ResponseFormatter(result,new Date(),HttpStatus.INTERNAL_SERVER_ERROR,false,new DataObj("failure"));
		}catch(Exception e){
			return new ResponseFormatter(e.getMessage(),new Date(),HttpStatus.BAD_REQUEST,false,new DataObj("failure"));

		}
	}

	@RequestMapping(value="/deleteuser",method=RequestMethod.GET)
	public ResponseFormatter  deleteuser(@RequestParam("userId") Integer id ) {
		try {
			String result=userService.deleteUser(id);
			if(result.equalsIgnoreCase("deleted succesfully"))
				return new ResponseFormatter(result,new Date(),HttpStatus.OK,true,new DataObj("success"));
			else
				return new ResponseFormatter(result,new Date(),HttpStatus.INTERNAL_SERVER_ERROR,false,new DataObj("failure"));
		}catch (Exception e){
			return new ResponseFormatter(e.getMessage(),new Date(),HttpStatus.BAD_REQUEST,false,new DataObj("failure"));
		}
	}

	@RequestMapping(value="/assignrole",method = RequestMethod.POST)
	public ResponseFormatter assignRole(@RequestBody UserRole userRole){
		try {
			String result=userService.assignRole(userRole);
			if(result.equalsIgnoreCase("role assigned successfully"))
				return new ResponseFormatter(result,new Date(),HttpStatus.OK,true,new DataObj("success"));
			else
				return new ResponseFormatter(result,new Date(),HttpStatus.INTERNAL_SERVER_ERROR,false,new DataObj("failure"));
		}catch (Exception e){
			return new ResponseFormatter(e.getMessage(),new Date(),HttpStatus.BAD_REQUEST,false,new DataObj("failure"));
		}
	}

	@RequestMapping(value = "/verifyuser",method = RequestMethod.POST)
	public ResponseFormatter verifyUser(@RequestBody UserOtp userotp){
		try {
			String result=userService.verifyUser(userotp);
			if(result.equalsIgnoreCase("valid user"))
				return new ResponseFormatter(result,new Date(),HttpStatus.OK,true,new DataObj("success"));
			else
				return new ResponseFormatter(result,new Date(),HttpStatus.INTERNAL_SERVER_ERROR,false,new DataObj("failure"));
		}catch (Exception e){
			return new ResponseFormatter(e.getMessage(),new Date(),HttpStatus.BAD_REQUEST,false,new DataObj("failure"));
		}
	}

	@RequestMapping(value="/depositamount",method = RequestMethod.POST)
	public ResponseFormatter depositAmount(@RequestBody  DepositAmountDto depositAmountDto ){
		try {
			String result=userService.depositAmount(depositAmountDto);
			if(result.equalsIgnoreCase("deposit amount request submitted"))
				return new ResponseFormatter(result,new Date(),HttpStatus.OK,true,new DataObj("success"));
			else
				return new ResponseFormatter(result,new Date(),HttpStatus.INTERNAL_SERVER_ERROR,false,new DataObj("failure"));
		}catch (Exception e){
			return new ResponseFormatter(e.getMessage(),new Date(),HttpStatus.BAD_REQUEST,false,new DataObj("failure"));
		}
	}

	@RequestMapping(value="/withdrawamount",method = RequestMethod.POST)
	public ResponseFormatter withDrawAmount(@RequestBody WithDrawAmount withDraw){
		try {
			String result=userService.withDrawAmount(withDraw);
			if(result.equalsIgnoreCase("withdraw request submitted"))
				return new ResponseFormatter(result,new Date(),HttpStatus.OK,true,new DataObj("success"));
			else
				return new ResponseFormatter(result,new Date(),HttpStatus.INTERNAL_SERVER_ERROR,false,new DataObj("failure"));
		}catch (Exception e){
			return new ResponseFormatter(e.getMessage(),new Date(),HttpStatus.BAD_REQUEST,false,new DataObj("failure"));
		}
	}

	@RequestMapping(value="/approveRequest",method=RequestMethod.POST)
	public ResponseFormatter approveRequest(@RequestBody ApprovalRequest approvalRequest){
		try {
			String result=userService.approveRequest(approvalRequest);
			if(result.equalsIgnoreCase("ordered successful"))
				return new ResponseFormatter(result,new Date(),HttpStatus.OK,true,new DataObj("success"));
			else
				return new ResponseFormatter(result,new Date(),HttpStatus.INTERNAL_SERVER_ERROR,false,new DataObj("failure"));
		}catch (Exception e){
			return new ResponseFormatter(e.getMessage(),new Date(),HttpStatus.BAD_REQUEST,false,new DataObj("failure"));
		}
	}

}
