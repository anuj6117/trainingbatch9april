package com.example.demo.dto;

public class UserOrderDTO {
	
	private Integer userId;
	private Integer coinQuantity;
	private Integer price;
	private String coinName;
	private String orderType;
		
	public String getOrderType() {
		return orderType;
	}

	public Integer getUserId() {
		return userId;
	}
	
	public Integer getCoinQuantity() {
		return coinQuantity;
	}
	
	public Integer getPrice() {
		return price;
	}
	
	public String getCoinName() {
		return coinName;
	}
	
}
