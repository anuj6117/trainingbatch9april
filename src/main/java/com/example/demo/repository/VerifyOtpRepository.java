package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.VerifyOtp;

public interface VerifyOtpRepository extends JpaRepository<VerifyOtp , Integer>
{
	public VerifyOtp findByEmailId(String email);
	
}