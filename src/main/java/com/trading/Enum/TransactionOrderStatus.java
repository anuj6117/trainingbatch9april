package com.trading.Enum;

public enum TransactionOrderStatus {

	PENDING("pending"), APPROVED("approved"), FAILED("failed"), REJECTED("rejected");

	private String value;

	public String getValue() {
		return value;
	}

	private TransactionOrderStatus(String value) {
		this.value = value;
	}

}
