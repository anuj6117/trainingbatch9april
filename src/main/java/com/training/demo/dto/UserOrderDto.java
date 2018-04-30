package com.training.demo.dto;

import com.training.demo.enums.WalletType;

public class UserOrderDto {

	private Integer userId;
	private long coinQuantity;
	private long price;
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

	public long getCoinQuantity() {
		return coinQuantity;
	}

	public void setCoinQuantity(long coinQuantity) {
		this.coinQuantity = coinQuantity;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

}