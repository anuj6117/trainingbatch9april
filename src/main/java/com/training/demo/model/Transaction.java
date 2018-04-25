package com.training.demo.model;

import java.util.Date;
import com.training.demo.enums.OrderStatus;
import com.training.demo.enums.WalletType;

public class Transaction {

	private Integer id;
	private WalletType walletType;
	private OrderStatus orderStatus;
	private Double fees;
	private Double netAmount;
	private Double exchangeRate;
	private Integer buyerId;
	private Integer sellerId;
	private Double grossAmount;
	private Date transactionCreatedOn;
	
	public Transaction() {
		super();
	}
	
	public Transaction(Integer id, WalletType walletType, OrderStatus orderStatus, Double fees, Double netAmount,
			Double exchangeRate, Integer buyerId, Integer sellerId, Double grossAmount, Date transactionCreatedOn) {
		super();
		this.id = id;
		this.walletType = walletType;
		this.orderStatus = orderStatus;
		this.fees = fees;
		this.netAmount = netAmount;
		this.exchangeRate = exchangeRate;
		this.buyerId = buyerId;
		this.sellerId = sellerId;
		this.grossAmount = grossAmount;
		this.transactionCreatedOn = transactionCreatedOn;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public WalletType getWalletType() {
		return walletType;
	}
	public void setWalletType(WalletType walletType) {
		this.walletType = walletType;
	}
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
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
	public Double getGrossAmount() {
		return grossAmount;
	}
	public void setGrossAmount(Double grossAmount) {
		this.grossAmount = grossAmount;
	}
	public Date getTransactionCreatedOn() {
		return transactionCreatedOn;
	}
	public void setTransactionCreatedOn(Date transactionCreatedOn) {
		this.transactionCreatedOn = transactionCreatedOn;
	}
	
}
