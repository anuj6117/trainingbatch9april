package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.example.demo.enums.CoinType;

@Entity
@Table(name="Currency")
public class CurrencyModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer coinId;
	
	@Column(name="coinName", unique=true)
	private String coinName;
	
	@Column(name="symbol", unique=true)
	private String symbol;
	
	@NotNull
	private Integer initialSupply;
	
	@Enumerated(EnumType.STRING)
	@Column(name="coinType", unique=true)
	private CoinType coinType;
	
	public CoinType getCoinType() {
		return coinType;
	}

	public void setCoinType(CoinType coinType) {
		this.coinType = coinType;
	}

	@NotNull
	private Double price;	
	
	@Column(name="CoinInINR", nullable=true)
	private Double coinInINR;
	
	private Integer fee;
	private Double profit;
	
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
	
	public Double getPrice() {
		return price;
	}
	
	public void setPrice(Double price) {
		this.price = price;
	}
	
}
