package com.training.demo.controller;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.training.demo.enums.OrderType;
import com.training.demo.model.OrderTable;
import com.training.demo.repository.OrderRepository;
import com.training.demo.repository.TransactionRepository;

@RestController
public class TransactionController {
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	TransactionRepository transactionRepository;
	@RequestMapping(value="/maketransaction", method=RequestMethod.GET)
	public String transactionApproval() {
			//Order order = findAllOrderByCoinType();
		//List<OrderTable> buyerOrderList = orderRepository.findAllByOrderType(OrderType.BUYER);
		List<OrderTable> buyerOrderList = orderRepository.getBuyer("BUYER","bitcoin");
		Iterator<OrderTable> buyerIterator = buyerOrderList.iterator();
		
		while(buyerIterator.hasNext())
		{
			OrderTable orderTable = buyerIterator.next();
			System.out.println("Coin name >>>>>>>>> "+orderTable.getCoinName()+" <<<<<<<  Price"+orderTable.getPrice()+" >>>"+orderTable.getOrderStatus());	
		}		
		
		//List<OrderTable> sellerOrderList = orderRepository.findAllByOrderType(OrderType.SELLER);
		//Iterator<OrderTable> sellerIterator = sellerOrderList.iterator();
	
		return null;
	}

}