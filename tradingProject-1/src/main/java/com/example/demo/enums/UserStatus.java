package com.example.demo.enums;

public enum UserStatus {
INACTIVE("inactive"), ACTIVE("Active");
	
	private String value;
	
	UserStatus(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}	
	
}
