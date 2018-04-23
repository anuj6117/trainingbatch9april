package com.training.demo.dto;

import com.training.demo.enums.WalletType;

public class UserWalletDto {
	
	
	private Integer userId;
	//@Enumerated(EnumType.STRING)
	private String walletType;
	private long amount;

	public String getWalletType() {
		return walletType;
	}

	public void setWalletType(String walletType) {
		this.walletType = walletType;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	
	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	

}
