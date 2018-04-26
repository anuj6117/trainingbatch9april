package com.training.demo.dto;

public class SellBuyOrderDto {
	
	private Integer userId;
	private Double coinQuantity;
	private Double price;
	private String coinName;
	private Integer fee;
	
	public SellBuyOrderDto() {
		super();
	}

	public SellBuyOrderDto(Integer userId, Double coinQuantity, Double price, String coinName, Integer fee) {
		super();
		this.userId = userId;
		this.coinQuantity = coinQuantity;
		this.price = price;
		this.coinName = coinName;
		this.fee = fee;
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

	public Integer getFee() {
		return fee;
	}

	public void setFee(Integer fee) {
		this.fee = fee;
	}
	
	
}
