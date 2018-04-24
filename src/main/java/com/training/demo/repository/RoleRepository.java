package com.training.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.training.demo.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

	Role findByRoleId(Integer roleId);

	Role findByRoleType(String roleType);

}
