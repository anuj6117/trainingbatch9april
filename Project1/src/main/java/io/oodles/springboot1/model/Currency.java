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
	private Integer initialSupply;
	
	private Integer price;
	private Integer fees;
	private Integer INRconversion;
	
	
	private Integer profit;
	
	
	
	
	public Integer getProfit() {
		return profit;
	}

	public void setProfit(Integer profit) {
		this.profit = profit;
	}

	
	public Integer getINRconversion() {
		return INRconversion;
	}

	public void setINRconversion(Integer iNRconversion) {
		INRconversion = iNRconversion;
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

	public Integer getFees() {
		return fees;
	}

	public void setFees(Integer fees) {
		this.fees = fees;
	}
	
	
	
	
	

}
