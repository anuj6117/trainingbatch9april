package com.trading.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trading.Enum.WalletType;
import com.trading.domain.User;
import com.trading.domain.UserOrder;

public interface OrderRepository extends JpaRepository<UserOrder, Long> {

	UserOrder findOneByUser(User user);

	UserOrder findByOrderId(long orderId);


}
