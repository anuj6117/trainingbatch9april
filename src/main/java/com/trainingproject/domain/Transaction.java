package com.trainingproject.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.trainingproject.enums.CoinType;
import com.trainingproject.enums.Status;

@Entity
@Table(name = "transaction")
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer transactionId;
	private Integer netAmount;
	@Enumerated(EnumType.STRING)
	private CoinType coinType;
	private String coinName;
	private Integer fees;
	private Integer exchangeRate;
	private Integer grossAmount;
	private Integer buyerId;
	private Integer sellerId;
	@Enumerated(EnumType.STRING)
	private Status status;
	private Date createdOn;
	private String message;
	
	public String getCoinName() {
		return coinName;
	}
	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}
	public CoinType getCoinType() {
		return coinType;
	}
	public void setCoinType(CoinType coinType) {
		this.coinType = coinType;
	}
	public Integer getFees() {
		return fees;
	}
	public void setFees(Integer fees) {
		this.fees = fees;
	}
	public Integer getNetAmount() {
		return netAmount;
	}
	public void setNetAmount(Integer netAmount) {
		this.netAmount = netAmount;
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
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	

}
