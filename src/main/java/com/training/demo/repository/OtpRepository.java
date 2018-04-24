package com.training.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.training.demo.model.OtpVerification;

public interface OtpRepository extends JpaRepository<OtpVerification, Integer> {
	OtpVerification findByEmail(String email);
}
