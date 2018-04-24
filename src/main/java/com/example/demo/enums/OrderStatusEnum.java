package com.example.demo.enums;

public enum OrderStatusEnum {
	PENDING("Pending"), MODERATE("Moderate"),COMPLETE("Complete");
	
	private String value;
	
	private OrderStatusEnum(String value)
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
