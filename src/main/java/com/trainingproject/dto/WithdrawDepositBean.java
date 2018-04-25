package com.trainingproject.dto;

import com.trainingproject.enums.CoinType;

public class WithdrawDepositBean {

	Integer userId;
	CoinType coinType;
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
	public CoinType getWalletType() {
		return coinType;
	}
	public void setWalletType(CoinType coinType) {
		this.coinType = coinType;
	}
	
}
