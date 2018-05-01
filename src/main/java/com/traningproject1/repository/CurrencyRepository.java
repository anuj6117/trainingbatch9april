package com.traningproject1.repository;

import java.util.Currency;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.traningproject1.domain.CurrencyClass;
@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyClass,Integer> {

	Currency findBycoinName(String coinType);

	List<CurrencyClass> findAll();

	CurrencyClass findByCoinName(String coinName);

	Integer findByFees(String coinName);

	CurrencyClass findByCoinId(Integer coinId);

	CurrencyClass findBySymbol(String sym);



}
