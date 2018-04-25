package com.example.enums;

public enum OrderType
{
BUY("Buy"),SELL("Sell"),DEPOSIT("Deposit");
	String value;

	public String getValue() {
		return value;
	}

	private OrderType(String value) {
		this.value = value;
	}
	
}
