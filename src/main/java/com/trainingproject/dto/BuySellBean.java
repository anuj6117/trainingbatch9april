package com.trainingproject.dto;

public class BuySellBean {

	private Integer userId;
	private Integer coinQuantity;
	private long price;
	private String coinName;

	public Integer getUserId() {
		return userId;
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
