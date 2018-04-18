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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.example.demo.enums.StatusEnum;



@Entity
@Table(name="Trading")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(name="name")
	@NotNull
	private String userName;
	@Email
	@Column(name="Email", unique=true, nullable=false)
	private String email;
	@Column(name="phoneNumber", unique=true, nullable=false)
	private String phoneNumber;
	@NotNull
	private String country;
	@NotNull
	private String password;
	@Enumerated(EnumType.STRING)
	@Column(name="status")
	private StatusEnum status;
	private String date;
	
	
	
	public Integer getId() {
		return id;
	}
	
		
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	
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
	
	
	public User(String userName, String email, String phoneNumber, String country, String password, String date,
			StatusEnum status) {
		super();
		this.userName = userName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.country = country;
		this.password = password;
		this.date = new Date().toString();
		this.status = status;
	}
	
	
	public String getUserName() {
		return userName;
	}
	
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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
