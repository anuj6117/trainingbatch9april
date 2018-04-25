package com.training.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.training.demo.model.UserOrder;



public interface UserOrderRepository extends JpaRepository<UserOrder, Integer> {

	 UserOrder findByUserOrderId(Integer userorderId) ;
		// TODO Auto-generated method stub
		
	
	
	
		

}
