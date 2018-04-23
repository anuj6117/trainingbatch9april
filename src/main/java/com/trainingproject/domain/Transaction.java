package com.trainingproject.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.trainingproject.enums.TransactionStatus;
import com.trainingproject.enums.WalletType;

@Entity
@Table(name="transaction")
public class Transaction {

	@Id
	@GeneratedValue
	private Integer transactionId;
	private long amount;
	private WalletType walletType;
	private long fee;
	private long exchangeRate;
	private long grossAmount;
	private String buyer;
	 @Enumerated(EnumType.STRING)
	private TransactionStatus status;
	private Date date;
	
	public Integer getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public WalletType getWalletType() {
		return walletType;
	}
	public void setWalletType(WalletType walletType) {
		this.walletType = walletType;
	}
	public long getFee() {
		return fee;
	}
	public void setFee(long fee) {
		this.fee = fee;
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
	public String getBuyer() {
		return buyer;
	}
	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}
	
	public TransactionStatus getStatus() {
		return status;
	}
	public void setStatus(TransactionStatus status) {
		this.status = status;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
}
