package com.trading.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trading.Enum.TransactionOrderStatus;
import com.trading.Enum.WalletType;

@Entity
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long transactionId;

	@Enumerated(EnumType.STRING)
	private WalletType coinType;
	
	private String coinName;

	@Enumerated(EnumType.STRING)
	private TransactionOrderStatus status;

	private long transactionFee;

	private long netAmount;

	private long exchangeRate;

	private long buyerId;

	public long getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(long exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	private long sellerId;

	private long grossAmount;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date transactionCreatedOn;

	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	public long getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(long netAmount) {
		this.netAmount = netAmount;
	}

	public long getGrossAmount() {
		return grossAmount;
	}

	public void setGrossAmount(long grossAmount) {
		this.grossAmount = grossAmount;
	}

	public long getTransactionFee() {
		return transactionFee;
	}

	public void setTransactionFee(long transactionFee) {
		this.transactionFee = transactionFee;
	}

	
	public WalletType getCoinType() {
		return coinType;
	}

	public void setCoinType(WalletType coinType) {
		this.coinType = coinType;
	}

	public String getCoinName() {
		return coinName;
	}

	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}

	public long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(long buyerId) {
		this.buyerId = buyerId;
	}

	public long getSellerId() {
		return sellerId;
	}

	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}

	public TransactionOrderStatus getStatus() {
		return status;
	}

	public void setStatus(TransactionOrderStatus status) {
		this.status = status;
	}

	public Date getTransactionCreatedOn() {
		return transactionCreatedOn;
	}

	public void setTransactionCreatedOn(Date transactionCreatedOn) {
		this.transactionCreatedOn = transactionCreatedOn;
	}

}
