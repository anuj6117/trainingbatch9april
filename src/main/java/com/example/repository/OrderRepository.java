package com.example.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.model.User;
import com.example.model.UserOrder;

public interface OrderRepository extends JpaRepository<UserOrder,Integer> {
	UserOrder findByOrderId(Integer id);
	@Query(value="SELECT * FROM orders WHERE order_type=?1 AND status_type=?2 AND coin_name=?3 ORDER BY price ASC ",nativeQuery=true)
	Set<UserOrder> sellList(String ordertype,String status,String coinName);
	@Query(value="SELECT * FROM orders WHERE order_type=?1 AND status_type=?2 ORDER BY price DESC ",nativeQuery=true)
	Set<UserOrder> buyList(String ordertype,String status);
	@Query(value="SELECT * FROM orders WHERE user_id=?1 AND coin_name=?2",nativeQuery=true)
	Set<UserOrder> history(Integer id,String coinName);
	/*@Query(value="SELECT * FROM orders WHERE price=(SELECT * MAX(price) FROM orders WHERE order_type=?1)")
	UserOrder maxList(String orderType,String status);*/
/*	 @Query(value="SELECT * FROM currency  WHERE price=?1 ",nativeQuery=true)
	   Currency myPrice(Integer price);*/
	UserOrder findByUser(User user);
}
