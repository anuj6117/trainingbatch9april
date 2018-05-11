package com.traningproject1.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.traningproject1.enumsclass.CoinType;

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
	private Double initialSupply;
	@NotNull(message="symbol Should Not Accept null")
	private Double  price;
	
	private Double fees;
	private Double profit;
	private Double coinInINR;
	@Enumerated(value=EnumType.STRING)
	private CoinType coinType;
	public CoinType getCoinType() {
		return coinType;
	}
	public void setCoinType(CoinType coinType) {
		this.coinType = coinType;
	}
	public void setFees(Double fees) {
		this.fees = fees;
	}
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
	public void setProfit(Double profit) {
		this.profit = profit;
	}
	public void setCoinInINR(Double coinInINR) {
		this.coinInINR = coinInINR;
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
