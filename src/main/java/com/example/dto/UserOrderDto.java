package com.example.dto;

import com.example.enums.WalletType;

public class UserOrderDto
{
 private Integer userId;
 private Double coinQuantity;
 private Double price;
 private String coinName;
/* private WalletType coinType;
 
 
public WalletType getCoinType() {
	return coinType;
}
public void setCoinType(WalletType coinType) {
	this.coinType = coinType;
}*/

 
public String getCoinName() {
	return coinName;
}

public Integer getUserId() {
	return userId;
}

public void setUserId(Integer userId) {
	this.userId = userId;
}

public Double getCoinQuantity() {
	return coinQuantity;
}
public void setCoinQuantity(Double coinQuantity) {
	this.coinQuantity = coinQuantity;
}
public Double getPrice() {
	return price;
}
public void setPrice(Double price) {
	this.price = price;
}
public void setCoinName(String coinName) {
	this.coinName = coinName;
}
 
 
}
