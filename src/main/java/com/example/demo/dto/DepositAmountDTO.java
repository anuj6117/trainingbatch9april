package com.example.demo.dto;

import com.example.demo.enums.OrderType;
import com.example.demo.enums.WalletType;

public class DepositAmountDTO
{
	private Integer userId;
	private WalletType walletType;
	private Double amount;
	private Double coinQuantity;
	private OrderType orderType;
	private String coinName;

	public String getCoinName() {
		return coinName;
	}

	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setWalletType(WalletType walletType) {
		this.walletType = walletType;
	}

	public WalletType getWalletType() {
		return walletType;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	public Double getCoinQuantity() {
		return coinQuantity;
	}

	public void setCoinQuantity(Double coinQuantity) {
		this.coinQuantity = coinQuantity;
	}
	
	
}
