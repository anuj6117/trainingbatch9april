package com.example.demo.enums;

public enum StatusEnum {

	INACTIVE("inactive"), ACTIVE("Active");
	
	private String value;
	
	StatusEnum(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
	
}
