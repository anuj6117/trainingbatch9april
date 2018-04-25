package com.trainingproject.enums;

public enum Status {
	
	INACTIVE("inactive"),
	ACTIVE("active"),
	PENDING("pending"),
	MODERATE("moderate"),
	FAILED("failed"),
	APPROVED("approved"),
	REJECTED("rejected");
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
