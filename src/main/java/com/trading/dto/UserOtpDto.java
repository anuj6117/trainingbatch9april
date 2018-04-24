package com.trading.dto;

public class UserOtpDto {
	private long userId;
	private int tokenOTP;
	public long getuserId() {
		return userId;
	}
	public void setuserId(long userId) {
		this.userId = userId;
	}
	public int getTokenOTP() {
		return tokenOTP;
	}
	public void setTokenOTP(int tokenOTP) {
		this.tokenOTP = tokenOTP;
	}
	

}
