package com.training.demo.model;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.training.demo.enums.UserStatus;

@Entity
@Table(name="UserTable")
public class User
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer userId;
	private String fullName;
	private String email;
	private String phoneNo;
	private String password;
	private String country;
	private Date date;
	private String status;
	
	public User()
	{	super();
		date = new Date();
		System.out.println("Default Constructor");
	}
	
	public User(Integer userId, String fullName, String email, String phoneNo, String password, String country,
			Date date) {
		super();
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
	public void setDate(Date d) {
		this.date = date;
	}
	public String getUserStatus() {
		return status;
	}
	public void setStatus(UserStatus status) {
		this.status = status.toString();
	}
	public String toString()
	{
		System.out.println("/////////////////////////////////////////");
		return userId+", \t"+fullName+", \t"+email+", \t"+country;
	}
}