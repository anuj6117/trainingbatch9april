package com.trainingproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trainingproject.domain.Currency;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency,Integer> {

}
