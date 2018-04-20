package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.model.Role;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.RoleService;

@Controller
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	@RequestMapping(value="/createrole", method=RequestMethod.POST)
	public String createRole(@RequestBody Role role)
	{
		if(role != null)
		{
			roleService.addRole(role);
			return "Role added successfully";
		}
		else
		{
			return "null value";
		}
	}
	
	/*@RequestMapping(value="/assignrole")
	public String updateRole(@RequestBody Role role)
	{
		if( role != null)
		{
			roleService.update(role);
			return "Role Updated successfully";
		}
		else
		{
			return "Role not updated";
		}
	}*/
}
