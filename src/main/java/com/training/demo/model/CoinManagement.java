package com.training.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.training.demo.enums.WalletType;

@Entity
@Table(name="coinManagement")
public class CoinManagement {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer coinId;
	private String coinName;
	private WalletType coinType;
	private String symbol;
	private Double initialSupply;
	//private Double exchangeRate;
	private Double price;
	private Double fees;
	private Double profit;
	private Double coinInINR;
	
	public CoinManagement() {
		super();
	}
	
	public CoinManagement(Integer coinId, String coinName, String symbol, Double initialSupply, Double price, Double fees, Double profit, Double coinInINR, String coinType) {
		super();
		this.coinId = coinId;
		this.coinName = coinName;
		this.symbol = symbol;
		this.initialSupply = initialSupply;
		this.price = price;
		this.fees = fees;
		this.profit = profit;
		this.coinInINR = coinInINR;
		this.coinType = WalletType.valueOf(coinType);
	}

	public Double getFees() {
		return fees;
	}
	
	public void setFees(Double fees) {
		this.fees = fees;
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

	public Double getCoinInINR() {
		return coinInINR;
	}

	public void setCoinInINR(Double coinInINR) {
		this.coinInINR = coinInINR;
	}

	public Double getProfit() {
		return profit;
	}

	public void setProfit(Double profit) {
		this.profit = profit;
	}

	public WalletType getCoinType() {
		return coinType;
	}

	public void setCoinType(WalletType coinType) {
		this.coinType = coinType;
	}

}