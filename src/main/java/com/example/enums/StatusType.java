package com.example.enums;

public enum StatusType
{
 PENDING("Pending"),APPROVED("Approved"),REJECTED("Rejected"),FAILED("Failed");
	 String value;

	public String getValue() {
		return value;
	}

	private StatusType(String value) {
		this.value = value;
	}
}
