package com.training.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.training.demo.model.Order;

public interface OrderRepository extends JpaRepository<Order,Integer>{

}
