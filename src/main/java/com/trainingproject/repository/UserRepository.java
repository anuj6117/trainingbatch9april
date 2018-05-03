package com.trainingproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trainingproject.domain.User;



@Repository
public interface UserRepository extends JpaRepository<User,Integer>{

	public User findByUserId(Integer userId);
	public User findByEmail(String email);
	public User findByPhoneNumber(String phoneNumber);
	
	
}
