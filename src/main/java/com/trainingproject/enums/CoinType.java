package com.trainingproject.enums;

public enum CoinType {
	
	FIAT("fiat coin"),
	CRYPTO("crypto coin");
	//BITCOIN("bit coin"),
	//ETHEREUM("ethereum coin");
	private String value;

	private CoinType(String value) {
	this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
