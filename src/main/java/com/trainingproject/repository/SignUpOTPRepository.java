package com.trainingproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trainingproject.domain.SignUpOTP;

@Repository
public interface SignUpOTPRepository extends JpaRepository<SignUpOTP,Integer> {

	SignUpOTP findBytokenOTP(Integer tokenOTP);

}
