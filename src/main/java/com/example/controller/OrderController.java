package com.example.controller;


import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	   UserOrder userorder=new UserOrder();
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
			     
			     System.out.println("createBuyOrder // ................................ ");
				   if(fiatWallet.getShadowbalance()>=grossamount)
					   
				    {   Integer updateShadowBalance=fiatWallet.getShadowbalance();
				        System.out.println("createBuyOrder // ................................ ");
				         updateShadowBalance=updateShadowBalance-grossamount;
					      fiatWallet.setShadowbalance(updateShadowBalance);
				         System.out.println(",,,,,,,,,,,,,,,,,,,,,,,,"+fiatWallet.getShadowbalance());
					    userorder.setUser(user);
					    System.out.println("createBuyOrder // ................................END END ");
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
	
	   UserOrder userorder=new UserOrder();
	   user=userRepository.findByUserId(userOrderDto.getUserId());
	   if(user.getStatus()==UserStatus.ACTIVE)
	   { 
		   Set<Wallet> list=user.getWallet();
		   for(Wallet s:list)
		     {
			   
		        if(s.getWalletName().equals(userOrderDto.getCoinName()))
		        {
		        	System.out.println("createSellOrder // balance.........................1.......balance "+s.getBalance());
		        	System.out.println("createSellOrder // shadowBalance.........................1.......balance "+userOrderDto.getCoinQuantity());
		        	if(s.getBalance()>=userOrderDto.getCoinQuantity())
		        	{
			        	System.out.println("enterng in createSellOrder.........................2");		        		
		        		currency=currencyRepository.findByCoinName(userOrderDto.getCoinName());
			    		   userorder.setUser(user);	
			    		   Integer updateShadowBalnce=s.getShadowbalance();
			    		   updateShadowBalnce=updateShadowBalnce-userOrderDto.getCoinQuantity();
			    		   s.setShadowbalance(updateShadowBalnce);
			    		   System.out.println("updated shadow balnce in createSellOrder.........."+s.getShadowbalance());
			    		   System.out.println("createSellOrder ends here...................END END");
			    		   return  orderService.createSELLORDER(userOrderDto, currency,userorder);
			        	
		        	}
		        	else
		        	{
		        		return "insufficient quantity to sell";
		        	}
		        	
		        }
		        else
		         {
		        	continue;
		         }
		     }
		   
		   return "coin name not available with seller";
		 
	  
	   	  
	   }
	   else
		return "Invalid User"; 
	   
	  // return "";
	}
   
   @RequestMapping("/showallorder")
   public List<UserOrder> showallorder()
   {
	   return orderrepository.findAll();
	   
   }   
  
   @RequestMapping("/walletHistory")
   public Set<UserOrder> walletHistory(@RequestParam("userId") Integer userId,@RequestParam("coinName") String coinName)
    {
	   
	 return orderrepository.history(userId, coinName);
    }
   
}
