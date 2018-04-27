package com.trading.dto;

import com.trading.Enum.TransactionOrderStatus;

public class OrderApprovalDto {
	private long orderId;
	private TransactionOrderStatus status;
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
	public TransactionOrderStatus getStatus() {
		return status;
	}
	public void setStatus(TransactionOrderStatus status) {
		this.status = status;
	}

}
