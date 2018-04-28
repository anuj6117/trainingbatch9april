package com.example.demo.enums;

public enum TransactionType {
	BUYER("Buyer"),SELLER("Seller");
	
	public String value;
	
	private TransactionType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	

}
