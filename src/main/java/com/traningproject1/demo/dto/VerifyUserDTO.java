package com.traningproject1.demo.dto;

public class VerifyUserDTO {
 private Integer userId;
 private Integer tokenOTP;
 private String emailId;
public String getEmailId() {
	return emailId;
}
public void setEmailId(String emailId) {
	this.emailId = emailId;
}
public Integer getUserId() {
	return userId;
}
public void setUserId(Integer userId) {
	this.userId = userId;
}
public Integer getTokenOTP() {
	return tokenOTP;
}
public void setTokenOTP(Integer tokenOTP) {
	this.tokenOTP = tokenOTP;
}

}
