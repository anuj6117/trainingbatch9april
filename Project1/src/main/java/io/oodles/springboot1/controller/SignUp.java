package io.oodles.springboot1.controller;

import java.util.List;
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

import io.oodles.springboot1.model.AssignRole;
import io.oodles.springboot1.model.StoreOTP;
import io.oodles.springboot1.model.Users;
import io.oodles.springboot1.service.Signupservice;

@RestController
public class SignUp {
	@Autowired
	Signupservice signupservice;
	

	@PostMapping("/signup")
	public String insertUser( @RequestBody Users users) {
		return signupservice.addUser(users);
		
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
	
	@PostMapping("/updateuser")
	public String updateuser(@RequestBody Users users) {
		System.out.println("???????????");
		return signupservice.update(users);
	}
	
	@GetMapping("/deleteuser")
	public String deleteuser(@RequestParam int userId) {
		return signupservice.delete(userId);
	}
	@PostMapping("/assignrole")
	public String assignRoleToUser(@RequestBody AssignRole assignrole ) {
		return signupservice.assign(assignrole);
		
	}
	
	}
			



