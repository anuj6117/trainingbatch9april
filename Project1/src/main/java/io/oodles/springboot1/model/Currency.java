package io.oodles.springboot1.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.oodles.springboot1.enums.WalletType;

@Entity
public class Currency {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer coinId;
	
	@Enumerated(EnumType.STRING)
	private WalletType coinType;
	private String coinName;
	
	private String symbol;
	
	private Double initialSupply=0.0;
	
	private Double price=0.0;
	private Double fees=0.0;
	private Double coinInINR=0.0;
	
	
	private Double profit=0.0;
	
	
	
	
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

	public WalletType getCoinType() {
		return coinType;
	}

	public void setCoinType(WalletType coinType) {
		this.coinType = coinType;
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

	public Double getFees() {
		return fees;
	}

	public void setFees(Double fees) {
		this.fees = fees;
	}
	
	
	
	
	

}
