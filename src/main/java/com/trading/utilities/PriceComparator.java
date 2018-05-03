package com.trading.utilities;

import java.util.Comparator;

import com.trading.domain.UserOrder;

public class PriceComparator implements Comparator <UserOrder>
{
	@Override
    public int compare(UserOrder ord1, UserOrder ord2) {
return Double.compare(ord1.getPrice(), ord2.getPrice());
}}
