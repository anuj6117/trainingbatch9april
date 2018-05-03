package com.example.demo.enums;

public enum OrderStatus {
	PENDING("Pending"),APPROVED("Approved"),REJECTED("Rejected"),COMPLETED("completed");
	
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
