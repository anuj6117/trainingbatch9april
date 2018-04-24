package com.trading.Enum;

public enum OrderType {

	BUYER("buyer"), SELLER("seller"), DEPOSIT("deposit");

	private String value;

	public String getValue() {
		return value;
	}

	private OrderType(String value) {
		this.value = value;
	}

}