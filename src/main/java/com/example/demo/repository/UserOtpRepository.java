package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.UserOtp;

@Repository
public interface UserOtpRepository extends JpaRepository<UserOtp, Integer> {

	public UserOtp findByOtp(String otp);
	public void deleteByEmail(String email);
	
}
