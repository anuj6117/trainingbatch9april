package com.example.demo.enums;

public enum OrderEnum {
	BUYER("Buyer"),SELLER("Seller");
	
	private String value;
	
	private OrderEnum(String value)
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
