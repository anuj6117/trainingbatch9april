package com.example.demo.utilities;

import com.example.demo.model.OrderDetails;

import java.util.Comparator;

public class BuyerIncreasingPriceComparator implements Comparator<OrderDetails> {

    @Override
    public int compare(OrderDetails obj1,OrderDetails obj2){
        return obj1.getPrice().compareTo(obj2.getPrice());
    }
}
