package com.training.demo.dto;

import java.util.Date;

import com.training.demo.enums.OrderStatus;
import com.training.demo.enums.OrderType;
import com.training.demo.enums.WalletType;
import com.training.demo.model.User;

public class UserOrder {

	private OrderType orderType;
	private WalletType walletType;
	private String coinName;
	private Double price;
	private Date orderCreatedOn;
	private OrderStatus orderStatus;
	
	public OrderType getOrderType() {
		return orderType;
	}
	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}
	public WalletType getWalletType() {
		return walletType;
	}
	public void setWalletType(WalletType walletType) {
		this.walletType = walletType;
	}
	public String getCoinName() {
		return coinName;
	}
	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Date getOrderCreatedOn() {
		return orderCreatedOn;
	}
	public void setOrderCreatedOn(Date orderCreatedOn) {
		this.orderCreatedOn = orderCreatedOn;
	}
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(OrderStatus orderStatus) {
		orderStatus = orderStatus;
	}
	
	public void setUser(User user) {
		// TODO Auto-generated method stub
		
	}	

}