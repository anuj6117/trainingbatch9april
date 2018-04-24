package com.training.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training.demo.model.OtpVerification;
import com.training.demo.model.Role;
import com.training.demo.repository.OtpRepository;
import com.training.demo.repository.RoleRepository;
import com.training.demo.repository.UserRepository;

@Service
public class RoleService {

	private Role role;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private OtpRepository otpRepository;

	public void addRole(Role roleArg) {
		roleRepository.save(roleArg);
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
