package com.training.demo.dto;

import com.training.demo.enums.WalletType;

public class UserWalletDto {
	
	
	private Integer userId;
	//@Enumerated(EnumType.STRING)
	private WalletType walletType;
	private long amount;
	private String coinName;

	
	public String getCoinName() {
		return coinName;
	}

	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}

	public WalletType getWalletType() {
		return walletType;
	}

	public void setWalletType(WalletType walletType) {
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
