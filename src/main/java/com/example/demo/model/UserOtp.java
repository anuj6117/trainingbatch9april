package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;


@Component
@Entity
@Table(name="UserVerify")
public class UserOtp {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer userid;

	@Column(name="Email", unique=true, nullable=false)
	private String email;
	
	private String otp;

	private String verified;

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getVerified() {
		return verified;
	}

	public void setVerified(String verified) {
		this.verified = verified;
	}

	public UserOtp(String email, String otp, String verified) {
		super();
		this.email = email;
		this.otp = otp;
		this.verified = verified;
	}
	
	public UserOtp(){}

}
