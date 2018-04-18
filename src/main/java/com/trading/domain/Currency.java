package com.trading.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Currency {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long coinId;
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
	public long getInitialSupply() {
		return initialSupply;
	}
	public void setInitialSupply(long initialSupply) {
		this.initialSupply = initialSupply;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	@NotNull
	private String coinName;
	@NotNull
	private String symbol;
	@NotNull
	private long initialSupply;
	@NotNull
	private long price;
	
}
	
