package com.trading.Enum;

public enum UserStatus {

	INACTIVE("inactive"), Active("active");

	private String value;

	public String getValue() {
		return value;
	}

	private UserStatus(String value) {
		this.value = value;
	}

}
