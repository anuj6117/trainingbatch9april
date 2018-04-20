package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Role;
import com.example.demo.repository.RoleRepository;

@Service
public class RoleService {
	
	@Autowired
	private RoleRepository roleRepository;

	public void addRole(Role role) {
		
		roleRepository.save(role);
		
	}

	/*public void update(Role role) {
		Role data=new Role();
		data.setRoleType(role.getRoleType());
		roleRepository.save(data);
		
	}*/

}
