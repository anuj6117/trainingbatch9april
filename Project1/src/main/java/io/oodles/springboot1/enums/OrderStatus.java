package io.oodles.springboot1.enums;

public enum OrderStatus {
	PENDING("pending"),
	FAILED("failed"),
	REJECTED("rejected"),
	APPROVE("approve");
	String value;

	private OrderStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	

}
