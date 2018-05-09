package com.training.demo.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.training.demo.enums.WalletType;

@Entity
public class CoinManagement {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer coinId;

	@Enumerated(EnumType.STRING)
	private WalletType coinType;
	// @NotNull(message = "coinName not null")
	private String coinName;
	// @Column(unique = true)
	// @NotNull(message = "symbol not null")
	private String symbol;
	@NotNull(message = "can not be null")
	private Double initialSupply;
	private Double profit;
	private Double INRconversion=0d;

	public CoinManagement() {
		profit = 0.0;
		fee = 0.0;
	}

	//@NotNull(message = "price not null")
	private Double price;
	private Double fee;

	public Integer getCoinId() {
		return coinId;
	}
	public void setCoinId(Integer coinId) {
		this.coinId = coinId;
	}
	public WalletType getCoinType() {
		return coinType;
	}
	public void setCoinType(WalletType coinType) {
		this.coinType = coinType;
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
	public Double getProfit() {
		return profit;
	}
	public void setProfit(Double profit) {
		this.profit = profit;
	}
	
	public Double getINRconversion() {
		return INRconversion;
	}
	public void setINRconversion(Double iNRconversion) {
		INRconversion = iNRconversion;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getFee() {
		return fee;
	}
	public void setFee(Double fee) {
		this.fee = fee;
	}

}