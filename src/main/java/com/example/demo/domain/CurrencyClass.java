package com.example.demo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="CurrencyClass")
public class CurrencyClass {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer coinId;
	
	@NotNull(message="Coin Name Should Not Accept null")
	private String coinName;
	@NotNull(message="symbol Should Not Accept null")
	private String symbol;
	@NotNull(message="symbol Should Not Accept null")
	private Integer initialSupply;
	@NotNull(message="symbol Should Not Accept null")
	private Integer price;
	
	private Double fees;
	private Double profit;
	private Double coinInINR;
	public CurrencyClass()
	{
		fees=0.0;
		profit=0.0;
		coinInINR=0.0;
	}
	public Integer getCoinId() {
		return coinId;
	}
	public void setCoinId(Integer coinId) {
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
	
	public double getFees() {
		return fees;
	}
	public void setFees(double fees) {
		this.fees = fees;
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
	
}
