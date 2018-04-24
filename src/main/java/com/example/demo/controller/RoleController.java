package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.RoleDTO;
import com.example.demo.model.Role;
import com.example.demo.service.RoleService;

@RestController
public class RoleController {

	@Autowired
	private RoleService roleService;

	@RequestMapping(value = "/createrole", method = RequestMethod.POST)
	public String createRole(@RequestBody Role role) {
		if (role != null) {
			roleService.addRole(role);
			return "Role added successfully";
		} else {
			return "null value";
		}
	}

	@RequestMapping(value = "/assignrole", method = RequestMethod.POST)
	public String assignRole(@RequestBody RoleDTO roleDTO) {
		// User user=null;

		String result = roleService.assignRole(roleDTO);
		if (result != null) {
			return "Successfully assigned role";
		} else {
			return "Role is not assigned";
		}

	}
}
