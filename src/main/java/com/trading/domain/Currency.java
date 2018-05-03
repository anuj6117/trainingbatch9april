package com.trading.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.trading.Enum.WalletType;

@Entity
public class Currency {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long coinId;

	@Column(unique = true)
	private String coinName;
	
	@Enumerated(EnumType.STRING)
	private WalletType coinType;
	
	
	
	public WalletType getCoinType() {
		return coinType;
	}

	public void setCoinType(WalletType coinType) {
		this.coinType = coinType;
	}

	

	@Column(unique = true)
	
	private String symbol;

	
	private Integer initialSupply;

	
	private double price;
	
	private double fee;
	
	private double coinINR;
	
	private double profit;

	public double getProfit() {
		return profit;
	}

	public void setProfit(double profit) {
		this.profit = profit;
	}

	public double getCoinINR() {
		return coinINR;
	}

	public void setCoinINR(double coinINR) {
		this.coinINR = coinINR;
	}

	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

	public long getCoinId() {
		return coinId;
	}

	public void setCoinId(long coinId) {
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
