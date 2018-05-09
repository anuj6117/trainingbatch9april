package com.example.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.example.enums.WalletType;

public class UserWalletDto
 {
  private Integer userId;
  private String coinName;
  @Enumerated(EnumType.STRING)
  private WalletType coinType;
  private Double amount;
  
  
  


public String getCoinName() {
	return coinName;
}
public void setCoinName(String coinName) {
	this.coinName = coinName;
}
public WalletType getCoinType() {
	return coinType;
}
public void setCoinType(WalletType coinType) {
	this.coinType = coinType;
}
public Double getAmount() {
	return amount;
}
public void setAmount(Double amount) {
	this.amount = amount;
}
public Integer getUserId() {
	return userId;
}
public void setUserId(Integer userId) {
	this.userId = userId;
}

  
}
