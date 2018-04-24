package com.trading.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.trading.domain.Role;
import com.trading.handler.ResponseHandler;
import com.trading.services.RoleService;

@RestController
public class RoleController {

	@Autowired
	private RoleService roleService;

	@RequestMapping(value = "/createrole", method = RequestMethod.POST)
	public ResponseEntity<Object> roledetails(@Valid @RequestBody Role role) throws Exception {
		Map<String, Object> result = null;
		try {
			result = roleService.insertDetails(role);
			if (result.get("isSuccess").equals(true)) {
				return ResponseHandler.generateResponse(HttpStatus.OK, true, result.get("message").toString(), result);
			} else {
				return ResponseHandler.generateResponse(HttpStatus.OK, false, result.get("message").toString(), result);
			}
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}

		 
	}
}
