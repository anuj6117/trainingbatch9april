package com.trading.dto;

public class UserOrderDto {

	private long userId;
	private Integer coinQuantity;
	private Integer price;
	private String coinName;
	

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
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
