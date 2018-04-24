package com.trainingproject.dto;

import com.trainingproject.enums.UserOrderStatus;

public class ApproveWalletBean {

	private Integer orderId;
	private UserOrderStatus orderStatus;
	
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public UserOrderStatus getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(UserOrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	
}
