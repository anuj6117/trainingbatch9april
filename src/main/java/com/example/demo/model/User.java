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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.demo.enums.Status;

@Entity
@Table(name="user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer userId;
	
	@Size(min=6, max=25, message="Fullname length must be between 6 to 25")
	@NotEmpty(message="Fullname should not left blank")
	@NotNull(message="Space is not allowed")
	private String fullName;
	
	@NotEmpty(message="Email should not left blank")
	@NotNull(message="Space is not allowed")
	@Email
	private String email;
	private String phoneNo;
	private String password;
	private String country;
	private Date date;
	private String st;
	
	public User()
	{
		System.out.println("Default Constructor");
	}
	
	public User(String fullName, String email, String phoneNo, String password, String country,
			Date date) 
	{
		System.out.println("All Arguement constructor-1");
		this.userId = userId;
		this.fullName = fullName;
		this.email = email;
		this.phoneNo = phoneNo;
		this.password = password;
		this.country = country;
		this.date = date;
	}
	
	
	
	public User(Integer userId, String fullName, String email, String phoneNo, String password, String country,
			Date date) 
	{
		System.out.println("All Arguement constructor");
		this.userId = userId;
		this.fullName = fullName;
		this.email = email;
		this.phoneNo = phoneNo;
		this.password = password;
		this.country = country;
		this.date = date;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
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
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	//@Enumerated(EnumType.STRING)
	public String getSt() {
		return st;
	}

	public void setSt(Status st) {
		this.st = st.toString();
	}
	

}
