package com.training.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.training.demo.enums.WalletType;

@Entity
public class CoinManagement {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long coinId;
	private WalletType coinType;
	//@NotNull(message = "coinName not null")
	private String coinName;
	@Column(unique = true)
	@NotNull(message = "symbol not null")
	private String symbol;
	//@NotNull(message = "can not be null")
	private Double initialSupply;
	//@NotNull(message = "price not null")
	private Integer price;
	private Integer fee;
	private Long INRconversion;
	private Long profit;

	public Long getProfit() {
		return profit;
	}

	public void setProfit(Long profit) {
		this.profit = profit;
	}

	public WalletType getCoinType() {
		return coinType;
	}

	public void setCoinType(WalletType coinType) {
		this.coinType = coinType;
	}

	public Integer getFee() {
		return fee;
	}

	public void setFee(Integer fee) {
		this.fee = fee;
	}

	public Long getINRconversion() {
		return INRconversion;
	}

	public void setINRconversion(Long iNRconversion) {
		INRconversion = iNRconversion;
	}

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