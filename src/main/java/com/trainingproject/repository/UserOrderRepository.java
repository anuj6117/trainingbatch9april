package com.trainingproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trainingproject.domain.User;
import com.trainingproject.domain.UserOrder;

public interface UserOrderRepository extends JpaRepository<UserOrder,Integer> {

	public List<UserOrder> findByuser(User user);
	
	public UserOrder findByuserorderId(Integer userorderId);
}
