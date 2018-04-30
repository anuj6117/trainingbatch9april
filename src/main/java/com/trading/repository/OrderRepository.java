package com.trading.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trading.Enum.OrderType;
import com.trading.Enum.TransactionOrderStatus;
import com.trading.Enum.WalletType;
import com.trading.domain.User;
import com.trading.domain.UserOrder;

public interface OrderRepository extends JpaRepository<UserOrder, Long> {

	UserOrder findOneByUser(User user);

	UserOrder findByOrderId(long orderId);

	
	
	UserOrder findMaxByPrice(long price);
	
	UserOrder findMinByPrice(long price);

	UserOrder findByOrderTypeAndStatus(OrderType orderType, TransactionOrderStatus status);

	
	List<UserOrder> findByOrderTypeAndStatusAndCoinType(OrderType orderType, TransactionOrderStatus status, WalletType coinType);


	List<UserOrder> findByOrderTypeAndStatusAndCoinName(OrderType orderType, TransactionOrderStatus status, String coinName);

	UserOrder findByCoinNameAndUser(String coinName, User user);
}
