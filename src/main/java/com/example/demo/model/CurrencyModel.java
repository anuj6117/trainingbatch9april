package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="Currency")
public class CurrencyModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer coinId;
	
	@Column(name="coinName", unique=true, nullable=false)
	private String coinName;
	
	@Column(name="symbol", unique=true , nullable=false)
	private String symbol;
	
	@NotNull
	private Integer initialSupply;
	
	@NotNull
	private Integer price;	
	
	@Column(name="CoinInINR", nullable=true)
	private Integer coinInINR;
	
	private Integer fee;
	private Integer profit;
	
	public Integer getCoinId() {
		return coinId;
	}
	
	public void setCoinId(Integer coinId) {
		this.coinId = coinId;
	}		
	
	public Integer getFee() {
		return fee;
	}
	
	public void setFee(Integer fee) {
		this.fee = fee;
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
