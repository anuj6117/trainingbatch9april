package com.trading.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trading.domain.User;

public interface UserRepo extends JpaRepository<User,Long>{

}
