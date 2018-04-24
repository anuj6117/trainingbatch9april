package com.example.demo.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.example.demo.enums.WalletEnum;

public class UserDepositWithdrawDTO {

	private Integer id;
	private Integer userId;
	private int amount;
	
	@Enumerated(EnumType.STRING)
	private WalletEnum walletType;
	
	
	public Integer getId() {
		return id;
	}
	
	public Integer getUserId() {
		return userId;
	}
	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public WalletEnum getWalletType() {
		return walletType;
	}
	
	public void setWalletType(WalletEnum walletType) {
		this.walletType = walletType;
	}
	
	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
	
}
