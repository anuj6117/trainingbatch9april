package com.training.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training.demo.dto.UserRoleDto;
import com.training.demo.model.Role;
import com.training.demo.model.User;
import com.training.demo.repository.RoleRepository;
import com.training.demo.repository.UserRepository;

@Service
public class RoleService{

	private Role role;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRepository userRepository;
	User user;	
	
	public String addRole(Role roleArg)
	{
		String roleName = roleArg.getRoleType();
		if(roleName.equals("") || roleName.isEmpty() || roleName == null)
		{
			return  "Role type can't be null.";
		}
		String trimmedRole = roleName.trim();
		if(!roleName.equals(trimmedRole))
		{
			return "please remove leading or trailing spaces.";
		}
		
		roleRepository.save(roleArg);
		return "Role is successfully added.";
	}

	public void deleteRole(Integer roleId) {
		Role tempRole = roleRepository.findByRoleId(roleId);
		roleRepository.delete(tempRole);
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
