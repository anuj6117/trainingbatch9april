package com.traningproject1.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.traningproject1.enumsclass.TransactionStatus;
import com.traningproject1.enumsclass.WalletType;
@Entity
@Table(name="Transaction")
public class Transaction {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
private Integer tranId;
private Integer amount;
private WalletType walletType;
private long fees;
private long exchangeRate;
private long grossAmount;
//private Buyer buyerId;
//private Seller sellerId;
private TransactionStatus status;

private  Date dateCreated;

public Integer getTranId() {
	return tranId;
}

public void setTranId(Integer tranId) {
	this.tranId = tranId;
}

public Integer getAmount() {
	return amount;
}

public void setAmount(Integer amount) {
	this.amount = amount;
}

public WalletType getWalletType() {
	return walletType;
}

public void setWalletType(WalletType walletType) {
	this.walletType = walletType;
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

public long getGrossAmount() {
	return grossAmount;
}

public void setGrossAmount(long grossAmount) {
	this.grossAmount = grossAmount;
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
}
