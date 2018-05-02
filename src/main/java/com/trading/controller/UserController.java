package com.trading.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trading.domain.User;
import com.trading.dto.UserOtpDto;
import com.trading.dto.UserRoleDto;
import com.trading.handler.ResponseHandler;
import com.trading.services.UserOtpService;
import com.trading.services.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserOtpService userotpService;

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ResponseEntity<Object> signup(@RequestBody User user) throws Exception {
		Map<String, Object> result = null;

		try {
			result = userService.signup(user);
			if (result.get("isSuccess").equals(true)) {
				return ResponseHandler.generateResponse(HttpStatus.OK, true, result.get("message").toString(), result);
			} else {
				return ResponseHandler.generateResponse(HttpStatus.OK, false, result.get("message").toString(), result);
			}
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}
	}

	@RequestMapping(value = "/verifyuser", method = RequestMethod.POST)
	public ResponseEntity<Object> userverification(@Valid @RequestBody UserOtpDto userOtpDto) throws Exception {
		Map<String, Object> result = null;

		try {
			result = userotpService.verifyDetails(userOtpDto);
			if (result.get("isSuccess").equals(true)) {
				return ResponseHandler.generateResponse(HttpStatus.OK, true, result.get("message").toString(), result);
			} else {
				return ResponseHandler.generateResponse(HttpStatus.OK, false, result.get("message").toString(), result);
			}
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}
	}

	@RequestMapping(value = "/getallusers", method = RequestMethod.GET)
	public  Object getAllUser() throws Exception {
		List<User> list =  userService.getDetails();
		if(list.isEmpty())
			return "no user  available";
		return list;
		
	}

	@RequestMapping(value = "/getbyuserid", method = RequestMethod.GET)
	public Object getByUserId(@Valid @RequestParam("userId") long userId) throws Exception {
		
		User user =(User) userService.getById(userId);
		if(user==null)
			return "User Id does not exist";
		return user;
	}

	@RequestMapping(value = "/updateuser", method = RequestMethod.POST)
	public ResponseEntity<Object> updateUser(@Valid @RequestBody User user) {
		Map<String, Object> result = null;

		try {
			result = userService.updateDetails(user);
			if (result.get("isSuccess").equals(true)) {
				return ResponseHandler.generateResponse(HttpStatus.OK, true, result.get("message").toString(), result);
			} else {
				return ResponseHandler.generateResponse(HttpStatus.OK, false, result.get("message").toString(), result);
			}
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}

	}

	@RequestMapping(value = "/deleteuser", method = RequestMethod.GET)
	public ResponseEntity<Object> deleteUser(@Valid @RequestParam("userId") long userId) {
		Map<String, Object> result = null;

		try {
			result = userService.deleteById(userId);
			if (result.get("isSuccess").equals(true)) {
				return ResponseHandler.generateResponse(HttpStatus.OK, true, result.get("message").toString(), result);
			} else {
				return ResponseHandler.generateResponse(HttpStatus.OK, false, result.get("message").toString(), result);
			}
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}

	}

	@RequestMapping(value = "/assignrole", method = RequestMethod.POST)

	public ResponseEntity<Object> assignRole(@Valid @RequestBody UserRoleDto userroledto) {
		Map<String, Object> result = null;

		try {
			result = userService.assignNewRole(userroledto);
			if (result.get("isSuccess").equals(true)) {
				return ResponseHandler.generateResponse(HttpStatus.OK, true, result.get("message").toString(), result);
			} else {
				return ResponseHandler.generateResponse(HttpStatus.OK, false, result.get("message").toString(), result);
			}
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}

	}
}
