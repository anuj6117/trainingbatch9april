
package com.example.dto;

import com.example.enums.StatusType;

public class WalletApprovalDto
{
 private Integer userId;
 private Integer orderId;
 private StatusType statusType;
 private String description;

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
public StatusType getStatusType() {
	return statusType;
}
public void setStatusType(StatusType statusType) {
	this.statusType = statusType;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}

 
 
}
