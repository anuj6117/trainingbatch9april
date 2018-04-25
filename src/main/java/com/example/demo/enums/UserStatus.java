package com.example.demo.enums;

public enum UserStatus {
	INACTIVE("inactive"), ACTIVE("active");

	private String value;

	private UserStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
