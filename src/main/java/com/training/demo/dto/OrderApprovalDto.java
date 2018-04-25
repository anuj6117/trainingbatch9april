package com.training.demo.dto;

import com.training.demo.enums.OrderStatus;

public class OrderApprovalDto {

	private String orderId;
	private OrderStatus status;
	private long userId;
	
	public OrderApprovalDto() {
		super();
	}
	
	public OrderApprovalDto(String orderId, OrderStatus status, long userId) {
		super();
		this.orderId = orderId;
		this.status = status;
		this.userId = userId;
	}

	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}	
}
