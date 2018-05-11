package com.trainingproject.dto;

import com.trainingproject.enums.CoinType;

public class UserOrderBuySellDto {
	private Integer userId;
	private Double coinQuantity;
	private Double price;
	private String coinName;
	private CoinType coinType;
	
	
	public CoinType getCoinType() {
		return coinType;
	}
	public void setCoinType(CoinType coinType) {
		this.coinType = coinType;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public Double getCoinQuantity() {
		return coinQuantity;
	}
	public void setCoinQuantity(Double coinQuantity) {
		this.coinQuantity = coinQuantity;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getCoinName() {
		return coinName;
	}
	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}

}
