package com.training.demo.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.training.demo.enums.OrderType;
import com.training.demo.model.OrderTable;

public interface OrderRepository extends JpaRepository<OrderTable,Integer>{

	OrderTable findOneByOrderId(Integer orderId);
	List<OrderTable> findAllByOrderType(OrderType orderType);
	
	@Query(value="SELECT * FROM order_table WHERE fk_user_id = ?1 AND coin_name = ?2", nativeQuery = true)
	List<OrderTable> getWalletHistory(@Param("userId") Integer userId, @Param("coinName") String coinName);
	
	}