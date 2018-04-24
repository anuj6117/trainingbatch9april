package com.traningproject1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.traningproject1.domain.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {

	//Role findByRoleType(String roleType);

	Role findByroleType(String roleType);

	Role getRoleByid(Integer i);

}
