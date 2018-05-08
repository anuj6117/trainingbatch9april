package io.oodles.springboot1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.oodles.springboot1.model.UserOrder;
import io.oodles.springboot1.model.Users;

@Repository
public interface OrderRepository extends JpaRepository<UserOrder, Integer> {

	
@Query(value="SELECT * FROM user_order u WHERE u.ordertype='BUY' AND u.order_status='PENDING'",nativeQuery=true)
public List<UserOrder> findByOrdertype1();
	
@Query(value="SELECT * FROM user_order u WHERE u.ordertype='SELL' AND u.order_status='PENDING'",nativeQuery=true)
public List<UserOrder> findByOrdertype();





}
