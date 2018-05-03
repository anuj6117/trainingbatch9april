package com.trading.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserOtp {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long Id;

	
	private int tokenOTP;

	
	@Column(unique = true)
	
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
