package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.RoleDTO;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	public void addRole(Role role) {

		roleRepository.save(role);
	}

	public String assignRole(RoleDTO roleDTO) {
		if(userRepository.findOneByUserId(roleDTO.getUserId()) != null) 
		{
			User user = userRepository.findOneByUserId(roleDTO.getUserId());
			if(roleRepository.findByRoleType(roleDTO.getRoleType()) != null) 
			{
				Role role = roleRepository.findByRoleType(roleDTO.getRoleType());
		
				user.getRoles().add(role);
		
				userRepository.save(user);
				return "assign role";
			}
			return "Role Assigned Successfully.";
		}
		else
		{
			return "User does not exist.";
		}
	}
}
