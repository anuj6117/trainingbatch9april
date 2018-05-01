package com.trading.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.trading.Enum.WalletType;

public class UserWalletDto {

	private long userId;
	
	@Enumerated(EnumType.STRING)
	private WalletType coinType;
	private Integer amount;
	
	private String coinName;
	
	private long balance;
	private long shadowBalance;

	public long getBalance() {
		return balance;
	}

	public void setBalance(long balance) {
		this.balance = balance;
	}

	public long getShadowBalance() {
		return shadowBalance;
	}

	public void setShadowBalance(long shadowBalance) {
		this.shadowBalance = shadowBalance;
	}

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

	public Integer getamount() {
		return amount;
	}

	public void setamount(Integer amount) {
		this.amount = amount;
	}
}
