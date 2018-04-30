package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.CoinManagement;

public interface CoinManagementRepository extends JpaRepository<CoinManagement, Integer> {

	public CoinManagement findOneByCoinId(Integer coinId);

}
