package io.oodles.springboot1.model;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import io.oodles.springboot1.enums.WalletType;

public class Deposit {
	Integer userId;
	@Enumerated(EnumType.STRING)
	WalletType coinType;
	String coinName;
	Double amount=0.0;
	
	
	
	
	public String getCoinName() {
		return coinName;
	}
	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}
	
	
	
	
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public WalletType getCoinType() {
		return coinType;
	}
	public void setCoinType(WalletType coinType) {
		this.coinType = coinType;
	}
	
	
	

}
