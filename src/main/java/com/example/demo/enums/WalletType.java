package com.example.demo.enums;

public enum WalletType {

	FIAT("Fiat"), CRYPTO("Crypto");
	
	private String values;
	
	private WalletType(String values)
	{
		this.values = values;
	}

	public String getValues() {
		return values;
	}

	public void setValues(String values) {
		this.values = values;
	}
	

}
