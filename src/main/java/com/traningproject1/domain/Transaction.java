package com.traningproject1.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.traningproject1.enumsclass.CoinType;
import com.traningproject1.enumsclass.TransactionStatus;
import com.traningproject1.enumsclass.UserOrderType;
@Entity
@Table(name="Transaction")
public class Transaction {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
private Integer tranId;
	@Enumerated(value=EnumType.STRING)
private CoinType coinType;
private long fees;
private long exchangeRate;
private double grossAmount;


private Integer buyerId;
private Integer sellerId;
private String message;
@Enumerated(value=EnumType.STRING)
private UserOrderType userOrderType;

@Enumerated(EnumType.STRING)
private TransactionStatus status;
private Integer netAmount;

private  Date dateCreated;

public Integer getTranId() {
	return tranId;
}

public void setTranId(Integer tranId) {
	this.tranId = tranId;
}




public CoinType getCoinType() {
	return coinType;
}

public void setCoinType(CoinType coinType) {
	this.coinType = coinType;
}

public long getFees() {
	return fees;
}

public void setFees(long fees) {
	this.fees = fees;
}

public long getExchangeRate() {
	return exchangeRate;
}

public void setExchangeRate(long exchangeRate) {
	this.exchangeRate = exchangeRate;
}



public TransactionStatus getStatus() {
	return status;
}

public void setStatus(TransactionStatus status) {
	this.status = status;
}

public Date getDateCreated() {
	return dateCreated;
}

public void setDateCreated(Date dateCreated) {
	this.dateCreated = dateCreated;
}
public Integer getBuyerId() {
	return buyerId;
}

public UserOrderType getUserOrderType() {
	return userOrderType;
}

public void setUserOrderType(UserOrderType userOrderType) {
	this.userOrderType = userOrderType;
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
public Integer getNetAmount() {
	return netAmount;
}

public void setNetAmount(Integer netAmount) {
	this.netAmount = netAmount;
}
public String getMessage() {
	return message;
}

public void setMessage(String message) {
	this.message = message;
}
public double getGrossAmount() {
	return grossAmount;
}

public void setGrossAmount(double grossAmount) {
	this.grossAmount = grossAmount;
}
}
