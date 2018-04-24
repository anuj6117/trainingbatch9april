package io.oodles.springboot1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.oodles.springboot1.model.UserOrder;

@Repository
public interface OrderRepository extends JpaRepository<UserOrder, Integer> {
	

}
