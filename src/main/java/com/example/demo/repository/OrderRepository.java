package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.OrderDetails;
import com.example.demo.model.User;

@Repository
public interface OrderRepository extends JpaRepository<OrderDetails, Integer> {
	
	public OrderDetails findAllByUser(User user);
	public OrderDetails findByOrderId(Integer orderId);
	
	/*@Query("select max(price) from OrderDetails where order_status='BUYER'")
	public List<OrderDetails> getBuyer();
	
	@Query("select min(price) from OrderDetails where order_status='SELLER'")
	public List<OrderDetails> getSeller();*/
	
	@Query("select od from OrderDetails od where UPPER(od.orderType)=UPPER(:ordertype) and UPPER(od.orderStatus)=UPPER(:orderStatus) order by price DESC")
	public List<OrderDetails> getBuyer(@Param("ordertype")String order,@Param("orderStatus")String status);
	
	@Query("select od from OrderDetails od where UPPER(od.orderType)=UPPER(:ordertype) and UPPER(od.orderStatus)=UPPER(:orderStatus) order by price ASC")
	public List<OrderDetails> getSeller(@Param("ordertype")String order,@Param("orderStatus")String status);
	
	/*public List<OrderDetails> findMaxByPriceAndOrderStatus(OrderStatus orderStatus);*/
}
