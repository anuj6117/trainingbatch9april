package com.trainingproject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trainingproject.controller.UserController;
import com.trainingproject.domain.Role;
import com.trainingproject.domain.User;
import com.trainingproject.repository.RoleRepository;

@Service
public class RoleService {

	@Autowired
	RoleRepository roleRepository;

	public String createRole(Role role) {
		if(roleRepository.findByroleType(role.getRoleType())!=null)
			return "this role already exists!";
		if(getAllRoles().size()==3)
			return "cannot create any more roles";
		
		if(role.getRoleType().equals("ADMIN"))
		 roleRepository.save(role);
		else if(role.getRoleType().equals("USER"))
			 roleRepository.save(role);
		else if(role.getRoleType().equals("MANAGER"))
			 roleRepository.save(role);
		else {
			return "role cannot be created";
		}
		 return "success:role created";
		
	}

	public List<Role> getAllRoles() {
		return roleRepository.findAll();
		
	}
	
	public Role getRole(Integer id) {
		return roleRepository.findById(id).get();
	}
}
