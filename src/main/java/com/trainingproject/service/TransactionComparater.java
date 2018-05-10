package com.trainingproject.service;

import java.util.Comparator;

import com.trainingproject.domain.UserOrder;
import com.trainingproject.enums.OrderType;

public class TransactionComparater implements Comparator<UserOrder>{

	@Override
	public int compare(UserOrder o1, UserOrder o2) {
	
		
		
		if(o1.getPrice()!=o2.getPrice())
			return (int) (o1.getPrice()-o2.getPrice());
			
			else {
				if(o1.getOrderType()==OrderType.BUY) {
				return -1*o1.getDate().compareTo(o2.getDate());
				}
				else return o1.getDate().compareTo(o2.getDate());
			}
		
	}

}
