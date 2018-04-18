package com.example.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.User;

public interface jpaRepository extends JpaRepository<User, Integer> 
{

	User findOneByemail(String email);
	User findByUserId(Integer userId);
	
}

