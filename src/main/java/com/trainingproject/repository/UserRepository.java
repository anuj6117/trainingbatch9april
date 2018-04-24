package com.trainingproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trainingproject.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>{

	User findByEmail(String email);
	
	User findByphoneNumber(long phoneNumber);
}
