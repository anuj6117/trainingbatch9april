package io.oodles.springboot1.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.oodles.springboot1.model.Role;
import io.oodles.springboot1.service.Role_Service;

@RestController
public class Role_Controller {
	@Autowired
	Role_Service role_Service;
	
	
	@GetMapping("/getallroles")
	public List<Role> getall(){
         return role_Service.getallroles();       	
	}
	@PostMapping("/createrole")
	public String createRole(@RequestBody Role role) {
		
		return role_Service.create(role);
	}
	@GetMapping("/getbyroleid/{id}")
	public Optional<Role> getbyid(@PathVariable int id){
		return role_Service.searchbyid(id);
	}
	
	
	@PutMapping("/updaterole/{id}")
	public Role updateuser(@RequestBody Role role,@PathVariable int id) {
		return role_Service.update(role,id);
	}
	
	@GetMapping("/deleterole/{id}")
	public void deleteuser(@PathVariable int id) {
		role_Service.delete(id);
	}
	}
	
	


