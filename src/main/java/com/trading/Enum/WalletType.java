package com.trading.Enum;

public enum WalletType {
	FIAT("fiat"), CRYPTO("crypto");

	private String value;

	public String getValue() {
		return value;
	}

	WalletType(String value) {
		this.value = value;
	}

}
