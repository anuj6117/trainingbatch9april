package com.trainingproject.enums;

public enum OrderType {

	Buy("buyer"),Sell("seller");
	private String value;
	
	private OrderType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
