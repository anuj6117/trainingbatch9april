package com.trading.repository;

import org.springframework.data.repository.CrudRepository;

import com.trading.Enum.RoleType;
import com.trading.domain.Role;
import com.trading.domain.User;

public interface RoleRepository extends CrudRepository<Role, Long> {
	public Role findByRoleType(RoleType roleType);


}
