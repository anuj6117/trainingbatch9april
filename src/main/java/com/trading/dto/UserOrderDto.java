package com.trading.dto;

public class UserOrderDto {

	private long userId;
	private long coinQuantity;
	private long price;
	private String coinName;
	private long fee;
	

	public long getFee() {
		return fee;
	}

	public void setFee(long fee) {
		this.fee = fee;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
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

	public String getCoinName() {
		return coinName;
	}

	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}

}
