package io.oodles.springboot1.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	public void insertUser(@RequestBody Users users) {
		signupservice.addUser(users);
	}
	
	@PostMapping("/verify")
	public String verify(@RequestBody StoreOTP otp) {
		return signupservice.auth(otp);
		
		
		}
	
	@GetMapping("getallusers")
	public List<Users> getall(){
		return signupservice.getallusers();
	}
	
	@GetMapping("/getbyuserid/{id}")
	public Optional<Users> getbyid(@PathVariable int id){
		return signupservice.searchbyid(id);
	}
	
	@PostMapping("/updateuser/{id}")
	public Users updateuser(@RequestBody Users users,@PathVariable int id) {
		return signupservice.update(users,id);
	}
	
	@GetMapping("/deleteuser/{id}")
	public void deleteuser(@PathVariable int id) {
		signupservice.delete(id);
	}
	@PostMapping("/assignrole")
	public Users assignRoleToUser(@RequestBody AssignRole assignrole ) {
		return signupservice.assign(assignrole);
		
	}
	
	}
			



