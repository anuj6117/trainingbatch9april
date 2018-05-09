package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Role;

import antlr.collections.List;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {

	//Role findByRoleType(String roleType);

	Role findByroleType(String roleType);

	Role getRoleByid(Integer i);

	Role findByRoleType(String string);





}
