package com.trading.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trading.domain.Role;

public interface RoleRepo extends JpaRepository<Role,Long>{

}
