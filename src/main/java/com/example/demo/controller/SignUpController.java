package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.model.VerifyOtp;
import com.example.demo.service.SignUpService;

@RestController
public class SignUpController 
{
	@Autowired
	private SignUpService sus;
	
	@RequestMapping(value="/signup", method=RequestMethod.POST)
	public String insertUser(@RequestBody User user)
	{
		String u=sus.addUser(user);
		if(u != null)
		{
			return "success";
		}
		else
		{
			return "failure";
		}
	}
	
	@RequestMapping(value="/verifyUser", method=RequestMethod.POST)
	public ResponseEntity<Object> verifyUser(@RequestBody VerifyOtp verifyOtp) {
		String result = null;
		try {
			result = dataService.verifyUser(verifyOtp);
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}
		return ResponseHandler.generateResponse(HttpStatus.OK, true, result, result);
	}
}
