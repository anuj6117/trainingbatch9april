package com.example.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="OtpTable")
public class UserOtpTable
{
	
public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
@Id
private String otp;
private String email;
private String date;

public String getEmailId() {
	return email;
}
public void setEmailId(String email) {
	this.email = email;
}
public String getDateCreated() {
	return date;
}
public void setDateCreated(String date) {
	this.date = date;
}
public UserOtpTable(String otp, String email, String date) {
	super();
	this.otp = otp;
	this.email = email;
	this.date = date;
}
public UserOtpTable() {
}
}
