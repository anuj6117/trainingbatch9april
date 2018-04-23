package io.oodles.springboot1.enums;

public enum TransactionStatus {
	PENDING("pending"),
	APPROVED("approved"),
	FAILED("failed");
	
	String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private TransactionStatus(String value) {
		this.value = value;
	}
	

}
