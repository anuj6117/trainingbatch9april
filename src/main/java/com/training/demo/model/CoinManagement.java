package com.training.demo.model;

import javax.persistence.Column;
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
	private long initialSupply;
	private Long profit;

	public long getInitialSupply() {
		return initialSupply;
	}

	public void setInitialSupply(long initialSupply) {
		this.initialSupply = initialSupply;
	}

	@NotNull(message = "price not null")
	private Integer price;
	private long fee;

	private Integer INRconversion;

	public Integer getINRconversion() {
		return INRconversion;
	}

	public void setINRconversion(Integer iNRconversion) {
		INRconversion = iNRconversion;
	}

	public void setCoinId(Integer coinId) {
		this.coinId = coinId;
	}

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

	public long getFee() {
		return fee;
	}

	public void setFee(long fee) {
		this.fee = fee;
	}

	public Integer getCoinId() {
		return coinId;
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

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}
}