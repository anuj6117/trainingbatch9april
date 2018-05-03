package com.trading.domain;

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

	private double transactionFee;

	private double netAmount;

	private double exchangeRate;

	private long buyerId;

	public double getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	private long sellerId;

	private double grossAmount;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private String transactionCreatedOn;

	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	public double getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(double netAmount) {
		this.netAmount = netAmount;
	}

	public double getGrossAmount() {
		return grossAmount;
	}

	public void setGrossAmount(double grossAmount) {
		this.grossAmount = grossAmount;
	}

	public double getTransactionFee() {
		return transactionFee;
	}

	public void setTransactionFee(double transactionFee) {
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
	public Integer CoinQuantiy;

	public Integer getCoinQuantiy() {
		return CoinQuantiy;
	}

	public void setCoinQuantiy(Integer coinQuantiy) {
		CoinQuantiy = coinQuantiy;
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

	public String getTransactionCreatedOn() {
		return transactionCreatedOn;
	}

	public void setTransactionCreatedOn(String transactionCreatedOn) {
		this.transactionCreatedOn = transactionCreatedOn;
	}

}
