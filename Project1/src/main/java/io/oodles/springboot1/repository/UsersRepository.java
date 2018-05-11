package io.oodles.springboot1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.oodles.springboot1.model.Users;
@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

	public Users findByUserId(Integer userId);

	public Users findByEmail(String email);

	public Users findByPhoneNumber(String phoneNumber);

	public Users findByUserName(String userName);

	public Users findByUserIdAndEmail(Integer userId, String email);

	public Users findByUserIdAndUserName(Integer userId, String userName);

	public Users findByUserIdAndPhoneNumber(Integer userId, String phoneNumber);

	
	

	

	

	
	

	

	
		
	

	

}
