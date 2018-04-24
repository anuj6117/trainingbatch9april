package com.trading.dto;

import com.trading.Enum.OrderStatus;

public class OrderApprovalDto {
	private long orderId;
	private OrderStatus status;
	private long userId;
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}

}
