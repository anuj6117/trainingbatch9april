package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Role;
import com.example.demo.repository.RoleRepository;

@Service
public class RoleService {
	@Autowired
	private RoleRepository roleRepository;
	
	//private User user;
	
	public String addRole(Role role)
	{   
		  roleRepository.save(role);
		  return "Success";
	}
 public List<Role> getAllRole()
 {

	 return roleRepository.findAll();
 }
 public Optional<Role> getRoleByid(Integer id)
 {
	Optional<Role> rolebyid=roleRepository.findById(id);
	return rolebyid;
 }

 public void deleteRoleById(Integer id)
 {
	 roleRepository.deleteById(id);
 }
 public Role updateRole(Role role)
 {
	 return roleRepository.save(role);
 }
public Optional<Role> getRoleById(Integer id) {
	// TODO Auto-generated method stub
	 return roleRepository.findById(id);
}
}
