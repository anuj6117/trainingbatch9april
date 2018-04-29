package com.training.demo.model;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "OtpVerification")
public class OtpVerification {
	@Id
	@GeneratedValue
	private Integer id;
	private String email;	
	private Integer userId;
	@Column(name="token123")
	private Integer tokenOTP;	
	private Date date;
	
	public Integer getUserId() {
		return userId;
	}
	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Integer getTokenOTP() {
		return tokenOTP;
	}

	public void setTokenOTP(Integer tokenOTP) {
		this.tokenOTP = tokenOTP;
	}

	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}	
}