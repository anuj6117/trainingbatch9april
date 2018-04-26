package com.traningproject1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.traningproject1.domain.UserOTP;

@Repository
public interface UserOTPRepository extends JpaRepository<UserOTP,Integer> {

}
