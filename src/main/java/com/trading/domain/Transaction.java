package com.trading.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.trading.Enum.TransactionStatus;
import com.trading.Enum.WalletType;

@Entity
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long transactionId;
	private long netAmount;
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
	public WalletType getWalletType() {
		return walletType;
	}
	public void setWalletType(WalletType walletType) {
		this.walletType = walletType;
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
	public TransactionStatus getStatus() {
		return status;
	}
	public void setStatus(TransactionStatus status) {
		this.status = status;
	}
	public Date getTransactionCreatedOn() {
		return transactionCreatedOn;
	}
	public void setTransactionCreatedOn(Date transactionCreatedOn) {
		this.transactionCreatedOn = transactionCreatedOn;
	}
	private long grossAmount;
	private long transactionFee;
	private WalletType walletType;
	private long buyerId;
	private long sellerId;
	private TransactionStatus status;
	private Date transactionCreatedOn;
}
