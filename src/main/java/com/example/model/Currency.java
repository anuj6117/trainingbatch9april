package com.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.example.enums.WalletType;

@Entity
@Table(name="currency")
public class Currency
{
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer coinId;

  private String coinName;
 
  @Enumerated(EnumType.STRING)
  private WalletType coinType;
  
  private String symbol;
 
  private Integer initialSupply;

  private Integer price;
  private Integer fees;
  private Integer profit;
  private Integer coinInINR;
  
  public void setCoinId(Integer coinId) {
	this.coinId = coinId;
}
public WalletType getCoinType() {
	return coinType;
}
public void setCoinType(WalletType coinType) {
	this.coinType = coinType;
}
public Integer getFees() {
	return fees;
}
public void setFees(Integer fees) {
	this.fees = fees;
}
public Integer getProfit() {
	return profit;
}
public void setProfit(Integer profit) {
	this.profit = profit;
}
public Integer getCoinInINR() {
	return coinInINR;
}
public void setCoinInINR(Integer coinInINR) {
	this.coinInINR = coinInINR;
}
public Integer getCoinId() {
	return coinId;
}
public void setCurrencyId(Integer coinId) {
	this.coinId = coinId;
}


public String getCoinName() {
	return coinName;
}
public void setCoinName(String coinName) {
	this.coinName = coinName;
}
public String getSymbol() {
	return symbol;
}
public void setSymbol(String symbol) {
	this.symbol = symbol;
}
public Integer getInitialSupply() {
	return initialSupply;
}
public void setInitialSupply(Integer initialSupply) {
	this.initialSupply = initialSupply;
}
public Integer getPrice() {
	return price;
}
public void setPrice(Integer price) {
	this.price = price;
}
 	
}
