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
	private Integer transactionId;
	private long netAmount;
	@Enumerated(EnumType.STRING)
	private WalletType walletType;
	private Long fee;
	public Long getFee() {
		return fee;
	}
	public void setFee(Long fee) {
		this.fee = fee;
	}
	private Long exchangeRate;
	private Long grossAmount;
	private String buyer;
	private String seller;
	private String message;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Enumerated(EnumType.STRING)
	private UserOrderStatus transactionStatus;
	public void setTransactionStatus(UserOrderStatus transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	private Date createdOn;
	public Integer getTransactionId() {
		return transactionId;
	}
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
	
	
	public Long getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(Long exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	public Long getGrossAmount() {
		return grossAmount;
	}
	public void setGrossAmount(Long grossAmount) {
		this.grossAmount = grossAmount;
	}
	public String getBuyer() {
		return buyer;
	}
	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}
	public String getSeller() {
		return seller;
	}
	public void setSeller(String seller) {
		this.seller = seller;
	}
	
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	

}
