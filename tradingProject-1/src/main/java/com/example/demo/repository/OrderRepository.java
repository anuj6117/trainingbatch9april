package com.example.demo.repository;

import com.example.demo.enums.OrderType;
import com.example.demo.model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderDetails,Integer> {
    public OrderDetails findOneByOrderId(Integer orderId);
    public List<OrderDetails> findOneByUserId(Integer userId);
    public List<OrderDetails> findByOrderTypeAndCoinName(OrderType orderType,String coinName);
    public OrderDetails findOneByPriceAndOrderType(Integer price,OrderType order);
}
