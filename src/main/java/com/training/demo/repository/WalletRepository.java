package com.training.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.training.demo.enums.WalletType;
import com.training.demo.model.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

	Wallet findByCoinType(WalletType getCoinType);

}
