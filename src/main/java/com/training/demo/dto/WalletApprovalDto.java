package com.training.demo.dto;

import com.training.demo.enums.UserOrderStatus;

public class WalletApprovalDto {
	private Integer userId;
	private Integer orderId;

	UserOrderStatus orderstatus;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public UserOrderStatus getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(UserOrderStatus orderstatus) {
		this.orderstatus = orderstatus;
	}
}
