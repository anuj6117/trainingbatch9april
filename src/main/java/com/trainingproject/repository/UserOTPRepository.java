package com.trainingproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trainingproject.domain.userOTP;

@Repository
public interface UserOTPRepository extends JpaRepository<userOTP,Integer>{

}
