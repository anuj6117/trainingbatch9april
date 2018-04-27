package io.oodles.springboot1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.oodles.springboot1.model.Users;
@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

	public Users findByUserid(Integer userid);

	public Users findByEmail(String email);

	

	

	

	
	

	

	
		
	

	

}
