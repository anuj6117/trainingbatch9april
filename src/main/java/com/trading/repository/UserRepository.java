package com.trading.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.trading.domain.Role;
import com.trading.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {

	public User findByEmail(String email);

	public User findByphoneNumber(long phoneNumber);

	public User findOneByUserId(long userId);

	public User findByUserId(long userId);



	public Object findByRoleAndUserId(List<Role> role, long getuserId);

}
