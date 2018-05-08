package com.training.demo.dto;

public class OrderApprovalDto {

	private Integer orderId;
	private String status;
	private Integer userId;
	private String description;
	
	public OrderApprovalDto() {
		super();
	}
	
	public OrderApprovalDto(Integer orderId, String status, Integer userId, String description) {
		super();
		this.orderId = orderId;
		this.status = status;
		this.userId = userId;
		this.description = description;
	}

	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getDescription() {
		// TODO Auto-generated method stub
		return description;
	}	
	
	public void setDescription(String description) {
		this.description = description;
	}
}
