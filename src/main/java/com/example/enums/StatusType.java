package com.example.enums;

public enum StatusType
{
 PENDING("Pending"),APPROVED("Approved"),REJECTED("Rejected"),FAILED("Failed"),COMPLETED("Completed");
	 String value;

	public String getValue() {
		return value;
	}

	private StatusType(String value) {
		this.value = value;
	}
}
