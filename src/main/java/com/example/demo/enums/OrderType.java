package com.example.demo.enums;

public enum OrderType {
	DEPOSIT("Deposit"), WITHDRAW("Withdraw"),BUYER("Buyer"),SELLER("Seller");
	
	private String value;
	
	private OrderType(String value)
	{
		this.value = value;
	}
	
	public String getValue()
	{
		return value;
	}
	
	public void setValue(String value)
	{
		this.value = value;
	}

}
