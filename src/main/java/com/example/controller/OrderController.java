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
import com.example.repository.OrderRepository;
import com.example.repository.UserRepository;
import com.example.repository.WalletRepository;
import com.example.service.OrderService;

@RestController
public class OrderController
{
 private User user;
 private UserOrder userorder=new UserOrder();
 @Autowired
 private UserRepository userRepository;
 @Autowired
 private OrderRepository orderrepository;
 @Autowired
 private OrderService orderService;
 @Autowired
 private WalletRepository walletRepository;
 Currency currency=new Currency();
 @Autowired
 private CurrencyRepository currencyRepository;
	
   @RequestMapping(value="/createbuyorder",method=RequestMethod.POST)
	public String createBuyOrder(@RequestBody UserOrderDto userOrderDto )
	{
	     
	   user=userRepository.findByUserId(userOrderDto.getUserId());
	   
	   if(user.getStatus()==UserStatus.ACTIVE)
	   {
		   Wallet fiatWallet=null;
		   Wallet cryptoWallet=null;
		 Set<Wallet> list=user.getWallet();
		 System.out.println("...................................."+list);
		 for(Wallet wallet:list)
		 {
			 if(wallet.getWalletType()==WalletType.FIAT)
			 {
				 fiatWallet=wallet;
			 }
			 if((wallet.getWalletType().equals(WalletType.CRYPTOCURRENCY) && (wallet.getWalletName().equals(userOrderDto.getCoinName())))) 
			 {
				 cryptoWallet=wallet;
			 }
		 }
		 if(cryptoWallet!=null)
		 {
			if(fiatWallet!=null)
			 {
				 currency=currencyRepository.findByCoinName(userOrderDto.getCoinName());
				 int netamount=userOrderDto.getCoinQuantity()*userOrderDto.getPrice();
			     int grossamount=((currency.getFees()*netamount)/100)+netamount; 
			     
			     System.out.println("7777777777777777777777"+ fiatWallet.getShadowbalance());
				   if(fiatWallet.getShadowbalance()>=grossamount)
				    {   Integer updateShadowBalance=fiatWallet.getShadowbalance();
				    
				         updateShadowBalance=updateShadowBalance-grossamount;
					      fiatWallet.setShadowbalance(updateShadowBalance);
				         System.out.println(",,,,,,,,,,,,,,,,,,,,,,,,"+fiatWallet.getShadowbalance());
					    userorder.setUser(user);
					    return  orderService.createBUYORDER(userOrderDto, currency,userorder);
					 
				    }
				   else
				     { return "insufficient balance to buy";
				     }
			  }
			else 
				return "fiat wallet not available";
			 
		 }
		 else
		 {
			return "coin not available"; 
		 }
		
	   }
	   else
		return "Invalid User, not active";
	   
	}
   
   
   @RequestMapping(value="/createsellorder",method=RequestMethod.POST)
	public String createSellOrder(@RequestBody UserOrderDto userOrderDto )
	{
	
	   
	   user=userRepository.findByUserId(userOrderDto.getUserId());
	   if(user.getStatus()==UserStatus.ACTIVE)
	   { 
		   Set<Wallet> list=user.getWallet();
		   for(Wallet s:list)
		     {
		        if(s.getWalletName()==userOrderDto.getCoinName())
		        {
		        	if(s.getBalance()>=userOrderDto.getCoinQuantity())
		        	{
		        		currency=currencyRepository.findByCoinName(userOrderDto.getCoinName());
			    		   userorder.setUser(user);	
			    		   return  orderService.createSELLORDER(userOrderDto, currency,userorder);
			        	
		        	}
		        	else
		        	{
		        		return "insufficient quantity to sell";
		        	}
		        	
		        }
		        else
		         {
		        	return "coin name not available with seller";
		         }
		     }
		 
	  
	   	  
	   }
	   else
		return "Invalid User"; 
	   
	   return "";
	}
   
  
}
