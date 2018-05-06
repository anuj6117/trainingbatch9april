package io.oodles.springboot1.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.oodles.springboot1.ResponseHandler.ResponseHandler;
import io.oodles.springboot1.model.AssignRole;
import io.oodles.springboot1.model.StoreOTP;
import io.oodles.springboot1.model.Users;
import io.oodles.springboot1.service.Signupservice;

@RestController
public class SignUp {
	@Autowired
	Signupservice signupservice;
	

	/*@PostMapping("/signup")
	public String insertUser( @RequestBody Users users) {
		return signupservice.addUser(users);
		
	}*/
	
	@PostMapping("/signup")   
	public ResponseEntity<Object> insertUser(@RequestBody Users users){
		
		Map<String,Object> result=null;
		try {
			result=signupservice.addUser(users);
			
			if(result.get("isSuccess").equals(true)) {
				return ResponseHandler.generateResponse(HttpStatus.OK, true, result.get("message").toString(), result);
			}
			else {
				return ResponseHandler.generateResponse(HttpStatus.OK, false, result.get("message").toString(), result);
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}
	}
	
	@PostMapping("/updateuser")
	public ResponseEntity<Object> updateuser(@RequestBody Users users) {
		Map<String,Object> result=null;
		try {
			result=signupservice.update(users);
			
			if(result.get("isSuccess").equals(true)) {
				return ResponseHandler.generateResponse(HttpStatus.OK, true, result.get("message").toString(), result);
			}
			else {
				return ResponseHandler.generateResponse(HttpStatus.OK, false, result.get("message").toString(), result);
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}
		
		 
	}
	@GetMapping("/deleteuser")
	public ResponseEntity<Object> deleteuser(@RequestParam int userId) {
		Map<String,Object> result=null;
		try {
			result=signupservice.delete(userId);
			
			if(result.get("isSuccess").equals(true)) {
				return ResponseHandler.generateResponse(HttpStatus.OK, true, result.get("message").toString(), result);
			}
			else {
				return ResponseHandler.generateResponse(HttpStatus.OK, false, result.get("message").toString(), result);
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}
		
	}
	
	
	@PostMapping("/verifyuser")
	public String verify(@RequestBody StoreOTP otp) {
		return signupservice.auth(otp);
		
		
		}
	
	@GetMapping("getallusers")
	public List<Users> getall(){
		return signupservice.getallusers();
	}
	
	@GetMapping("/getbyuserid")
	public Optional<Users> getbyid(@RequestParam int userId){
		return signupservice.searchbyid(userId);
	}
	
	
	
	
	@PostMapping("/assignrole")
	public String assignRoleToUser(@RequestBody AssignRole assignrole ) {
		return signupservice.assign(assignrole);
		
	}
	
	}
			



