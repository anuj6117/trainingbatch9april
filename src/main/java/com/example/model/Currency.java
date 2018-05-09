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
 
  private Double initialSupply;

  private Double price;
  private Double fees;
  private Double profit;
  private Double coinInINR;
  
  public void setCoinId(Integer coinId) {
	this.coinId = coinId;
}
  public Integer getCoinId() {
		return coinId;
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
public String getSymbol() {
	return symbol;
}
public void setSymbol(String symbol) {
	this.symbol = symbol;
}
public Double getInitialSupply() {
	return initialSupply;
}
public void setInitialSupply(Double initialSupply) {
	this.initialSupply = initialSupply;
}
public Double getPrice() {
	return price;
}
public void setPrice(Double price) {
	this.price = price;
}
public Double getFees() {
	return fees;
}
public void setFees(Double fees) {
	this.fees = fees;
}
public Double getProfit() {
	return profit;
}
public void setProfit(Double profit) {
	this.profit = profit;
}
public Double getCoinInINR() {
	return coinInINR;
}
public void setCoinInINR(Double coinInINR) {
	this.coinInINR = coinInINR;
}




 	
}
