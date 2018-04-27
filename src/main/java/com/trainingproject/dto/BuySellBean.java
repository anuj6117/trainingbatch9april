package com.trainingproject.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.trainingproject.enums.CoinType;

public class BuySellBean {

	private Integer userId;
	private Integer coinQuantity;
	private Integer price;
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
	public Integer getCoinQuantity() {
		return coinQuantity;
	}
	public void setCoinQuantity(Integer coinQuantity) {
		this.coinQuantity = coinQuantity;
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
	
}
