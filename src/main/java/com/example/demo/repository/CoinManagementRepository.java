package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.CoinManagement;
import com.example.demo.model.User;

public interface CoinManagementRepository extends JpaRepository<CoinManagement,Long> {
	
	public CoinManagement findOneByCoinId(Long coinId);

}
