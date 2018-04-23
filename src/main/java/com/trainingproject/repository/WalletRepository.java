package com.trainingproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trainingproject.domain.Wallet;
import com.trainingproject.enums.WalletType;

@Repository
public interface WalletRepository extends JpaRepository<Wallet,Integer>{
	
	Wallet findByWalletType(WalletType walletType);

	Wallet findByBalance(Long amount);

}
