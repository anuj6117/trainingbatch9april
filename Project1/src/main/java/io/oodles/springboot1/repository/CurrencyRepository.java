package io.oodles.springboot1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.oodles.springboot1.model.Currency;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Integer> {

	Currency findByCoinName(String coinName);

}
