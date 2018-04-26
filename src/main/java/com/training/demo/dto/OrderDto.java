package com.training.demo.dto;

public class OrderDto {

	private Integer userId;
	private String walletType;
	private Double amount;
	private String coinName;
	
	public OrderDto() {
		super();
	}

	public OrderDto(Integer userId, String walletType, Double amount, String coinName) {
		super();
		this.userId = userId;
		this.walletType = walletType;
		this.amount = amount;
		this.coinName = coinName;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getWalletType() {
		return walletType;
	}

	public void setWalletType(String walletType) {
		this.walletType = walletType;
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