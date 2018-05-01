package io.oodles.springboot1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.oodles.springboot1.model.Users;
@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

	public Users findByUserId(Integer userId);

	public Users findByEmail(String email);

	//public Users findByUserIdAndWallet(Integer userId, Set<Wallet> wallet);

	

	

	

	
	

	

	
		
	

	

}
