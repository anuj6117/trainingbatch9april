package com.trainingproject.dto;

import com.trainingproject.enums.WalletType;

public class AssignWalletBean {

	private Integer userId;
	private WalletType walletType;
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
