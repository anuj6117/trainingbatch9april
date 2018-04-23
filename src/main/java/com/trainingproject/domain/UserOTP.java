package com.trainingproject.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "userotp")
public class UserOTP {
	@Id
	private Integer tokenOTP;
	private String email;
	private Date date;
	
	
	
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
