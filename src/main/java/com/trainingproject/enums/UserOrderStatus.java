package com.trainingproject.enums;

public enum UserOrderStatus {
	PENDING("pending"),COMPLETE("complete"),MODERATE("moderate");

private String value;
	
	private UserOrderStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
