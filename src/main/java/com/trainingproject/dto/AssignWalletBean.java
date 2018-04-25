package com.trainingproject.dto;

import com.trainingproject.enums.CoinType;

public class AssignWalletBean {

	private Integer userId;
	private CoinType coinType;
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
