package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.enums.WalletType;
import com.example.model.Wallet;

public interface WalletRepository extends JpaRepository<Wallet,Integer>
{
  Wallet findByCoinType(WalletType wallettype);
 
}
