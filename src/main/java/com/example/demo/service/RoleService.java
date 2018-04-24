package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Role;
import com.example.demo.repository.RoleRepository;


@Service
public class RoleService {
	
	@Autowired
	private RoleRepository roleRepository;	
	
	public Role createRole(String roleType) {
		if(roleRepository.findOneByRoleType(roleType)==null) {
			Role role = new Role();
			role.setRoleType(roleType);
			return roleRepository.save(role);
		}
		else
			return null;
	}		
	
	public List<Role> getRoles() {
		return  roleRepository.findAll();		
	}

}
