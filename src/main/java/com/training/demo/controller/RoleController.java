package com.training.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.training.demo.model.Role;
import com.training.demo.service.RoleService;

@RestController
public class RoleController {
	@Autowired
	private RoleService roleService;
	
	@RequestMapping(value="/createrole", method=RequestMethod.POST)
	public String createRole(@RequestBody Role role)
	{ 
			return roleService.addRole(role);
		
	}	
	
	@RequestMapping(value="/deleterole", method=RequestMethod.GET)
	public String deleteRole(@RequestParam("roleId") Integer roleId)
	{ 
		return roleService.deleteRoleById(roleId);
	}

	@RequestMapping(value="/getallrole", method=RequestMethod.GET)
	public Object getAllRole()
	{ 	
			return roleService.getAllRole();
	}

	@RequestMapping(value="/getrolebyid", method=RequestMethod.GET)
	public Object getRoleById(@RequestParam("roleId") Integer roleId)
	{ 	
		if(roleId != null)
		{
			return roleService.getRoleById(roleId);
		}
		else
		{
			return "User id can not be null or empty.";
		}
	}
}