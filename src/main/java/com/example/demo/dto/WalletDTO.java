package com.example.demo.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.example.demo.enums.WalletType;

public class WalletDTO {

	private Integer userId;
	private Double balance;
	@Enumerated(EnumType.STRING)
	private WalletType walletType;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public WalletType getWalletType() {
		return walletType;
	}

	public void setWalletType(WalletType walletType) {
		this.walletType = walletType;
	}

	
}
