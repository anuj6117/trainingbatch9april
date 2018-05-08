package com.traningproject1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.traningproject1.domain.User;
import com.traningproject1.domain.Wallet;
import com.traningproject1.enumsclass.CoinType;
@Repository
public interface WalletRepository extends JpaRepository<Wallet,Integer> {

	Wallet findByCoinType(CoinType coinType);

	Wallet findByUserAndCoinType(User user, CoinType fiate);

	Wallet findByUserAndCoinTypeAndCoinName(User user, CoinType crypto, String coinName);


	Wallet findByUserAndCoinName(User user, String coinName);

	

	//Wallet findByWalletType(AssignWalletDTO assignwalletDTO);

	//Wallet findByCoinType(Integer integer);

	//Wallet findBycoinName(User user, CoinType fiate);

}
