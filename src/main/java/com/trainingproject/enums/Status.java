package com.trainingproject.enums;

public enum Status {
	INACTIVE("inactive"),ACTIVE("active");
	private String status;

	public String getValue() {
	return status;
	}

	private Status(String status) {
	this.status = status;
	}
	

}
