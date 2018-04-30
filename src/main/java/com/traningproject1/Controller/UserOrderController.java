package com.traningproject1.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.traningproject1.demo.dto.BuySellOrderDTO;
import com.traningproject1.service.UserOrderService;

@RestController
public class UserOrderController {
	@Autowired
	UserOrderService userorderservice;
@RequestMapping(value="/createsellorder",method=RequestMethod.POST)
public String createSellOrder(@RequestBody BuySellOrderDTO buysellorderdto)
{
	
  return userorderservice.createSellOrder(buysellorderdto);	
}
@RequestMapping(value="/createbuyorder",method=RequestMethod.POST)
public String createBuyOrder(@RequestBody BuySellOrderDTO buysellorderdto)
{
//  if(buysellorderdto.getCoinName().equals())	
  return userorderservice.createBuyOrder(buysellorderdto);	
}
}
