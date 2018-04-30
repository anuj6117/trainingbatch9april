package com.trading.utilities;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.trading.domain.UserOrder;

public class UserOrderComparator implements Comparator<UserOrder>{
private List<Comparator<UserOrder>> listComparators;
 
    @SafeVarargs
    public  UserOrderComparator(Comparator<UserOrder>... comparators) {

        this.listComparators = Arrays.asList(comparators);
    }
 
    @Override
    public int compare(UserOrder ord1, UserOrder ord2) {
        for (Comparator<UserOrder> comparator : listComparators) {
            int result = comparator.compare(ord1, ord2);
            if (result != 0) {
                return result;
            }
        }
        return 0;
    }
}

