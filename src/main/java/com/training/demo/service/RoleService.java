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
	
	public String addRole(Role roleArg)
	{
		String roleName = roleArg.getRoleType();
		List<Role> roles = roleRepository.findAll();
		for(Role r : roles)
		{
			if(r.getRoleType().equalsIgnoreCase(roleArg.getRoleType())) 
			{
				return "roleType already exists.";
				
			}
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
		
		System.out.println(roleArg.getRoleType());
		
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

	public String deleteRole(Integer roleId)
	{
		try {
		Role tempRole = roleRepository.findByRoleId(roleId);
		roleRepository.delete(tempRole);
		return "Role is successfully added.";
		}
		catch(Exception e)
		{
			return "roleId does not exist.";
		}
	}

	public List<Role> getAllRole() {
		List<Role> tempRole = roleRepository.findAll();
		return tempRole;
	}
	public Role getAllRole(Integer roleId) {
		Role tempRole = roleRepository.findByRoleId(roleId);
		return tempRole;
	}	
	
	
}
