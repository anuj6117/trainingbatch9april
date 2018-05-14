package com.example.demo.repository;

import com.example.demo.model.UserOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserOtpRepository  extends JpaRepository<UserOtp,Integer> {
    public UserOtp findOneByTokenOTP(Integer token);
}
