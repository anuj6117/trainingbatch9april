package com.example.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.example.enums.WalletType;

public class UserWalletDto
 {
  private Integer userId;
  private String walletName;
  @Enumerated(EnumType.STRING)
  private WalletType walletType;
  private Integer amount;
  
  
  

public String getWalletName() {
	return walletName;
}
public void setWalletName(String walletName) {
	this.walletName = walletName;
}
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
