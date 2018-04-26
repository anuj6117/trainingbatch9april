package com.trainingproject.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.trainingproject.enums.WalletType;

public class UserOrderBuySellDto {
	private Integer userId;
	private Long coinQuantity;
	private Long price;
	private String coinName;
	@Enumerated(EnumType.STRING)
	private WalletType coinType;
	
	
	public WalletType getCoinType() {
		return coinType;
	}
	public void setCoinType(WalletType coinType) {
		this.coinType = coinType;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public Long getCoinQuantity() {
		return coinQuantity;
	}
	public void setCoinQuantity(Long coinQuantity) {
		this.coinQuantity = coinQuantity;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public String getCoinName() {
		return coinName;
	}
	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}

}
