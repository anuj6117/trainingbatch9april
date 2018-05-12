package com.example.demo.repository;

import com.example.demo.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency,Integer> {
    public Currency findOneBySymbol(String symbol);
    public Currency findOneByCoinName(String coinName);
    public Currency findOneByCoinId(Integer coinId);

}
