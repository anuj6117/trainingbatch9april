package com.example.demo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.demo.enums.StatusEnum;

@Entity
@Table(name="Trading")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(name="name")
	private String fullName;
	
	private String email;
	private String mobile;
	private String country;
	private String password;
	private String date;
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(name="status")
	private StatusEnum status;
	public StatusEnum getStatus() {
		return status;
	}
	
	public void setStatus(StatusEnum status) {
		this.status = status;
	}
	
	public User() {}
	
	
	public User(StatusEnum status , String date) {
		this.status=status;
		this.date=date;
	}
	
	
	public User(String fullName, String email, String mobile, String country, String password, String date,
			StatusEnum status) {
		super();
		this.fullName = fullName;
		this.email = email;
		this.mobile = mobile;
		this.country = country;
		this.password = password;
		this.date = new Date().toString();
		this.status = status;
	}
	
	
	public String getFullName() {
		return fullName;
	}
	
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
}
