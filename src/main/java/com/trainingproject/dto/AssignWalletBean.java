package com.trainingproject.dto;

import com.trainingproject.enums.CoinType;

public class AssignWalletBean {

	private Integer userId;
	private CoinType coinType;
	private String coinName;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public CoinType getCoinType() {
		return coinType;
	}
	public void setCoinType(CoinType coinType) {
		this.coinType = coinType;
	}
	public String getCoinName() {
		return coinName;
	}
	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}
	
	
	
}
