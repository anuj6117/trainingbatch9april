package com.trainingproject.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trainingproject.domain.Role;
import com.trainingproject.repository.RoleRepository;

@Service
public class RoleService {
	@Autowired
	private RoleRepository roleRepository;

	public String addRole(Role role) {
		// TODO Auto-generated method stub
		if((roleRepository.findByRoleType(role.getRoleType()) == null)) {
		if(role.getRoleType().equalsIgnoreCase("admin") || role.getRoleType().equalsIgnoreCase("manager")) {
			 roleRepository.save(role);
			return "Role is successfully created";
			}
		  else 
			return "Role can not be created";
		}
		else
			return "Role is already exist";
	}

	public List<Role> getAllRole() {
		// TODO Auto-generated method stub
		List<Role> list = new ArrayList<Role>();
		roleRepository.findAll()
		.forEach(list::add);
		return list;
	}

	public Optional<Role> getById(Integer roleId) {
		// TODO Auto-generated method stub
		return roleRepository.findById(roleId);
	}

	public void updaterRole(Role role) {
		// TODO Auto-generated method stub
		roleRepository.save(role);
	}

	public void deleteRole(Integer roleId) {
		// TODO Auto-generated method stub
		roleRepository.deleteById(roleId);
	}
}
