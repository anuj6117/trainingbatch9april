/*package com.training.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.training.demo.model.Role;
import com.training.demo.repository.RoleRepository;
import com.training.demo.service.RoleService;

@RestController
public class RoleController {
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@RequestMapping(value="/addRole", method=RequestMethod.POST)
	public String insertRole(@RequestBody Role role)
	{ 
		System.out.println("controller hit");
		String newRole=roleService.addRole(role);
		if(newRole != null)
		{
			return newRole;
		}
		else
		{
			return "failure";
		}
	}
	
}
*/