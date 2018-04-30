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

	
	private Integer price;
	
	private long fee;
	
	private long coinINR;
	
	private long profit;

	public long getProfit() {
		return profit;
	}

	public void setProfit(long profit) {
		this.profit = profit;
	}

	public long getCoinINR() {
		return coinINR;
	}

	public void setCoinINR(long coinINR) {
		this.coinINR = coinINR;
	}

	public long getFee() {
		return fee;
	}

	public void setFee(long fee) {
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

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

}
