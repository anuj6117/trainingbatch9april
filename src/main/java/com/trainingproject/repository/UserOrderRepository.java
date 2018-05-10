package com.trainingproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trainingproject.domain.User;
import com.trainingproject.domain.UserOrder;
import com.trainingproject.enums.CoinType;
import com.trainingproject.enums.OrderType;
import com.trainingproject.enums.UserOrderStatus;

public interface UserOrderRepository extends JpaRepository<UserOrder,Integer> {

	public List<UserOrder> findByuser(User user);
	
	public UserOrder findByorderId(Integer orderId);

	public List<UserOrder> findByorderType(OrderType orderType);
	
	public List<UserOrder> findByorderTypeAndOrderStatus(OrderType orderType,UserOrderStatus orderStatus);
	
	public List<UserOrder> findBycoinTypeAndUser(CoinType coinType,User user);
}
