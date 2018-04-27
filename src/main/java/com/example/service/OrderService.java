package com.example.service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.UserOrderDto;
import com.example.enums.OrderType;
import com.example.enums.StatusType;
import com.example.model.Currency;
import com.example.model.UserOrder;
import com.example.repository.CurrencyRepository;
import com.example.repository.OrderRepository;

@Service
public class OrderService
{   static Integer fee;
	//private Currency currency=new Currency();
	//private 
	@Autowired
	private OrderRepository orderRepository;
	private CurrencyRepository currencyRepository;
	Set<UserOrder> buyList=new HashSet<UserOrder>();
	Set<UserOrder> sellList=new HashSet<UserOrder>();
 public String createBUYORDER(UserOrderDto userOrderDto,Currency currency,UserOrder userorder)
 {
	try
	{
	buyList.add(userorder);	
	String date =new Date()+"";
	userorder.setOrderType(OrderType.BUY);
	userorder.setCoinName(userOrderDto.getCoinName());
	userorder.setCoinType(currency.getCoinType());
	userorder.setStatusType(StatusType.PENDING);
	userorder.setCoinQuantity(userOrderDto.getCoinQuantity());
	userorder.setPrice(currency.getPrice());
	userorder.setFees(currency.getFees());
	int netamount=userOrderDto.getCoinQuantity()*currency.getPrice();
	System.out.println(netamount+"..........netamount........");
	userorder.setNetAmount(netamount);
	int fee=currency.getFees();
    int percentValue=((netamount/100)*fee)+netamount;	
	System.out.println(percentValue+"..........percentvalue........");
	userorder.setGrossAmount(percentValue);
	userorder.setOrderCreatedOn(date);
	orderRepository.save(userorder);
   return "Order Table entry created";
	}
	catch(NullPointerException e)
	{
		return "null value occuring";
	}
 }
   
 public String transactionMethod()
 {
	/* Set<UserOrder> orderList=new HashSet<UserOrder>();
	 int 
	 for(int i=0;i<orderList.size();i++)	 
	 {
		 orderList.add(e)
	 }
		*/ 
	 return "";

 }
 
 public String createSELLORDER(UserOrderDto userOrderDto,Currency currency,UserOrder userorder)
 {
	 
    try
    {
     sellList.add(userorder);		
     String date =new Date()+"";
 	userorder.setOrderType(OrderType.SELL);
 	userorder.setCoinName(userOrderDto.getCoinName());
 	userorder.setCoinType(currency.getCoinType());
 	userorder.setStatusType(StatusType.PENDING);
 	userorder.setCoinQuantity(userOrderDto.getCoinQuantity());
 	userorder.setPrice(currency.getPrice());
 	int netamount=userOrderDto.getCoinQuantity()*currency.getPrice();
 	userorder.setNetAmount(netamount);	
 	userorder.setGrossAmount(netamount);
 	userorder.setOrderCreatedOn(date);
 	orderRepository.save(userorder);
     
	 return "Details in order table is inserted for sell";
    }
    catch(NullPointerException e)
    {
      return "value null";    	
    }

 }
}
