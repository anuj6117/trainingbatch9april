package com.trainingproject.utils;

import java.util.Comparator;

import com.trainingproject.domain.UserOrder;

public class UserOrderMaxBuyerPrice implements Comparator<UserOrder> {
	@Override
    public int compare(UserOrder uo1, UserOrder uo2) {
        return uo1.getPrice().compareTo(uo2.getPrice());
    }
}
