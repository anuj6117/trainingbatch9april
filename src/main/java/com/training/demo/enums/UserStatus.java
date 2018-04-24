package com.training.demo.enums;

public enum UserStatus {
	INACTIVE("inactive"), ACTIVE("active");

	private String value;

	public String getValue() {
		return value;
	}

	private UserStatus(String value) {
		this.value = value;
	}
}