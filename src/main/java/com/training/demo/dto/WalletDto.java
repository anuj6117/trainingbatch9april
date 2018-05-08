package com.training.demo.dto;

public class WalletDto {
	
	private Integer userId;
	
	private String coinType;
	
	private String coinName;
	
	public WalletDto() {
		super();
	}

	
	public WalletDto(Integer userId, String coinType, String coinName) {
		super();
		this.userId = userId;
		this.coinType = coinType;
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

	public String getCoinName() {
		return coinName;
	}
	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}
	
}