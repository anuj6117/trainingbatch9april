package com.training.demo.dto;

public class WalletDto {
	
	private Integer userId;
	
	private String walletType;
	
	private String coinName;
	
	public WalletDto() {
		super();
	}

	
	public WalletDto(Integer userId, String walletType, String coinName) {
		super();
		this.userId = userId;
		this.walletType = walletType;
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

	public String getCoinName() {
		return coinName;
	}
	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}
	
	
}