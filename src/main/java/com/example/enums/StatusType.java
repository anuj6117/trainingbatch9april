package com.example.enums;

public enum StatusType
{
 PENDING("Pending"),COMPLETED("Completed"),MODERATE("Moderate");
	 String value;

	public String getValue() {
		return value;
	}

	private StatusType(String value) {
		this.value = value;
	}
}
