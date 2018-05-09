package com.training.demo.dto;

import com.training.demo.enums.WalletType;

public class UserOrderDto {

	private Integer userId;
	private double coinQuantity;
	private double price;
	private String coinName;
	private WalletType coinType;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getCoinName() {
		return coinName;
	}

	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}

	public WalletType getCoinType() {
		return coinType;
	}

	public void setCoinType(WalletType coinType) {
		this.coinType = coinType;
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

	

}