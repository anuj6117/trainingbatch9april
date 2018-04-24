package com.trading.Enum;

public enum RoleType {

	ADMIN("admin"), USER("user");

	private String value;

	public String getValue() {
		return value;
	}

	private RoleType(String value) {
		this.value = value;
	}

}