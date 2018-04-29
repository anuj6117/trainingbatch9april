package com.training.demo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.training.demo.enums.OrderStatus;
import com.training.demo.enums.WalletType;

@Entity
@Table(name = "transaction")
public class Transaction {

	@Id
	private Integer transactionId;	
	
	private Double coinQuantity;
	@Enumerated(EnumType.STRING)
	private WalletType coinType;
	private String coinName;
	@Enumerated(EnumType.STRING)
	private OrderStatus transactionStatus;
	private Double fees;
	private Double netAmount;
	private Double grossAmount;
	private Double exchangeRate;
	private Integer buyerId;
	private Integer sellerId;
	private Date transactionCreatedOn;
	private String description;
	
	public Transaction() {
		super();
	}
	
	public Transaction(Integer transactionId, Double coinQuantity, WalletType coinType, String coinName,
			OrderStatus transactionStatus, Double fees, Double netAmount, Double grossAmount, Double exchangeRate,
			Integer buyerId, Integer sellerId, Date transactionCreatedOn, String description) {
		super();
		this.transactionId = transactionId;
		this.coinQuantity = coinQuantity;
		this.coinType = coinType;
		this.coinName = coinName;
		this.transactionStatus = transactionStatus;
		this.fees = fees;
		this.netAmount = netAmount;
		this.grossAmount = grossAmount;
		this.exchangeRate = exchangeRate;
		this.buyerId = buyerId;
		this.sellerId = sellerId;
		this.transactionCreatedOn = transactionCreatedOn;
		this.description = description;
	}

	public Integer getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}
	public Double getCoinQuantity() {
		return coinQuantity;
	}
	public void setCoinQuantity(Double coinQuantity) {
		this.coinQuantity = coinQuantity;
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
	public OrderStatus getTransactionStatus() {
		return transactionStatus;
	}
	public void setTransactionStatus(OrderStatus transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	public Double getFees() {
		return fees;
	}
	public void setFees(Double fees) {
		this.fees = fees;
	}
	public Double getNetAmount() {
		return netAmount;
	}
	public void setNetAmount(Double netAmount) {
		this.netAmount = netAmount;
	}
	public Double getGrossAmount() {
		return grossAmount;
	}
	public void setGrossAmount(Double grossAmount) {
		this.grossAmount = grossAmount;
	}
	public Double getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(Double exchangeRate) {
		this.exchangeRate = exchangeRate;
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
	public Date getTransactionCreatedOn() {
		return transactionCreatedOn;
	}
	public void setTransactionCreatedOn(Date transactionCreatedOn) {
		this.transactionCreatedOn = transactionCreatedOn;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}	
}