package com.training.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.training.demo.enums.OrderType;
import com.training.demo.model.OrderTable;

public interface OrderRepository extends JpaRepository<OrderTable,Integer>{

	OrderTable findOneByOrderId(Integer orderId);
	List<OrderTable> findAllByOrderType(OrderType orderType);

	@Query("select od from OrderTable od where UPPER(od.orderType)=UPPER(:ordertype) and LOWER(od.coinName) = LOWER(:coinname) and od.orderStatus = 'PENDING' order by price DESC")
	public List<OrderTable> getBuyer(@Param("ordertype")String order, @Param("coinname")String coinName);
	
	@Query("select od from OrderTable od where UPPER(od.orderType)=UPPER(:ordertype) and LOWER(od.coinName) = LOWER(:coinname) and od.orderStatus = 'PENDING' order by price ASC")
	public List<OrderTable> getSeller(@Param("ordertype")String order, @Param("coinname")String coinName);

/*	@Query("select od from OrderTable od where UPPER(od.orderType)=UPPER(:ordertype) and od.orderStatus = 'PENDING' and od.coinName = 'Etherium' order by price DESC")
	public List<OrderTable> getEtheriumBuyer(@Param("ordertype")String order);
	
	@Query("select od from OrderTable od where UPPER(od.orderType)=UPPER(:ordertype) and od.orderStatus = 'PENDING' order by price ASC")
	public List<OrderTable> getEtheriumSeller(@Param("orderType")String order);*/
	
	/*@Query("select od from OrderTable od where UPPER(od.orderType)=UPPER(:ordertype) and od.orderStatus = 'PENDING' and od.coinName = 'bitcoin' order by price DESC")
	public List<OrderTable> getBitcoinBuyer(@Param("ordertype")String order);
	
	@Query("select od from OrderTable od where UPPER(od.orderType)=UPPER(:ordertype) and od.orderStatus = 'PENDING' order by price ASC")
	public List<OrderTable> getBitcoinSeller(@Param("orderType")String order);	
*/
	}
