package com.training.demo.repository;

import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.training.demo.model.OrderTable;
import com.training.demo.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction,Integer> {

	@Query("select od from OrderTable od where UPPER(od.orderType)=UPPER(:orderType) and orderStatus = 'PENDING' order by price DESC")
	public CopyOnWriteArrayList<OrderTable> getBuyer(@Param("orderType")String order);
	
	@Query("select od from OrderTable od where UPPER(od.orderType)=UPPER(:orderType) and orderStatus = 'PENDING' order by price")
	public CopyOnWriteArrayList<OrderTable> getSeller(@Param("orderType")String order);
	

}
