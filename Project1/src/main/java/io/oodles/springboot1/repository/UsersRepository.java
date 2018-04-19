package io.oodles.springboot1.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import io.oodles.springboot1.model.Users;
@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

	public Users findByEmailid(String emailid);

	
		
	

	

}
