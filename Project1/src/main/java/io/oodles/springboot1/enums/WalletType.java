package io.oodles.springboot1.enums;

public enum WalletType {
	
	
	FIAT("fiat"),
	CRYPTO("crypto");
	
	public String value;
	
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private WalletType(String value) {
		this.value = value;
	}
	

}
