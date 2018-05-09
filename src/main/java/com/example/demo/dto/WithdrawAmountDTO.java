package com.example.demo.dto;

import com.example.demo.enumeration.CoinType;

public class WithdrawAmountDTO {
	private Integer userId;
	private CoinType coinType;
	private Integer amount;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public CoinType getCoinType() {
		return coinType;
	}
	public void setCoinType(CoinType coinType) {
		this.coinType = coinType;
	}

}
