package com.trading.repository;

import org.springframework.data.repository.CrudRepository;

import com.trading.domain.User;

public interface UserRepo extends CrudRepository<User,Long>{

	public User findByEmail(String email);
	public User findByphoneNumber(long phoneNumber);

}
