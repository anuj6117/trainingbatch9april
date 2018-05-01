package com.example.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.model.Currency;

import antlr.collections.List;

public interface CurrencyRepository extends JpaRepository<Currency,Integer>
{
   public Currency findByCoinId(Integer coinId);
   Currency findByCoinName(String coinName);
   Currency findBySymbol(String symbol);
   Currency findByInitialSupply(Integer supply);
   Currency findByPrice(Integer price);
   @Query(value="SELECT * FROM currency  WHERE price=?1 ",nativeQuery=true)
   Currency myPrice(Integer price);
   @Query(value="SELECT * FROM currency  WHERE price=?1 ",nativeQuery=true)
   public Set<Currency> buylist(Integer price);
}
