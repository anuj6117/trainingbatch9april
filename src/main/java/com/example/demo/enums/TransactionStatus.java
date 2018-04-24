package com.example.demo.enums;

public enum TransactionStatus {
	
	PENDING("Pending"), APPROVED("Approved"), FAILED("Failed");
	
	private String value;
	
	TransactionStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}	

}
