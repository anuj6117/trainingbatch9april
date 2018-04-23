package com.trading.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trading.domain.UserOrder;

public interface OrderRepository extends JpaRepository<UserOrder, Long> {

	

}
