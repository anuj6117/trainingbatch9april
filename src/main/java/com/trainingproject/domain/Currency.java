package com.trainingproject.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.trainingproject.enums.CoinType;

@Entity
@Table(name="Currency")
public class Currency {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer coinId;
    
	private String coinName;
	 @Enumerated(EnumType.STRING)
	private CoinType coinType;
	private String symbol;
	private double initialSupply;
	private double price; 
	private double fee; 
	private double profit; 
	private double coinInINR; 
	
	public Currency () {
		coinInINR=0;
		profit=0;
		fee=0;
	}
	
	public CoinType getCoinType() {
		return coinType;
	}

	public void setCoinType(CoinType coinType) {
		this.coinType = coinType;
	}

	public Integer getCoinId() {
		return coinId;
	}
	public void setCoinId(Integer coinId) {
		this.coinId = coinId;
	}
	public double getInitialSupply() {
		return initialSupply;
	}
	public void setInitialSupply(double initialsupply) {
		this.initialSupply = initialsupply;
	}
	public double getFee() {
		return fee;
	}
	public void setFee(double fee) {
		this.fee = fee;
	}
	public double getProfit() {
		return profit;
	}
	public void setProfit(double profit) {
		this.profit = profit;
	}
	public double getCoinInINR() {
		return coinInINR;
	}
	public void setCoinInINR(double coinInINR) {
		this.coinInINR = coinInINR;
	}
	
	public double getSupply() {
		return initialSupply;
	}
	public void setSupply(double supply) {
		this.initialSupply = supply;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
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
	
}
