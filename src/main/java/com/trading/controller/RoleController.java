package com.trading.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.trading.domain.Role;
import com.trading.services.RoleService;

@RestController
public class RoleController {
	
	@Autowired
	private	RoleService roleService;
		
		@RequestMapping(value = "/createrole", method = RequestMethod.POST)
		public String roledetails(@RequestBody Role role) throws Exception
		{
			return roleService.insertDetails(role);
		}		
}


