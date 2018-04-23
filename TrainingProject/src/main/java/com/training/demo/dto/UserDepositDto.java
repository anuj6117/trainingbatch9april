package com.training.demo.dto;

import com.training.demo.enums.WalletType;

public class UserDepositDto {
	// private Integer Id;

	// public Integer getId() {
	// return Id;
	// }
	// public void setId(Integer id) {
	// Id = id;
	// }
	private Integer userId;
	private WalletType walletType;
	private Integer amount;

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

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

}
