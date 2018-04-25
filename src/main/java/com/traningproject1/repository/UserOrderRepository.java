package com.traningproject1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.traningproject1.domain.UserOrder;
@Repository
public interface UserOrderRepository extends JpaRepository<UserOrder,Integer> {

	UserOrder findByuserorderId(Integer userorderId);

	

}
