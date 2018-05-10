package com.trainingproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trainingproject.domain.UserOTP;

@Repository
public interface UserOTPRepository extends JpaRepository<UserOTP,Integer>{

	public UserOTP findByTokenOTP(Integer tokenOTP);
}
