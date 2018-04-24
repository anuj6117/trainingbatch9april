package com.example.demo.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class UserWalletDTO {

	@Enumerated(EnumType.STRING)
	private  String walletType;
	private Integer id;
	
	public String getWalletType() {
		return walletType;
	}
	
	public void setWalletType(String walletType) {
		this.walletType = walletType;
	}
	
	public Integer getId() {
		return id;
	}
		
}
