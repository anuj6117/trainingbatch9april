package com.training.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.training.demo.model.User;

public interface UserRepository extends JpaRepository<User, Integer> 
{
	User findByEmail(String email);
	User findByUserId(Integer userId);
	boolean existsByEmail(String email);
}