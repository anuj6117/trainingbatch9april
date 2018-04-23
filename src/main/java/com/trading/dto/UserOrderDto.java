package com.trading.dto;

import com.trading.Enum.WalletType;

public class UserOrderDto {
	
	private long userId;
	private long coinQuantity;
	private long price;
	private WalletType coinName;
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getCoinQuantity() {
		return coinQuantity;
	}
	public void setCoinQuantity(long coinQuantity) {
		this.coinQuantity = coinQuantity;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public WalletType getCoinName() {
		return coinName;
	}
	public void setCoinName(WalletType coinName) {
		this.coinName = coinName;
	}

}
