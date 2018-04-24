package io.oodles.springboot1.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.oodles.springboot1.model.StoreOTP;

@Repository
public interface StoreOTPRepository extends JpaRepository<StoreOTP,Integer > {

	public StoreOTP findByUserotp(Integer userotp);

	//public void deleteByUserotp(Integer userotp);

	
}