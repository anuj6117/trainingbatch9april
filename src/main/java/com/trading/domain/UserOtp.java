package com.trading.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
public class UserOtp {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long userId;
	@NotNull
	private int tokenOTP;
	@NotNull
	@Column(unique= true)
	@Email
	private String email;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int gettokenOTP() {
		return tokenOTP;
	}
	public void settokenOTP(int tokenOTP) {
		this.tokenOTP = tokenOTP;
	}
	
}
