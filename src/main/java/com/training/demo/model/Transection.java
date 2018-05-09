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

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long transactionId;
	private double netAmount;
	@Enumerated(EnumType.STRING)
	private WalletType walletType;
	private double fee;
	private String coinName;
	private double exchangeRate;
	private double grossAmount;
	private Integer buyer;
	private Integer seller;
	private String message;
	@Enumerated(EnumType.STRING)
	private UserOrderStatus transactionStatus;

	

	
	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	public UserOrderStatus getTransactionStatus() {
		return transactionStatus;
	}

	

	public String getCoinName() {
		return coinName;
	}

	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}

	public long getTransactionId() {
		return transactionId;
	}



	

	public void setBuyer(Integer buyer) {
		this.buyer = buyer;
	}

	public void setSeller(Integer seller) {
		this.seller = seller;
	}

	

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



	public WalletType getWalletType() {
		return walletType;
	}

	public void setWalletType(WalletType walletType) {
		this.walletType = walletType;
	}

	

	public double getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(double netAmount) {
		this.netAmount = netAmount;
	}

	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

	public double getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public double getGrossAmount() {
		return grossAmount;
	}

	public void setGrossAmount(double grossAmount) {
		this.grossAmount = grossAmount;
	}

	public Integer getBuyer() {
		return buyer;
	}

	public Integer getSeller() {
		return seller;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

}
