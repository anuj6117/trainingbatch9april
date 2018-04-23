package com.trainingproject.enums;

public enum TransactionStatus {
Pending("Transaction pending"),
Approved("Transaction approved"),
Failed("Transaction failed");
	private String value;
	private TransactionStatus(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
