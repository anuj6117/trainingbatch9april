package com.example.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="OtpTable")
public class UserOtpTable
{
	
@Id
private String tokenOTP;
private String email;
private String date;


public String getTokenOTP() {
	return tokenOTP;
}
public void setTokenOTP(String tokenOTP) {
	this.tokenOTP = tokenOTP;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getDateCreated() {
	return date;
}
public void setDateCreated(String date) {
	this.date = date;
}

public UserOtpTable(String tokenOTP, String email, String date) {
	super();
	this.tokenOTP = tokenOTP;
	this.email = email;
	this.date = date;
}
public UserOtpTable() {
}
}
