package com.trainingproject.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trainingproject.domain.UserOrder;
import com.trainingproject.enums.OrderType;
import com.trainingproject.enums.UserOrderStatus;
import com.trainingproject.repository.CurrencyRepository;
import com.trainingproject.repository.UserOrderRepository;

@Service
public class TransactionService implements Comparator<UserOrder> {

	@Autowired
	UserOrderRepository orderRepository;
	
	@Autowired
	CurrencyRepository  currencyRepository;
	
	public String approveTransaction() {
	
		List<UserOrder> sellers=orderRepository.findByorderType(OrderType.SELL);
		List<UserOrder> buyers=orderRepository.findByorderType(OrderType.BUY);
		
		Collections.sort(sellers, this);
		Collections.sort(buyers,Collections.reverseOrder());
		
		for(UserOrder sellOrder:sellers) {
			
			if(sellOrder.getOrderStatus().equals(UserOrderStatus.PENDING)) {
				

				
				
				for(UserOrder buyorder:buyers) {
					
					String coinName=buyorder.getCoinName();
					
					if(buyorder.getOrderStatus().equals(UserOrderStatus.PENDING)&&sellOrder.getCoinName().equals(coinName))
					{
						
						Integer scQ=sellOrder.getCoinQuantity();
						Integer bcq=buyorder.getCoinQuantity();
						
						Integer fees=currencyRepository.findBycoinName(coinName).getFees();
						long totprice= (buyorder.getPrice()*buyorder.getCoinQuantity());
						long fee=(fees*totprice)/100;
						long grossAmount=totprice+fee;
						
		
					}
				}
				
			}
		}
		
		return "success";
	}

	@Override
	public int compare(UserOrder o1, UserOrder o2) {
		
		
		return (int) (o1.getPrice()-o2.getPrice());
		
	}

}
