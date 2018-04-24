package com.example.demo.enums;

public enum OrderType {

	BUYER("Buyer"),SELLER("Seller");
	
	private String value;
	
	OrderType(String value) {
		this.value = value;		
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
