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

import com.trainingproject.enums.WalletType;

@Entity
@Table(name="currency")
public class Currency {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Integer coinId;
	@Enumerated(EnumType.STRING)
	private  WalletType coinType;
	
	@Column(unique = true)
	@NotNull(message = "coinName not null")
	private String coinName;
	
	@Column(unique = true)
	@NotNull(message = "symbol not null")
	private String symbol;
	
	@NotNull(message = "initialSupply not null")
	private Long initialSupply;
	
	@NotNull(message = "price not null")
	private Long price;
	private Long fees;
	private Long profit;
	private Long coinInINR;
	
	private Currency() {
		fees = 0l;
		profit = 0l;
		coinInINR = 0l;
	}
	
	public WalletType getCoinType() {
		return coinType;
	}

	public void setCoinType(WalletType coinType) {
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
