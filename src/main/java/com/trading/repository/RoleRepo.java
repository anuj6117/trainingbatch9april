package com.trading.repository;

import org.springframework.data.repository.CrudRepository;

import com.trading.domain.Role;

public interface RoleRepo extends CrudRepository<Role,Long>{
	public Role findByRoleType(String roleType);

}
