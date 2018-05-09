package com.traningproject1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.traningproject1.domain.User;
@Repository
public interface UserRepository extends JpaRepository<User,Integer>{

	//User findByUserId(Integer id);

	User findByuserId(Integer id);



	Object findByphoneNumber(String phoneNumber);




	User findByEmail(String emailId);



	User findByUserId(Integer userId);



	User findByPhoneNumber(String phoneNumber);

	//User findByuserId(Integer userId);

	//User findUserByid(Integer userId);

	

}
