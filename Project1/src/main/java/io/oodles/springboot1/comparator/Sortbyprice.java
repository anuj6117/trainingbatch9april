package io.oodles.springboot1.comparator;

import java.util.Comparator;

import io.oodles.springboot1.model.UserOrder;

public class Sortbyprice implements Comparator<UserOrder> {

	
	@Override
	public int compare(UserOrder o1, UserOrder o2) {
		// TODO Auto-generated method stub
		return o1.getPrice().compareTo(o2.getPrice());
	}

}
