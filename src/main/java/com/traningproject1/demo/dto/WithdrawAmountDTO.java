package com.traningproject1.demo.dto;

import com.traningproject1.enumsclass.WalletType;

public class WithdrawAmountDTO {
	private Integer userId;
	private WalletType walletType;
	private Integer amount;
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
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}

}
