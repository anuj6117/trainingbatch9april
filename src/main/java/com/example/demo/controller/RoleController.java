package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.RoleDTO;
import com.example.demo.model.Role;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.RoleService;
import com.example.demo.utility.ResponseHandler;

@RestController
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@RequestMapping(value = "/createrole", method = RequestMethod.POST)
	public ResponseEntity<Object> createRole(@RequestBody Role role)
	{
		Map<String, Object> result = null;

		try {
			result = roleService.addRole(role);

			if (result.get("isSuccess").equals(true))
			{
				return ResponseHandler.generateResponse(HttpStatus.OK, true, result.get("message").toString(), result);
			} 
			else
			{
				return ResponseHandler.generateResponse(HttpStatus.OK, false, result.get("message").toString(), result);
			}

		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}
	}
	
	@RequestMapping(value = "/assignrole", method = RequestMethod.POST)
	public Object assignRole(@RequestBody RoleDTO roleDTO)
	{
		Integer userId = roleDTO.getUserId();
		String roleType = roleDTO.getRoleType().toUpperCase();
		if(userRepository.findByUserId(userId) == null)
		{
			return "User id does not exist.";
		}
		else if(roleRepository.findByRoleType(roleType) == null)
		{
			return "Role does not exist.";
		}
		else
		{
			return roleService.assignRole(roleDTO);
		}
	}
}
