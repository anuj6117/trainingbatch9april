package com.trainingproject.dto;

public class UserVerificationDto {
	private Integer userId;
	private Integer tokenOTP;
	
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
