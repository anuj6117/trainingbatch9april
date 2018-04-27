package io.oodles.springboot1.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.oodles.springboot1.enums.WalletType;

@Entity
public class Currency {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@Enumerated(EnumType.STRING)
	private WalletType coinType;
	private String coinname;
	private String symbol;
	private Integer initialSupply;
	private Integer price;
	private Integer fee;
	private Integer INRconversion;
	private Integer profit;
	
	
	
	
	public Integer getProfit() {
		return profit;
	}

	public void setProfit(Integer profit) {
		this.profit = profit;
	}

	public Integer getFee() {
		return fee;
	}

	public void setFee(Integer fee) {
		this.fee = fee;
	}

	public Integer getINRconversion() {
		return INRconversion;
	}

	public void setINRconversion(Integer iNRconversion) {
		INRconversion = iNRconversion;
	}

	public String getCoinname() {
		return coinname;
	}
	
	public WalletType getCoinType() {
		return coinType;
	}

	public void setCoinType(WalletType coinType) {
		this.coinType = coinType;
	}

	public void setCoinname(String coinname) {
		this.coinname = coinname;
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
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	
	

}
