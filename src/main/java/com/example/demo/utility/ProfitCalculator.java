package com.example.demo.utility;

public class ProfitCalculator {
	
	public static Double calculateProfit(Double price,Integer coinQuantity, Integer fee) {
		price= (price*fee)/100;
		return price;
	}	
	
}
