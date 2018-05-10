package com.training.demo.dto;

import com.training.demo.enums.WalletType;

public class OrderDto {

	private Integer userId;
	private String coinType;
	private Double amount;
	private String coinName;
	
	public OrderDto() {
		super();
	}

	public OrderDto(Integer userId, String coinType, Double amount, String coinName) {
		super();
		this.userId = userId;
		this.coinType = coinType;
		this.amount = amount;
		this.coinName = coinName;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getCoinType() {
		return coinType;
	}

	public void setCoinType(String coinType) {
		this.coinType = coinType;
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