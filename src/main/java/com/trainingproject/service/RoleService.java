package com.trainingproject.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trainingproject.domain.Role;

import com.trainingproject.repository.RoleRepository;

@Service
public class RoleService {

	@Autowired
	RoleRepository roleRepository;

	public String createRole(Role role) {
		if(roleRepository.findByRoleType(role.getRoleType())!=null)
			return "this role already exists!";
		if(getAllRoles().size()==3)
			return "can not create any more roles";
		
		if(role.getRoleType().equalsIgnoreCase("ADMIN"))
		 roleRepository.save(role);
		else if(role.getRoleType().equalsIgnoreCase("USER"))
			 roleRepository.save(role);
		else if(role.getRoleType().equalsIgnoreCase("MANAGER"))
			 roleRepository.save(role);
		else {
			return "role can not be created";
		}
		 return "success";
		
	}

	public List<Role> getAllRoles() {
		return roleRepository.findAll();
		
	}
	
	public Role getRole(Integer id) {
		return roleRepository.findById(id).get();
	}
}
