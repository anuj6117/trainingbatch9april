package io.oodles.springboot1.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.oodles.springboot1.enums.OrderType;
import io.oodles.springboot1.model.UserOrder;

@Repository
public interface OrderRepository extends JpaRepository<UserOrder, Integer> {

	@Query("SELECT id FROM user_order")
	List<UserOrder> findById(Integer id);

	/*@Query(value="SELECT * FROM user_order where ordertype=io.oodles.springboot1.enums.OrderType
	List<UserOrder> findBuyer();*/
	

	/*@Query(value="SELECT * FROM user_order u WHERE u.ordertype like ?1",nativeQuery=true)
	Set<UserOrder> findBuyer(String ordertype);*/
	
	
	

}
