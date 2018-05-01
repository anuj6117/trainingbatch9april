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
	private Integer tokenOtp;
	private String emailId;
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
