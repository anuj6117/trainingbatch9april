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
	private Double netAmount;
	@Enumerated(EnumType.STRING)
	private CoinType coinType;
	private String coinName;
	private Double fees;
	private Double exchangeRate;
	private Double grossAmount;
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
	
	public Double getNetAmount() {
		return netAmount;
	}
	public void setNetAmount(Double netAmount) {
		this.netAmount = netAmount;
	}
	public Double getFees() {
		return fees;
	}
	public void setFees(Double fees) {
		this.fees = fees;
	}
	public Double getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(Double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	public Double getGrossAmount() {
		return grossAmount;
	}
	public void setGrossAmount(Double grossAmount) {
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
