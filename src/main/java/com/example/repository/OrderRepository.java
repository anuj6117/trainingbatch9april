package com.example.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.enums.OrderType;
import com.example.model.Currency;
import com.example.model.UserOrder;

public interface OrderRepository extends JpaRepository<UserOrder,Integer> {
	UserOrder findByOrderId(Integer id);
	@Query(value="SELECT * FROM orders WHERE order_type=?1 ",nativeQuery=true)
	Set<UserOrder> useList(String ordertype);
/*	 @Query(value="SELECT * FROM currency  WHERE price=?1 ",nativeQuery=true)
	   Currency myPrice(Integer price);*/
}
