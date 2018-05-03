package com.training.demo.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.training.demo.enums.OrderType;
import com.training.demo.model.OrderTable;

public interface OrderRepository extends JpaRepository<OrderTable,Integer>{

	OrderTable findOneByOrderId(Integer orderId);
	List<OrderTable> findAllByOrderType(OrderType orderType);
		
/*
	@Query("select od from OrderTable od where UPPER(od.orderType)=UPPER(:ordertype) and LOWER(od.coinName) = LOWER(:coinname) and od.orderStatus = 'PENDING' order by price DESC")
	public List<OrderTable> getBuyer(@Param("ordertype")String order, @Param("coinname")String coinName);
	
	@Query("select od from OrderTable od where UPPER(od.orderType)=UPPER(:ordertype) and LOWER(od.coinName) = LOWER(:coinname) and od.orderStatus = 'PENDING' order by price ASC")
	public List<OrderTable> getSeller(@Param("ordertype")String order, @Param("coinname")String coinName);

	@Query("select od from OrderTable od where UPPER(od.orderType)=UPPER(:ordertype) and od.orderStatus = 'PENDING' order by price DESC")
	public List<OrderTable> getBuyer(@Param("ordertype")String order);
	
	@Query("select od from OrderTable od where UPPER(od.orderType)=UPPER(:ordertype) and od.orderStatus = 'PENDING' order by price ASC")
	public List<OrderTable> getSeller(@Param("ordertype")String order);
	
	@Query(value="select p from #{#entityName} p where p.id=:projectId and p.projectName=:projectName")
	List<Project> findAll(@Param("projectId") int projectId, @Param("projectName") String projectName);

public interface UserRepository extends JpaRepository<User, Long> {
  @Query(value = "SELECT * FROM USERS WHERE LASTNAME = ?1",
    countQuery = "SELECT count(*) FROM USERS WHERE LASTNAME = ?1",
    nativeQuery = true)
  Page<User> findByLastname(String lastname, Pageable pageable);

*/	
	
	@Query(value="SELECT * FROM order_table WHERE fk_user_id = ?1 AND coin_name = ?2", nativeQuery = true)
	List<OrderTable> getWalletHistory(@Param("userId") Integer userId, @Param("coinName") String coinName);
	
	}