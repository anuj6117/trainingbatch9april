package com.trading.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.trading.Enum.WalletType;

public class UserWalletDto {

	private long userId;
	
	@Enumerated(EnumType.STRING)
	private WalletType coinType;
	private long amount;
	
	private String coinName;

	public String getCoinName() {
		return coinName;
	}

	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}

	

	public long getuserId() {
		return userId;
	}

	public void setuserId(long userId) {
		this.userId = userId;
	}

	public WalletType getCoinType() {
		return coinType;
	}

	public void setCoinType(WalletType coinType) {
		this.coinType = coinType;
	}

	public long getamount() {
		return amount;
	}

	public void setamount(long amount) {
		this.amount = amount;
	}
}
