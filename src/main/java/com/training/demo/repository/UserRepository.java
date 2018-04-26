package com.training.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.training.demo.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	public User findByEmail(String email);

	public User findByUserId(Integer l);

	public User findByUserId(String l);

	boolean existsByEmail(String email);
	
	//boolean existByPhoneNo(String phoneNo);
}