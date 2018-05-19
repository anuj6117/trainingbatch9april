package com.example.demo.repository;

import com.example.demo.enums.OrderStatus;
import com.example.demo.enums.OrderType;
import com.example.demo.model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface OrderRepository extends JpaRepository<OrderDetails,Integer> {
    public OrderDetails findOneByOrderId(Integer orderId);
    public List<OrderDetails> findOneByUserId(Integer userId);
    public List<OrderDetails> findByOrderTypeAndCoinName(OrderType orderType,String coinName);
    public OrderDetails findOneByPriceAndOrderType(Integer price,OrderType order);
    public OrderDetails findFirstByCoinNameAndOrderStatusAndOrderTypeOrderByPriceDesc(String coinName, OrderStatus orderStatus,OrderType orderType);
    public OrderDetails findFirstByCoinNameAndOrderStatusAndOrderTypeOrderByPriceAsc(String coinName,OrderStatus orderStatus,OrderType orderType);
    public List<OrderDetails> findByOrderTypeAndOrderStatus(OrderType orderType, OrderStatus orderStatus);
}
