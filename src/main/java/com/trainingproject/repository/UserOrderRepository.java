package com.trainingproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trainingproject.domain.UserOrder;

public interface UserOrderRepository extends JpaRepository<UserOrder,Integer> {

}
