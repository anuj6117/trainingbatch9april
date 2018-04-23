package com.training.demo.enums;

public enum WalletType {
	FIAT("ghjg"), BITCOIN("vnvn"), ETHERIUM("bhjg");
	private String value;

	private WalletType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
