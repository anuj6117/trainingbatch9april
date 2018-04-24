package com.example.demo.enums;

public enum Status {
	INACTIVE("inactive"), ACTIVE("active");

	private String value;

	private Status(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
