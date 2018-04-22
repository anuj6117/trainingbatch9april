package com.trading.dto;

import com.trading.Enum.WalletType;

public class UserWalletDto {

	private long userId;
	private WalletType walletType;
	private long amount;
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public WalletType getwalletType() {
		return walletType;
	}
	public void setwalletType(WalletType walletType) {
		this.walletType = walletType;
	}
	public long getamount() {
		return amount;
	}
	public void setamount(long amount) {
		this.amount = amount;
	}
}
