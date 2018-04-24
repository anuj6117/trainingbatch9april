package com.trading.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trading.Enum.WalletType;
import com.trading.domain.User;
import com.trading.domain.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

	Wallet findByWalletTypeAndUser(WalletType getwalletType, User user);
}
