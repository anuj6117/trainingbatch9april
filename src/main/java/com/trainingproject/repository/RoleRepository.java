package com.trainingproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trainingproject.domain.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer>{

	Role findByRoleType(String roleType);
	
}
