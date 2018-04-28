package com.trainingproject.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.trainingproject.enums.CoinType;

@Entity
@Table(name="currency")
public class Currency {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Integer coinId;
	@Enumerated(EnumType.STRING)
	private  CoinType coinType;
	
	@Column(unique = true)
	@NotNull(message = "coinName not null")
	private String coinName;
	
	@Column(unique = true)
	@NotNull(message = "symbol not null")
	private String symbol;
	
	@NotNull(message = "initialSupply not null")
	private Integer initialSupply;
	
	@NotNull(message = "price not null")
	private Integer price;
	private Integer fees;
	private Integer profit;
	private Long coinInINR;
	
	private Currency() {
		fees = 0;
		profit = 0;
		coinInINR = 0l;
	}
	
	public CoinType getCoinType() {
		return coinType;
	}

	public void setCoinType(CoinType coinType) {
		this.coinType = coinType;
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
	public Integer getFees() {
		return fees;
	}

	public void setFees(Integer fees) {
		this.fees = fees;
	}

	public Integer getProfit() {
		return profit;
	}

	public void setProfit(Integer profit) {
		this.profit = profit;
	}

	public Long getCoinInINR() {
		return coinInINR;
	}
	public void setCoinInINR(Long coinInINR) {
		this.coinInINR = coinInINR;
	}
	
	
}
