package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.User;
import com.example.demo.domain.UserOrder;
import com.example.demo.domain.Wallet;
import com.example.demo.dto.BuySellOrderDTO;
import com.example.demo.repository.UserOrderRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.WalletRepository;
import com.example.demo.service.UserOrderService;

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
