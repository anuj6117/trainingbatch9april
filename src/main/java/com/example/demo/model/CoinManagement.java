package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="coinmanagement")
public class CoinManagement {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer coinId;
	private String coinName;
	private String symbol;
	private Double initialSupply;
	private Integer price;
	private Double coinInInr;
	private Double profit;
	private Double fee;
	private Double exchangeRate;
	
	
	

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

	public Double getInitialSupply() {
		return initialSupply;
	}

	public void setInitialSupply(Double initialSupply) {
		this.initialSupply = initialSupply;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Double getCoinInInr() {
		return coinInInr;
	}

	public void setCoinInInr(Double coinInInr) {
		this.coinInInr = coinInInr;
	}

	public Double getProfit() {
		return profit;
	}

	public void setProfit(Double profit) {
		this.profit = profit;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public Double getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(Double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
}