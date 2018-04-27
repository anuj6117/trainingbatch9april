package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Currency;

public interface CurrencyRepository extends JpaRepository<Currency,Integer>
{
   public Currency findByCoinId(Integer coinId);
   Currency findByCoinName(String coinName);
}
