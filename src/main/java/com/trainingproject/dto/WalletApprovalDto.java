package com.trainingproject.dto;

import com.trainingproject.enums.Status;

public class WalletApprovalDto {
	private Integer orderId;
	private Status status;
	

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

}
