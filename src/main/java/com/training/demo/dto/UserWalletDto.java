package com.training.demo.dto;

import com.training.demo.enums.WalletType;

public class UserWalletDto {

	private Integer userId;
	// @Enumerated(EnumType.STRING)
	private WalletType coinType;
	private long amount;
	private String coinName;

	public WalletType getCoinType() {
		return coinType;
	}

	public void setCoinType(WalletType coinType) {
		this.coinType = coinType;
	}

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

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

}
