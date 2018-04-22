package com.trading.repository;

import org.springframework.data.repository.CrudRepository;

import com.trading.domain.Role;

public interface RoleRepository extends CrudRepository<Role,Long>{
	public Role findByRoleType(String roleType);

}
