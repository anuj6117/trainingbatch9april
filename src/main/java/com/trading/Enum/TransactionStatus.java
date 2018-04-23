package com.trading.Enum;

public enum TransactionStatus {
	
	PENDING("pending"), APPROVED("approved"), FAILED("failed");
	
	private String value;

	public String getValue() {
		return value;
	}

	private TransactionStatus(String value) {
		this.value = value;
	}
	
	
	
		}



