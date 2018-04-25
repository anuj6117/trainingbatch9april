package com.trainingproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trainingproject.domain.UserOrder;

@Repository
public interface UserOrderRepository extends JpaRepository<UserOrder, Integer> {

	UserOrder findByOrderId(Integer orderId);

}
