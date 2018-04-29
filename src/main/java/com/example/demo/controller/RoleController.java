package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Role;
import com.example.demo.service.RoleService;

@RestController
public class RoleController {
	
	@Autowired
	private RoleService roleService;	
	
	@RequestMapping(value="/createrole", method=RequestMethod.POST)
	public String createRole(@RequestBody Role role) {
		return (roleService.createRole(role.getRoleType())!=null)?"success":"role not created";
	}	
		
	@RequestMapping("/getrole")
	public List<Role> getRoles() {
		return roleService.getRoles();		
	}	
	
}

