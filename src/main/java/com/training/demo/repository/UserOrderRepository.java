package com.training.demo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.training.demo.enums.OrderType;
import com.training.demo.enums.UserOrderStatus;
import com.training.demo.model.UserOrder;



public interface UserOrderRepository extends JpaRepository<UserOrder, Integer> {

	 UserOrder findByUserOrderId(Integer userorderId) ;
		// TODO Auto-generated method stub

	List<UserOrder> findByorderTypeAndStatus(OrderType buyer, UserOrderStatus pending);

	//List<UserOrder> findByorderTypeAndStatus(OrderType seller, UserOrderStatus pending);

	//List<UserOrder> findByorderTypeAndOrderStatus(OrderType seller, UserOrderStatus pending);

	//ArrayList<UserOrder> findAllByOrderTypeAndUserOrderStatus(String string, String string2);

//	ArrayList<UserOrder> findAllByOrderTypeAndUserOrderStatus(String string, String string2);
		
	
	
	
	@Query(value="SELECT * FROM userorder WHERE user_id=?1 AND coin_name=?2",nativeQuery=true)
	Set<UserOrder> history(Integer id,String coinName);

}
