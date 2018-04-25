package com.training.demo.dto;

public class OrderDto {

	private Integer userId;
	private Integer coinQuantity;
	private Double amount;
	private String coinName;
	
	public OrderDto() {
		super();
	}

	public OrderDto(Integer userId, Integer coinQuantity, Double amount, String coinName) {
		super();
		this.userId = userId;
		this.coinQuantity = coinQuantity;
		this.amount = amount;
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

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getCoinName() {
		return coinName;
	}

	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}


	
}