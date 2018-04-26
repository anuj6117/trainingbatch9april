package com.training.demo.enums;

public enum WalletType {
	FIAT("nhjk") ,CRYPTO("njk") ;
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
