package com.trading.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.trading.domain.Role;
import com.trading.domain.User;
import com.trading.services.RoleService;

@RestController
public class RoleController {
	
	@Autowired
	private	RoleService roleservice;
		
		@RequestMapping(value = "/createrole", method = RequestMethod.POST)
		public String roledetails(@RequestBody Role role) throws Exception
		{
			return roleservice.insertDetails(role);
		}
	
		@RequestMapping(value = "/assignrole", method = RequestMethod.POST)
		public Role assignRole(@RequestBody Role role) {
			return roleservice.updateDetails(role);
		}
}


