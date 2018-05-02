package com.trading.Enum;

public enum RoleType {

	ADMIN("admin"), USER("user"), MANAGER("manager");

	private String value;

	public String getValue() {
		return value;
	}

	private RoleType(String value) {
		this.value = value;
	}

}