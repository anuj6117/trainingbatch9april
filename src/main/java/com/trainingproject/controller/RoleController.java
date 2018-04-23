package com.trainingproject.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.trainingproject.domain.Role;
import com.trainingproject.service.RoleService;

@RestController
public class RoleController {
	@Autowired
	private RoleService roleService;
	
	@RequestMapping(value = "/createrole",method = RequestMethod.POST)
	   public String func(@RequestBody Role role) {
		 Role roleCreated = roleService.addRole(role);
		if(roleCreated != null) {
			return "success";
		}
			else
				return "Failure";

		}
	
	/*@RequestMapping(value = "/createrole",method = RequestMethod.POST)
	public void addRole(@RequestBody Role role) {
		
		roleService.addRole(role);
	}*/
	
	@RequestMapping(value = "/getallroles")
	public List<Role> getAllRole(){
		return roleService.getAllRole();
	}
	
	@RequestMapping(value = "/getbyroleid",method = RequestMethod.GET)
	public Optional<Role> getById(Integer roleId){
		return roleService.getById(roleId);
	}
	@RequestMapping(value = "/updaterole",method = RequestMethod.POST)
	public void updateRole(@RequestBody Role role){
		roleService.updaterRole(role);
	}

	@RequestMapping(value = "/deleterole",method = RequestMethod.GET)
	public void deleteRole(Integer roleId){
		roleService.deleteRole(roleId);
	}
}
