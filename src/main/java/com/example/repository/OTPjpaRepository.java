package com.example.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.UserOtpTable;

public interface OTPjpaRepository extends JpaRepository<UserOtpTable,String> {
	
	//public UserOtpTable findOneByOtp(String otp);
	public UserOtpTable findByTokenOTP(String otp);
	public UserOtpTable findByEmail(String email);
	
}

