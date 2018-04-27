package com.example.dto;

import com.example.enums.WalletType;

public class UserOrderDto
{
 private Integer userId;
 private Integer coinQuantity;
 private Integer price;
 private String coinName;
/* private WalletType coinType;
 
 
public WalletType getCoinType() {
	return coinType;
}
public void setCoinType(WalletType coinType) {
	this.coinType = coinType;
}*/
public Integer getUserId() {
	return userId;
}
public void setUserId(Integer userId) {
	this.userId = userId;
}
public Integer getCoinQuantity() {
	return coinQuantity;
}
public void setCoinQuantity(Integer coinQuantity) {
	this.coinQuantity = coinQuantity;
}
public Integer getPrice() {
	return price;
}
public void setPrice(Integer price) {
	this.price = price;
}
public String getCoinName() {
	return coinName;
}
public void setCoinName(String coinName) {
	this.coinName = coinName;
}
 
 
}
