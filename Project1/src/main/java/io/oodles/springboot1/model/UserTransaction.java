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
	Double netAmount=0.0;
	WalletType coinType;
	String coinName;
	Double fees=0.0;
	Double exchangeRate=0.0;
	Double grossAmount=0.0;
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
	public Double getFees() {
		return fees;
	}
	public void setFees(Double fees) {
		this.fees = fees;
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
