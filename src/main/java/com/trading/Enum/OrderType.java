package com.trading.Enum;

public enum OrderType {

BUYER("buyer"),SELLER("seller");

private String value;

	public String getValue() {
		return value;
	}

	private OrderType(String value) {
		this.value = value;
	}
	
	
	
		}