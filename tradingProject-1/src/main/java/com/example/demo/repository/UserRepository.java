package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.User;
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
		public User findOneById(Integer id);
		public User findByPhoneNumber(String phoneNumber);
		public User findOneByEmail(String email);
}
