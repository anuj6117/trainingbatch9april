package com.example.controller;


import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.UserOrderDto;
import com.example.enums.UserStatus;
import com.example.enums.WalletType;
import com.example.model.Currency;
import com.example.model.User;
import com.example.model.UserOrder;
import com.example.model.Wallet;
import com.example.repository.CurrencyRepository;
import com.example.repository.UserRepository;
import com.example.service.OrderService;

@RestController
public class OrderController
{
 private User user;
 private UserOrder userorder=new UserOrder();
 @Autowired
 private UserRepository userRepository;
 @Autowired
 private OrderService orderService;
 Currency currency=new Currency();
 @Autowired
 private CurrencyRepository currencyRepository;
	
   @RequestMapping(value="/createbuyorder",method=RequestMethod.POST)
	public String createBuyOrder(@RequestBody UserOrderDto userOrderDto )
	{
	   user=userRepository.findByUserId(userOrderDto.getUserId());
	   if(user.getStatus()==UserStatus.ACTIVE)
	   {
	   currency=currencyRepository.findByCoinName(userOrderDto.getCoinName());
	   userorder.setUser(user);
	   return  orderService.createBUYORDER(userOrderDto, currency,userorder);
	   }
	   else
		return "Invalid User";
	}
   
   
   @RequestMapping(value="/createSellorder",method=RequestMethod.POST)
	public String createSellOrder(@RequestBody UserOrderDto userOrderDto )
	{
		/*user=userRepository.findByUserId(userOrderDto.getUserId());
		if(user!=null)
		{
		  userorder.setOrderId(userOrderDto.getUserId());
		  orderService.createSellOrder(userOrderDto);	
		}
		return "user is null..........";
*/	
	   User user1=
	   user=userRepository.findByUserId(userOrderDto.getUserId());
	   if(user.getStatus()==UserStatus.ACTIVE)
	   { 
		   Set<Wallet> list=user.getWallet();
		   for(Wallet s:list)
		     {
		    	 
		   
		    	 if(s.getWalletType()==WalletType.CRYPTOCURRENCY && (s.getWalletName()==userOrderDto.getCoinName()))
		    	 {
		    		 currency=currencyRepository.findByCoinName(userOrderDto.getCoinName());
		    		   userorder.setUser(user);	
		    		   return  orderService.createSELLORDER(userOrderDto, currency,userorder);
		        		 
		    	 }
		     }
		  
	  
	   return "";
	  
	   }
	   else
		return "Invalid User";   
	}
   @RequestMapping(value="/transaction")
   public String transactionMethod()
   {
	   return orderService.transactionMethod();
   }
}
