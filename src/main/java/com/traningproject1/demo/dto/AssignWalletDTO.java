package com.traningproject1.demo.dto;

import com.traningproject1.enumsclass.WalletType;

public class AssignWalletDTO {
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
