package io.oodles.springboot1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.oodles.springboot1.model.StoreOTP;

@Repository
public interface StoreOTPRepository extends JpaRepository<StoreOTP,Integer > {

	public StoreOTP findByTokenOTP(Integer tokenOTP);

	

	
}