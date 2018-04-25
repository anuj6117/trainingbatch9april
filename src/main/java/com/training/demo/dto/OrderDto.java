package com.training.demo.dto;

public class OrderDto {

	private Integer userId;
	private Integer coinQuantity;
	private Double price;
	private String coinName;
	
	public OrderDto() {
		super();
	}

	public OrderDto(Integer userId, Integer coinQuantity, Double price, String coinName) {
		super();
		this.userId = userId;
		this.coinQuantity = coinQuantity;
		this.price = price;
		this.coinName = coinName;
	}
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