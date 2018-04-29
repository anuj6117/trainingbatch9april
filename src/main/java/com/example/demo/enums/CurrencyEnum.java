package com.example.demo.enums;

public enum CurrencyEnum {

FIAT("Fiat"), CRYPTO("Crypto");
	
	private String value;
	
	CurrencyEnum(String value) {
		this.value = value;		
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
