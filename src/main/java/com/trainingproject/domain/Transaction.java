package com.trainingproject.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.trainingproject.enums.TransactionStatus;
import com.trainingproject.enums.CoinType;

@Entity
@Table(name="transaction")
public class Transaction {

	@Id
	@GeneratedValue
	private Integer transactionId;
	private long amount;
	 @Enumerated(EnumType.STRING)
	private CoinType coinType;
	private String coinName;
	private long fee;
	private long exchangeRate;
	private long grossAmount;
	private Integer buyer;
	private Integer seller;
	 @Enumerated(EnumType.STRING)
	private TransactionStatus status;
	private Date date;
	private String remarks;
	
	
	
	public Integer getTransactionId() {
		return transactionId;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	public CoinType getCoinType() {
		return coinType;
	}
	public void setCoinType(CoinType coinType) {
		this.coinType = coinType;
	}
	public String getCoinName() {
		return coinName;
	}
	public void setCoinName(String coinName) {
		this.coinName = coinName;
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
	public Integer getBuyer() {
		return buyer;
	}
	public Integer getSeller() {
		return seller;
	}
	public void setSeller(Integer seller) {
		this.seller = seller;
	}
	public void setBuyer(Integer buyer) {
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
