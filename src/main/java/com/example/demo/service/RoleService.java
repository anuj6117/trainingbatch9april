package com.example.demo.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	private Set<User> user;

	public Role createRole(Role role) {
		if(roleRepository.findOneByRoleType(role.getRoleType())==null)
			return roleRepository.save(role);

		else
			return null;

	}

	public Object assignRole(Role role) {
		user = (Set)userRepository.findOneById(role.getUserId());
		role.setUser(user);
		return roleRepository.save(role);
	}

}
