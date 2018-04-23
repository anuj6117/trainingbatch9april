package com.example.dto;

import com.example.enums.WalletType;

public class UserWalletDto
 {
  private Integer userId;
  private WalletType walletType;
  private Integer amount;
  
public Integer getAmount() {
	return amount;
}
public void setAmount(Integer amount) {
	this.amount = amount;
}
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
