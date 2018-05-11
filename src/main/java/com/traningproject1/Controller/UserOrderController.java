package com.traningproject1.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.traningproject1.demo.dto.BuySellOrderDTO;
import com.traningproject1.domain.User;
import com.traningproject1.domain.UserOrder;
import com.traningproject1.domain.Wallet;
import com.traningproject1.repository.UserOrderRepository;
import com.traningproject1.repository.UserRepository;
import com.traningproject1.repository.WalletRepository;
import com.traningproject1.service.UserOrderService;

@RestController
public class UserOrderController {
	@Autowired
	UserOrderService userorderservice;
	@Autowired
	UserOrderRepository userOrderRepository;
	@Autowired
	WalletRepository walletRepository;
	@Autowired
	UserRepository userRepository;
	
@RequestMapping(value="/createsellorder",method=RequestMethod.POST)
public String createSellOrder(@RequestBody BuySellOrderDTO buysellorderdto)
{ 
	User user=userRepository.findByUserId(buysellorderdto.getUserId());
	if(user==null)
	{
		return "Invalid user id";
	}
	 String sellerprice=String.valueOf(buysellorderdto.getPrice());
	  if(!(sellerprice.matches("^([0-9]){1,}$")))
	 {
	 	return "Price Can't be negative";
	 }
	  String coinquant=String.valueOf(buysellorderdto.getCoinQuantity());
	  
	  if(!(coinquant.matches("^([0-9]){1,}$")))
	 {
	 	return "Coin Quantity Can't be negative";
	 }
  return userorderservice.createSellOrder(buysellorderdto);	
}

@RequestMapping(value="/createbuyorder",method=RequestMethod.POST)
public String createBuyOrder(@RequestBody BuySellOrderDTO buysellorderdto)
{
	User user=userRepository.findByUserId(buysellorderdto.getUserId());
	if(user==null)
	{
		return "Invalid user id";
	} 
	 String buyerprice=String.valueOf(buysellorderdto.getPrice());
	  if(!(buyerprice.matches("^([0-9]){1,}$")))
	 {
	 	return "Price Can't be negative";
	 }
	  String coinquant=String.valueOf(buysellorderdto.getCoinQuantity());
	  
	  if(!(coinquant.matches("^([0-9]){1,}$")))
	 {
	 	return "Coin Quantity Can't be negative";
	 }
	Wallet wallet=walletRepository.findByUserAndCoinName(user, buysellorderdto.getCoinName());
	if(wallet==null)
	{
		return "please add wallet first";
	}
	 if(!(buysellorderdto.getCoinName().matches("^([a-zA-Z0-9-_]{2,}$)"))){
			return "Coin Name not valid";
		}

  return userorderservice.createBuyOrder(buysellorderdto);	
}


@RequestMapping(value="/walletHistory",method=RequestMethod.GET)
public Object walletHistory(@RequestParam("userId")Integer userId,@RequestParam("coinName") String coinName)
{
	User user=userRepository.findByUserId(userId);
	Wallet wallet=walletRepository.findByUserAndCoinName(user,coinName);
	if(user==null||wallet==null)
	{
		return "Invalid user id or wallet ";
	}
	return userOrderRepository.walletHistory(userId, coinName);
}
@RequestMapping(value="/getallorder",method=RequestMethod.GET)
public List<UserOrder>getAllOrder()
{
	return userorderservice.getAllOrder();
}
@RequestMapping(value="/getorderbyuserid",method=RequestMethod.GET)
public Object getOrderByUserId(@RequestParam("userId")Integer userId)
{
	User user=userRepository.findByUserId(userId);
	if(user==null)
	{
		return "Invalid user id";
	} 
	return userorderservice.getOrderByUserId(userId);
}
}
