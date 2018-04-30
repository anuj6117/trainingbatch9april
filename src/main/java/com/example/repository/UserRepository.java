package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.model.User;

public interface UserRepository extends JpaRepository<User, Integer> 
{
	//@Query(value="Select email from User where (userId)=(:userId)",nativeQuery=true )
    //User Eemail(@Param("userId") Integer emailNo);
	User findOneByemail(String email);
	User findByUserId(Integer userId);
	User findByEmail(String email);
	User findByPhoneNumber(String number);

	
}

