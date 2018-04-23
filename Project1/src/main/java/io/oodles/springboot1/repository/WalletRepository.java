package io.oodles.springboot1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.oodles.springboot1.enums.WalletType;
import io.oodles.springboot1.model.Wallet;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Integer>{


	Wallet findByWallet(WalletType wallet);

	

}
