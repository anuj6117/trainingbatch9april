package com.traningproject1.demo.dto;

import com.traningproject1.enumsclass.TransactionStatus;

public class WalletApprovalDTO {
 private Integer userorderId;
 private String message;
 private Integer userId;
 


// @Enumerated(value=EnumType.STRING) 
private TransactionStatus transactionStatus;

 public Integer getUserorderId() {
	return userorderId;
}

public String getMessage() {
	return message;
}

public void setMessage(String message) {
	this.message = message;
}

public void setUserorderId(Integer userorderId) {
	this.userorderId = userorderId;
}
public TransactionStatus getTransactionStatus() {
	return transactionStatus;
}

public void setTransactionStatus(TransactionStatus transactionStatus) {
	this.transactionStatus = transactionStatus;
}
public Integer getUserId() {
	return userId;
}

public void setUserId(Integer userId) {
	this.userId = userId;
}
}
