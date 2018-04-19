package com.trainingproject.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="currency")
public class Currency {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Integer coinId;
	private String coinName;
	private String symbol;
	private Long initialSupply;
	private Long price;
	private Long fees;
	private Long profit;
	private Long coinInINR;
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
	public Long getInitialSupply() {
		return initialSupply;
	}
	public void setInitialSupply(Long initialSupply) {
		this.initialSupply = initialSupply;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public Long getFees() {
		return fees;
	}
	public void setFees(Long fees) {
		this.fees = fees;
	}
	public Long getProfit() {
		return profit;
	}
	public void setProfit(Long profit) {
		this.profit = profit;
	}
	public Long getCoinInINR() {
		return coinInINR;
	}
	public void setCoinInINR(Long coinInINR) {
		this.coinInINR = coinInINR;
	}
	
	
}
