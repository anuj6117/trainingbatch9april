package com.traningproject1.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.traningproject1.demo.dto.BuySellOrderDTO;
import com.traningproject1.domain.User;
import com.traningproject1.domain.UserOrder;
import com.traningproject1.enumsclass.UserOrderStatus;
import com.traningproject1.enumsclass.UserOrderType;
import com.traningproject1.repository.UserOrderRepository;
import com.traningproject1.repository.UserRepository;

@Service
public class UserOrderService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	UserOrderRepository userorderRepository;
	//@Autowired
	//CurrencyRepository currencyRepository;
    public String createSellOrder(BuySellOrderDTO buysellorderdto)
    {
    	User user=userRepository.findByuserId(buysellorderdto.getUserId());
    	System.out.println("+++++++++++++++++++++++++"+user.getUserId());
//    	if(user.getUserId()==null)
//    	{
//    		return "Invalid User";
//    	}
    	UserOrder userorder=new UserOrder();
    
 
    	userorder.setCoinName(buysellorderdto.getCoinName());
    	userorder.setCoinQuantity(buysellorderdto.getCoinQuantity());
    	userorder.setPrice(buysellorderdto.getPrice());
    	userorder.setDateCreated(new Date());
    	userorder.setOrderType(UserOrderType.SELLER);
    	userorder.setStatus(UserOrderStatus.PENDING);
        
    	userorderRepository.save(userorder);
        return "success";
    }
    public String createBuyOrder(BuySellOrderDTO buysellorderdto)
    {
    	User user=userRepository.findByuserId(buysellorderdto.getUserId());
    	
    	if(user.getUserId()==null)
    	{
    		return "Invalid User";
    	}
    	UserOrder userorder=new UserOrder();
    	//userorder.setUserId(user.getUserId());
    	userorder.setCoinName(buysellorderdto.getCoinName());
    	userorder.setCoinQuantity(buysellorderdto.getCoinQuantity());
    	userorder.setPrice(buysellorderdto.getPrice());
    	
    	userorder.setOrderType(UserOrderType.BUYER);
    	userorder.setStatus(UserOrderStatus.PENDING);
        
    	userorderRepository.save(userorder);
    	return null;
    }
    
}
