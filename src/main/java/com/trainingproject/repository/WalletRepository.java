package com.trainingproject.repository;

//import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trainingproject.domain.User;
import com.trainingproject.domain.Wallet;
import com.trainingproject.enums.CoinType;

@Repository
public interface WalletRepository extends JpaRepository<Wallet,Integer> {

	Wallet findBycoinType(CoinType coinType);

	Wallet findByuser(User user);
	
	Wallet findBycoinTypeAndUser(CoinType coinType,User user);
	
	Wallet findBycoinNameAndUser(String coinName,User user);

	Wallet findBycoinNameAndCoinType(String coinName, CoinType coinType);
}
