package com.traningproject1.domain;

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
	
	private Integer tokenOtp;
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
	public Integer getTokenOtp() {
		return tokenOtp;
	}

	public void setTokenOtp(Integer tokenOtp) {
		this.tokenOtp = tokenOtp;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

}
