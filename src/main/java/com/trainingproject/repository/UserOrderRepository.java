package com.trainingproject.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trainingproject.domain.User;
import com.trainingproject.domain.UserOrder;
import com.trainingproject.enums.OrderType;
import com.trainingproject.enums.Status;

@Repository
public interface UserOrderRepository extends JpaRepository<UserOrder, Integer> {

	UserOrder findByOrderId(Integer orderId);

	

	//ArrayList<UserOrder> findAllByOrderTypeAndStatus(OrderType orderType, Status status);



	ArrayList<UserOrder> findAllByOrderTypeAndStatus(OrderType buyer, Status pending);



	UserOrder findByCoinNameAndUser(String coinName, User user);

	

}
