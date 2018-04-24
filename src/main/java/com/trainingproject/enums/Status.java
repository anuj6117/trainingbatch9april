package com.trainingproject.enums;

public enum Status {
	
	INACTIVE("inactive"),ACTIVE("active"),pending("pending"),complete("complete"),moderate("moderate");
	private String value;

	private Status(String value) {
	this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	
	

}
