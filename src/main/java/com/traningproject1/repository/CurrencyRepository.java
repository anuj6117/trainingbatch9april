package com.traningproject1.repository;

import java.util.Currency;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.traningproject1.domain.CurrencyClass;
@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyClass,Integer> {

	Currency findBycoinName(String coinType);



}
