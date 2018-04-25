package com.training.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.training.demo.model.OrderTable;

public interface OrderRepository extends JpaRepository<OrderTable,Integer>{

}
