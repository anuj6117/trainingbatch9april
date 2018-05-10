package com.example.demo.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "verifyotp")
public class VerifyOtp {

	@Id
	@GeneratedValue
	private Integer id;
	private Integer tokenOTP;
	private String email;
	private Date date;
	
	public VerifyOtp() 
	{
		
	}
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
}
