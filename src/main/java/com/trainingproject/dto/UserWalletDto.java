package com.trainingproject.dto;

import com.trainingproject.enums.WalletType;

public class UserWalletDto {
	private Integer userId;
	private WalletType walletType;
	private String coinName;
	private Long amount;
	
	
	
	public String getCoinName() {
		return coinName;
	}
	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public WalletType getWalletType() {
		return walletType;
	}
	public void setWalletType(WalletType walletType) {
		this.walletType = walletType;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	

}
