package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CoinManagement {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long coinId;
	private String coinName;
	private String symbol;
	private Double initialSupply;
	private Integer price;
	
	public Long getCoinId() {
		return coinId;
	}
	public void setCoinId(Long coinId) {
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
	
	

}
