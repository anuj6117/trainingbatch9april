package com.training.demo.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.training.demo.model.Role;
import com.training.demo.model.User;
import com.training.demo.repository.RoleRepository;

@Service
public class RoleService{


	
	@Autowired
	private RoleRepository roleRepository;
		
	User user;	
	
//Corrected and Validated
	public String addRole(Role roleArg)
	{
		String roleName = roleArg.getRoleType().toUpperCase();
		roleArg.setRoleType(roleName);
		Role role ;
			
		if((role = roleRepository.findByRoleType(roleArg.getRoleType())) != null) {
			return "Already existing role type.";
		}
		
		if(roleName.equals("") || roleName.isEmpty() || roleName == null)
		{
			return  "Role type can't be null or empty.";
		}

		String trimmedRole = roleName.trim();
		if(!roleName.equals(trimmedRole))
		{
			return "please remove leading or trailing spaces.";
		}

		
		if(roleArg.getRoleType().equalsIgnoreCase("user") || roleArg.getRoleType().equalsIgnoreCase("manager") || roleArg.getRoleType().equalsIgnoreCase("admin"))
			{
				roleRepository.save(roleArg);
				return "Role is successfully added.";
			}
		else
		{
			return "invalid roleType.";
		}
		
	}

//Corrected and Validated	
	public String deleteRoleById(Integer roleId)
	{
		Role tempRole ;
		if((tempRole = roleRepository.findByRoleId(roleId))!= null) {
			roleRepository.delete(tempRole);
			return "Role Is Successfully Added.";
		}
		else
		{
			return "Role Id Does Not Exist.";	
		}
		
	}

//Corrected and Validated	
	public Object getAllRole() {
		List<Role> tempRole = roleRepository.findAll();
		if(tempRole.isEmpty())
		{
			return "There are no any roles available.";
		}
		return tempRole;
	}
	
	public Object getRoleById(Integer roleId) {
		Role role;
		if((role = roleRepository.findByRoleId(roleId))==null){
			return "There are no any role available for the given role id or invalid role id";
		}
		return role;
	}
	
}
