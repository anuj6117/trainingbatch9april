package com.trainingproject.enums;

public enum WalletType {
	
	FIAT("fiat coin"),BITCOIN("bit coin"),ETHEREUM("ethereum coin");
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
