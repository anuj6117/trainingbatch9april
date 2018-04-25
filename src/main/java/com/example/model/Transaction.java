package com.example.model;

import javax.annotation.Generated;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.example.enums.StatusType;
import com.example.enums.WalletType;

public class Transaction 
{
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private Integer transactionId;
private Integer coinQuantity;
private String name;
@Enumerated(EnumType.STRING)
private WalletType coinType;
@Enumerated(EnumType.STRING)
private StatusType status;
private String transactionCreatedOn;
private Integer netAmount;
private Integer transactionFee;
private Integer exchangeRate;
private Integer grossAmount;
private Integer buyerId;
private Integer sellerId;
private String description;
public Integer getTransactionId() {
	return transactionId;
}
public void setTransactionId(Integer transactionId) {
	this.transactionId = transactionId;
}
public Integer getCoinQuantity() {
	return coinQuantity;
}
public void setCoinQuantity(Integer coinQuantity) {
	this.coinQuantity = coinQuantity;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public WalletType getCoinType() {
	return coinType;
}
public void setCoinType(WalletType coinType) {
	this.coinType = coinType;
}
public StatusType getStatus() {
	return status;
}
public void setStatus(StatusType status) {
	this.status = status;
}
public String getTransactionCreatedOn() {
	return transactionCreatedOn;
}
public void setTransactionCreatedOn(String transactionCreatedOn) {
	this.transactionCreatedOn = transactionCreatedOn;
}
public Integer getNetAmount() {
	return netAmount;
}
public void setNetAmount(Integer netAmount) {
	this.netAmount = netAmount;
}
public Integer getTransactionFee() {
	return transactionFee;
}
public void setTransactionFee(Integer transactionFee) {
	this.transactionFee = transactionFee;
}
public Integer getExchangeRate() {
	return exchangeRate;
}
public void setExchangeRate(Integer exchangeRate) {
	this.exchangeRate = exchangeRate;
}
public Integer getGrossAmount() {
	return grossAmount;
}
public void setGrossAmount(Integer grossAmount) {
	this.grossAmount = grossAmount;
}
public Integer getBuyerId() {
	return buyerId;
}
public void setBuyerId(Integer buyerId) {
	this.buyerId = buyerId;
}
public Integer getSellerId() {
	return sellerId;
}
public void setSellerId(Integer sellerId) {
	this.sellerId = sellerId;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}


}
