package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	public User findByEmail(String email);
	public User findOneById(Integer id);
	public Optional<User> findById(Integer id);
	public User findByPhoneNumber(String phoneNumber);
	
	
}
