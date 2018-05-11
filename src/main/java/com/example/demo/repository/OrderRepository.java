package com.example.demo.repository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Order;
import com.example.demo.model.User;

public interface OrderRepository extends JpaRepository<Order, Integer> 
{
	public Order findByOrderId(Integer orderId);
	
	public List<Order> findByCoinName(String coinName);
	
	/*@Query("select odr from Order odr where UPPER(odr.orderType)=UPPER(:ordertype) and UPPER(odr.orderStatus)=UPPER(:orderStatus) order by price DESC")
	public List<Order> getBuyer(@Param("ordertype")String order,@Param("orderStatus")String status);
	
	@Query("select odr from Order order where UPPER(odr.orderType)=UPPER(:ordertype) and UPPER(odr.orderStatus)=UPPER(:orderStatus) order by price ASC")
	public List<Order> getSeller(@Param("ordertype")String order,@Param("orderStatus")String status);
*/	
	@Query("select odr from Order odr where UPPER(odr.orderType)=UPPER(:orderType) and orderStatus = 'PENDING' order by price DESC")
	public CopyOnWriteArrayList<Order> getBuyers(@Param("orderType")String order);
	
	@Query("select odr from Order odr where UPPER(odr.orderType)=UPPER(:orderType) and orderStatus = 'PENDING' order by price ASC")
	public CopyOnWriteArrayList<Order> getSellers(@Param("orderType")String order);
	
	public List<Order> findOrderByUserAndCoinName(User user, String CoinName);
}
