package com.trading.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserOtp {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long userId;
	private int tokenOTP;
	private String emailid;
	
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	public int gettokenOTP() {
		return tokenOTP;
	}
	public void settokenOTP(int tokenOTP) {
		this.tokenOTP = tokenOTP;
	}
	
}
