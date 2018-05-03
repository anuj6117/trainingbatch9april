package com.training.demo.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.training.demo.enums.UserOrderStatus;
import com.training.demo.enums.WalletType;

@Entity
@Table(name = "transaction")
public class Transection {
	public long getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(long exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	public UserOrderStatus getTransactionStatus() {
		return transactionStatus;
	}

	public void setExchangeRate(Integer exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long transactionId;
	private long netAmount;
	@Enumerated(EnumType.STRING)
	private WalletType walletType;
	private Long fee;
	private String coinName;

	public String getCoinName() {
		return coinName;
	}

	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}

	public long getTransactionId() {
		return transactionId;
	}

	public Long getFee() {
		return fee;
	}

	public void setFee(Long fee) {
		this.fee = fee;
	}

	private long exchangeRate;
	private Long grossAmount;
	private Integer buyer;

	public void setBuyer(Integer buyer) {
		this.buyer = buyer;
	}

	public void setSeller(Integer seller) {
		this.seller = seller;
	}

	private Integer seller;
	private String message;
	@Enumerated(EnumType.STRING)
	private UserOrderStatus transactionStatus;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setTransactionStatus(UserOrderStatus transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	private Date createdOn;

	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}

	public long getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(long netAmount) {
		this.netAmount = netAmount;
	}

	public WalletType getWalletType() {
		return walletType;
	}

	public void setWalletType(WalletType walletType) {
		this.walletType = walletType;
	}

	public Long getGrossAmount() {
		return grossAmount;
	}

	public void setGrossAmount(Long grossAmount) {
		this.grossAmount = grossAmount;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

}
