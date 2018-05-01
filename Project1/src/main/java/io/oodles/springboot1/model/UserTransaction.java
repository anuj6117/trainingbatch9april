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
	Integer id;
	Integer netAmount;
	WalletType coinType;
	String coinName;
	Integer fees;
	Integer exchangeRate;
	Integer grossAmount;
	Integer buyer_id;
	Integer seller_id;
	@Enumerated(EnumType.STRING)
	OrderStatus transactionstatus;
	Date dateCreated;
	String description;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public Integer getNetAmount() {
		return netAmount;
	}
	public void setNetAmount(Integer netAmount) {
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
	public Integer getFees() {
		return fees;
	}
	public void setFees(Integer fees) {
		this.fees = fees;
	}
	public OrderStatus getTransactionstatus() {
		return transactionstatus;
	}
	public void setTransactionstatus(OrderStatus transactionstatus) {
		this.transactionstatus = transactionstatus;
	}
	
	public Integer getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(Integer exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	public Integer getGrossAmount() {
		return grossAmount;
	}
	public void setGrossAmount(Integer grossAmount) {
		this.grossAmount = grossAmount;
	}
	
	
	
	public Integer getBuyer_id() {
		return buyer_id;
	}
	public void setBuyer_id(Integer buyer_id) {
		this.buyer_id = buyer_id;
	}
	public Integer getSeller_id() {
		return seller_id;
	}
	public void setSeller_id(Integer seller_id) {
		this.seller_id = seller_id;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	

}
