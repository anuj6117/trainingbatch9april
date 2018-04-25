package com.traningproject1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.traningproject1.domain.Wallet;
import com.traningproject1.enumsclass.CoinType;
@Repository
public interface WalletRepository extends JpaRepository<Wallet,Integer> {

	//Wallet findByWalletType(AssignWalletDTO assignwalletDTO);

	Wallet findByCoinType(CoinType cointype);

}
