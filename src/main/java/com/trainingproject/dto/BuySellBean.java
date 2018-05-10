package com.trainingproject.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.trainingproject.enums.CoinType;

public class BuySellBean {

	private Integer userId;
	private double coinQuantity;
	private double price;
	private String coinName;
	 @Enumerated(EnumType.STRING)
	private CoinType coinType;

	public Integer getUserId() {
		return userId;
	}
	public CoinType getCoinType() {
		return coinType;
	}
	public void setCoinType(CoinType coinType) {
		this.coinType = coinType;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public double getCoinQuantity() {
		return coinQuantity;
	}
	public void setCoinQuantity(double coinQuantity) {
		this.coinQuantity = coinQuantity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getCoinName() {
		return coinName;
	}
	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}
	
}
