package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	
	public User findByEmail(String email);
	
	public User findByUserName(String userName);
	
	public User findByUserId(Integer integer);

	public User findOneByUserId(Integer userId);

}
