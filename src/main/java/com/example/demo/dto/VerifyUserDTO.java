package com.example.demo.dto;

public class VerifyUserDTO {
// private Integer userId;
 private Integer tokenOTP;
 private String emailId;
 public String getEmailId() {
	return emailId;
}
public void setEmailId(String emailId) {
	this.emailId = emailId;
}

public Integer getTokenOTP() {
	return tokenOTP;
}
public void setTokenOTP(Integer tokenOTP) {
	this.tokenOTP = tokenOTP;
}

}
