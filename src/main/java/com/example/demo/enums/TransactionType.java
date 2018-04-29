package com.example.demo.enums;

public enum TransactionType {

	DEPOSIT("Deposit"), WITHDRAW("Withdraw"), SELL("Sell"), BUY("Buy");
	
	private String value;
	
	TransactionType(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}	
	
}
