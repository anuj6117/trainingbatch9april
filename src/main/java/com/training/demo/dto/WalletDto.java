package com.training.demo.dto;

import com.training.demo.enums.WalletType;

public class WalletDto {
	
	private Integer userId;
	
	private String walletType;
	
	public WalletDto() {
		super();
	}
	public WalletDto(Integer userId, String walletType) {
		super();
		this.userId = userId;
		this.walletType = walletType;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getWalletType() {
		return walletType;
	}
	public void setWalletType(String walletType) {
		this.walletType = walletType;
	}
}