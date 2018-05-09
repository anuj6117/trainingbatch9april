package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.UserOTP;

@Repository
public interface UserOTPRepository extends JpaRepository<UserOTP,Integer> {





	void deleteByuserOTPId(UserOTP findBytokenOtp);

	UserOTP findBytokenOTP(Integer tokenOTP);

	UserOTP findByTokenOTP(Integer tokenOTP);

	UserOTP findByEmailId(String email);

	

}
