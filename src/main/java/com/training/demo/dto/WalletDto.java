package com.training.demo.dto;

public class WalletDto {
	
	private Integer userId;
	
	private String walletType;
	
	private Double price;
	
	private String coinName;
	
	public WalletDto() {
		super();
	}
	public WalletDto(Integer userId, String walletType) {
		super();
		this.userId = userId;
		this.walletType = walletType;
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
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getPrice() {
		// TODO Auto-generated method stub
		return price;
	}
	public String getCoinName() {
		return coinName;
	}
	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}
	
	
}