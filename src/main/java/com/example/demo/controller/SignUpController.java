package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.model.VerifyOtp;
import com.example.demo.service.SignUpService;

@RestController
public class SignUpController {
	@Autowired
	private SignUpService signUpService;

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String insertUser(@RequestBody User user) {
		String u = signUpService.addUser(user);
		if (u != null) {
			return u;
		} else {
			return "Null user details not accepted";
		}
	}

	@RequestMapping(value = "/verifyuser", method = RequestMethod.POST)
	public void verifyUser(@RequestBody VerifyOtp obj) {
		if (((obj.getEmailId() != null) && (obj.getTokenOtp() != null))) {
			System.out.println("from controller " + obj.getEmailId() + "\t" + obj.getTokenOtp());
			signUpService.verifyUserWithOtp(obj.getEmailId(), obj.getTokenOtp());

		} else {
			System.out.println("from else block of controller " + obj.getEmailId() + "\t" + obj.getTokenOtp());
			System.out.println("email and otp is null.");
		}
	}

	@RequestMapping(value = "/getallusers", method = RequestMethod.GET)
	public List<User> getAllUsers() {
		List<User> list = signUpService.getAllUsers();
		return list;
	}

	@RequestMapping(value = "/getuserbyid", method = RequestMethod.GET)
	public Optional<User> getUserById(@RequestParam("userId") Integer id) {
		Optional<User> obj = null;
		try {
			obj = signUpService.getuserById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	@RequestMapping(value = "/updateuserbyid", method = RequestMethod.POST)
	public User updateUserById(@RequestBody User user) {
		return signUpService.update(user);
	}

	@RequestMapping(value = "/deleteuser", method = RequestMethod.GET)
	public String delete(@RequestParam("userId") Integer id) {
		if (id != null) {
			signUpService.delete(id);
			return "success";
		}
		return "fail";
	}
	
	
}
