package io.oodles.springboot1.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.oodles.springboot1.enums.OrderStatus;
import io.oodles.springboot1.enums.WalletType;

@Entity
public class UserTransaction {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	Integer transactionId;
	Double coinQuantity=0.0;
	Double netAmount=0.0;
	WalletType coinType;
	String coinName;
	Double transactionfee=0.0;
	Double exchangeRate=0.0;
	Double grossAmount=0.0;
	Integer buyerId;
	Integer sellerId;
	@Enumerated(EnumType.STRING)
	OrderStatus transactionstatus;
	Date transactionCreatedOn;
	String description;
	
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public Double getNetAmount() {
		return netAmount;
	}
	public void setNetAmount(Double netAmount) {
		this.netAmount = netAmount;
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

	public OrderStatus getTransactionstatus() {
		return transactionstatus;
	}
	public void setTransactionstatus(OrderStatus transactionstatus) {
		this.transactionstatus = transactionstatus;
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
	public Double getTransactionfee() {
		return transactionfee;
	}
	public void setTransactionfee(Double transactionfee) {
		this.transactionfee = transactionfee;
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
	
	
	
	
	

}
