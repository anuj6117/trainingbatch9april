package com.example.enums;

public enum WalletType
{
 FIAT("Fiat"),CRYPTO("Crypto");
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