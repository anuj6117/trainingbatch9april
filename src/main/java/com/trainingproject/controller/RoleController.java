package com.trainingproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trainingproject.domain.Role;
import com.trainingproject.service.RoleService;

@RestController
public class RoleController {

	@Autowired
	RoleService roleService;
	
	@RequestMapping(value="/createrole",method=RequestMethod.POST)
	public String createRoll(@RequestBody Role role) {
		Role rolec=roleService.createRoll(role);
		if(rolec==null)
			return "failure";
		else return "success";
	}
	
	@RequestMapping(value="getAllRoles",method=RequestMethod.GET)
	public List<Role> getAllRoles(){
		return roleService.getAllRoles();
	}
	
	@RequestMapping(value="getrolebyid",method=RequestMethod.GET)
	public Role getRoleById(@RequestParam Integer id) {
		return roleService.getRole(id);
	}
	
}
