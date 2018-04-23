package com.training.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

	@RequestMapping(value = "/addRole", method = RequestMethod.POST)
	public String insertRole(@RequestBody Role role) {
		System.out.println("Role controller hit addRole api");
		if (role != null) {
			roleService.addRole(role);
			return "Role Added Successfully.";
		} else {
			return "Role Addition Failure.";
		}
	}

	@RequestMapping(value = "/deleteRole", method = RequestMethod.GET)
	public String deleteRole(@RequestParam("roleId") Integer roleId)

	{
		System.out.println("Role controller hit delete api.");
		if (roleId != null) {
			roleService.deleteRole(roleId);
			return "Role Added Successfully.";
		} else {
			return "Role Addition Failure.";
		}
	}

	@RequestMapping(value = "/getAllRole", method = RequestMethod.GET)
	public List<Role> getAllRole() {
		System.out.println("Role controller hit getRole api.");
		return roleService.getAllRole();
	}

	@RequestMapping(value = "/getRoleById", method = RequestMethod.GET)
	public List<Role> getRoleById(@RequestParam("roleId") Integer roleId) {
		System.out.println("Role controller hit getRoleById api.");
		if (roleId != null) {
			return roleService.getAllRole();
		} else {
			throw new NullPointerException("RoleId may not be null.");
		}
	}
}