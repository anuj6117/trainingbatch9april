package com.trading.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.trading.Enum.WalletType;

@Entity
public class Currency {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long coinId;

	@Column(unique = true)
	@NotNull
	private String coinName;
	
	@Enumerated(EnumType.STRING)
	private WalletType coinType;
	
	private long INR;
	
	
	public WalletType getCoinType() {
		return coinType;
	}

	public void setCoinType(WalletType coinType) {
		this.coinType = coinType;
	}

	public long getINR() {
		return INR;
	}

	public void setINR(long iNR) {
		INR = iNR;
	}

	@Column(unique = true)
	@NotNull
	private String symbol;

	@NotNull
	private Integer initialSupply;

	@NotNull
	private Integer price;
	
	private long fee;
	
	private long coinINR;

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

	public long getInitialSupply() {
		return initialSupply;
	}

	public void setInitialSupply(Integer initialSupply) {
		this.initialSupply = initialSupply;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

}
