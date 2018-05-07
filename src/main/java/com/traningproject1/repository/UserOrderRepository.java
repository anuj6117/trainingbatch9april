package com.traningproject1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.traningproject1.domain.User;
import com.traningproject1.domain.UserOrder;
@Repository
public interface UserOrderRepository extends JpaRepository<UserOrder,Integer> {

	UserOrder findByuserorderId(Integer userorderId);
	
	@Query("select od from UserOrder od where UPPER(od.orderType)=UPPER(:orderType) and status = 'PENDING' order by price DESC")
	public List<UserOrder> getBuyers(@Param("orderType") String type);
	
	@Query("select od from UserOrder od where UPPER(od.orderType)=UPPER(:orderType) and status = 'PENDING' order by price ASC")
	public List<UserOrder> getSellers(@Param("orderType") String type);

	@Query(value=" select * from user_order where user_user_id =?1 and coin_name =?2",nativeQuery=true)
	public List<UserOrder> walletHistory(Integer userId, String coinName);
	
	//public List<UserOrder> findAllByUserAndCoinName(User user,String coinName);
	
	//@Query("select od from UserOrder od where UPPER(od.userId)=UPPER(:userId) and coinName = ":"order by price DESC")
	//public List<UserOrder> getHistory(@Param("userId") String type);
}

