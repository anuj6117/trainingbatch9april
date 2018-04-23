package com.training.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.training.demo.model.CoinManagement;
import com.training.demo.model.User;

public interface CoinManagementRepository extends JpaRepository<CoinManagement, Long> {

	public CoinManagement findOneByCoinId(Long coinId);

}