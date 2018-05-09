package com.example.demo.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="UserOTP")
public class UserOTP {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer userOTPId;
	
	private Integer tokenOTP;
	private String emailId;
	private Date date;
	
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	

	public Integer getUserOTPId() {
		return userOTPId;
	}
	public void setUserOTPId(Integer userOTPId) {
		this.userOTPId = userOTPId;
	}
	

	public Integer getTokenOTP() {
		return tokenOTP;
	}

	public void setTokenOTP(Integer tokenOTP) {
		this.tokenOTP = tokenOTP;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

}
