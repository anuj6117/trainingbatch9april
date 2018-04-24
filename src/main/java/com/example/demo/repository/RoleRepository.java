package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	
	public Role findByRoleId(Integer roleId);
	public Role findByRoleType(String rollType);

}
