package com.example.demo.enums;

import java.io.Serializable;

public enum WalletEnum implements Serializable{

	FIATE("Fiate"), BITCOIN("BitCoin"), ETHEREUM("Ethereum");
	
	private String value;	

	WalletEnum(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}	
	
}
