package com.training.demo.dto;

import com.training.demo.enums.OrderStatus;

public class OrderApprovalDto {

	private Integer orderId;
	private OrderStatus status;
	private Integer userId;
	
	public OrderApprovalDto() {
		super();
	}
	
	public OrderApprovalDto(Integer orderId, OrderStatus status, Integer userId) {
		super();
		this.orderId = orderId;
		this.status = status;
		this.userId = userId;
	}

	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}	
}
