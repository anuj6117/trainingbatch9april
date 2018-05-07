package com.training.demo.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "OtpVerification")
public class OtpVerification {
	@Id
	Integer userId;
	String email;
	Integer tokenOTP;
	

	Date date;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}