package com.trainingproject.dto;

import com.trainingproject.enums.WalletType;

public class WithdrawDepositBean {

	Integer userId;
	WalletType walletType;
	long amount;
	
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
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
	
}
