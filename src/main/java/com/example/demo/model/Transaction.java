package com.example.demo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.demo.enums.CoinType;
import com.example.demo.enums.OrderStatus;
import com.example.demo.enums.OrderType;


@Entity
@Table(name="Transaction")
public class Transaction {
	
	@Id
	private Integer transactionId;	
	
	@Column(unique=true)
	private Double coinQuantity;
	
	@Enumerated(EnumType.STRING)
	private CoinType coinType;	
	
	@Enumerated(EnumType.STRING)
	private OrderStatus transactionStatus;
	
	@Enumerated(EnumType.STRING)
	private OrderType transactionType;	

	private String coinName;
	private Integer fees;
	private Double netAmount;
	private Double grossAmount;
	private Double exchangeRate;
	private Integer buyerId;
	private Integer sellerId;
	private Date transactionCreatedOn;
	private String description;
	
	public Transaction() {}
	
	
	public Transaction(Integer transactionId, Double coinQuantity, CoinType coinType, OrderStatus transactionStatus,
			OrderType transactionType, String coinName, Integer fees, Double netAmount, Double grossAmount,
			Double exchangeRate, Integer buyerId, Integer sellerId, Date transactionCreatedOn, String description) {
		super();
		this.transactionId = transactionId;
		this.coinQuantity = coinQuantity;
		this.coinType = coinType;
		this.transactionStatus = transactionStatus;
		this.transactionType = transactionType;
		this.coinName = coinName;
		this.fees = fees;
		this.netAmount = netAmount;
		this.grossAmount = grossAmount;
		this.exchangeRate = exchangeRate;
		this.buyerId = buyerId;
		this.sellerId = sellerId;
		this.transactionCreatedOn = transactionCreatedOn;
		this.description = description;
	}
	
	public Transaction(OrderDetails orderDetails) {
		this.transactionId = orderDetails.getOrderId();
		this.coinName = orderDetails.getCoinName();
		this.coinType = orderDetails.getCoinType();
		this.transactionStatus = orderDetails.getOrderStatus();
		this.transactionType = orderDetails.getOrderType();
		this.grossAmount = orderDetails.getPrice();
		this.fees = orderDetails.getFee();
		this.netAmount = orderDetails.getPrice();
		this.grossAmount = orderDetails.getPrice();
		this.exchangeRate = orderDetails.getExchangeRate();
		this.buyerId = orderDetails.getUser().getUserId();
		this.transactionCreatedOn = new Date();		
	}

	public OrderType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(OrderType transactionType) {
		this.transactionType = transactionType;
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
	public OrderStatus getTransactionStatus() {
		return transactionStatus;
	}
	
	public void setTransactionStatus(OrderStatus transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	
	public Integer getFees() {
		return fees;
	}
	
	public void setFees(Integer fees) {
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
