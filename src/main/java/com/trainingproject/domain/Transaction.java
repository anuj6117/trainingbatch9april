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
	private double amount;
	 @Enumerated(EnumType.STRING)
	private CoinType coinType;
	private String coinName;
	private double fee;
	private double exchangeRate;
	private double grossAmount;
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
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
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
