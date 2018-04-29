package com.example.demo.enums;

public enum CoinType {

	FIAT("Fiat"),CRYPTO("Crypto");
	
	private String coinType;
	
	CoinType(String coinType){
		this.coinType = coinType;		
	}

	public String getCoinType() {
		return coinType;
	}

	public void setCoinType(String coinType) {
		this.coinType = coinType;
	}
	
	
}
