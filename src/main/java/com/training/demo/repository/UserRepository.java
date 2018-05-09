package com.training.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.training.demo.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	public User findByEmail(String email);

	public User findByUserId(Integer l);

	public User findByUserId(String l);

	boolean existsByEmail(String email);
	
	boolean existsByPhoneNo(String phoneNo);

	public User findByUserName(String getuserName);

	public User findByCountry(String country);

	public User findByPhoneNo(String phoneNo);
}