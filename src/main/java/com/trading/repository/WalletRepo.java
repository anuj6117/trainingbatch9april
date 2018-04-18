package com.trading.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trading.domain.Wallet;


public interface WalletRepo extends JpaRepository<Wallet,Long>{

	
}
