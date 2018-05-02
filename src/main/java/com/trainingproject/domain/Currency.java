package com.trainingproject.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.trainingproject.enums.CoinType;

@Entity
@Table(name="Currency")
public class Currency {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer coinId;
    
	private String coinName;
	private CoinType coinType;
	private String symbol;
	private Integer initialSupply;
	private Integer price; 
	private long fee; 
	private Integer profit; 
	private Integer coinInINR; 
	
	public Currency () {
		coinInINR=0;
		profit=0;
		fee=0;
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
	public Integer getInitialSupply() {
		return initialSupply;
	}
	public void setInitialSupply(Integer initialsupply) {
		this.initialSupply = initialsupply;
	}
	public long getFee() {
		return fee;
	}
	public void setFee(long fee) {
		this.fee = fee;
	}
	public Integer getProfit() {
		return profit;
	}
	public void setProfit(Integer profit) {
		this.profit = profit;
	}
	public Integer getCoinInINR() {
		return coinInINR;
	}
	public void setCoinInINR(Integer coinInINR) {
		this.coinInINR = coinInINR;
	}
	public Integer getId(){
		return coinId;
	}
	public void setId(Integer id) {
		this.coinId = id;
	}
	public Integer getSupply() {
		return initialSupply;
	}
	public void setSupply(Integer supply) {
		this.initialSupply = supply;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
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
	
}
