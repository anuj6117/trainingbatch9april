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
import com.example.demo.service.RoleService;
import com.example.demo.utility.ResponseHandler;

@RestController
public class RoleController {

	@Autowired
	private RoleService roleService;
	
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
	public ResponseEntity<Object> assignRole(@RequestBody RoleDTO roleDTO)
	{
		Map<String, Object> result = null;

		try {
			result = roleService.assignRole(roleDTO);

			if (result.get("isSuccess").equals(true))
			{
				System.out.println("if block of Controller.");
				return ResponseHandler.generateResponse(HttpStatus.OK, true, result.get("message").toString(), result);
			} 
			else
			{
				System.out.println("else block of controller.");
				return ResponseHandler.generateResponse(HttpStatus.OK, false, result.get("message").toString(), result);
			}

		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}
	}
}
