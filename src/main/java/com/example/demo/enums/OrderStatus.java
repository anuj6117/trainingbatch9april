package com.example.demo.enums;

public enum OrderStatus {
	PENDING("Pending"), MODERATE("Moderate"),COMPLETE("Complete");
	
	private String value;
	
	private OrderStatus(String value)
	{
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	

}
