package com.example.demo.repository;

import com.example.demo.model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderDetails,Integer> {
    public OrderDetails findOneByOrderId(Integer orderId);
}
