package com.trading.domain;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trading.Enum.UserStatus;

@Entity
public class User {
	
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private long userId;
	
@NotNull
@Size(max=25, message = "Name can have maximum 25 characters")
private String userName;
	
@NotNull
@Email(message = "Incorrect Format")
@Column(unique = true)
private String email;
	
@NotNull
private String password;
	
@NotNull
private String confirmpassword;

@NotNull
private String country;

@NotNull
@Column(unique = true)
@Digits(fraction = 0, integer = 10)
private long phoneNumber;

@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
private Date date;

@Enumerated(EnumType.STRING)
private UserStatus status;

	public String getConfirmpassword() 
	{
		return confirmpassword;
	}
	
	public void setConfirmpassword(String confirmpassword) 
	{
		this.confirmpassword = confirmpassword;
	}
	
@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,CascadeType.MERGE })

@JoinTable(name = "user_role",joinColumns = { @JoinColumn(name = "user_Id", referencedColumnName="userId") },
inverseJoinColumns = { @JoinColumn(name = "role_Id", referencedColumnName="roleId")})
    
private List<Role> role = new ArrayList<>();
	
@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)

private List<Wallet> wallet = new ArrayList<>();
	
@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
private List<UserOrder> userOrder = new ArrayList<>();
	
	public List<UserOrder> getUserOrder() 
	{
		return userOrder;
	}
	
	public void setUserOrder(List<UserOrder> userOrder) 
	{
		this.userOrder = userOrder;
	}
	
	public List<Role> getRole() 
	{
		return role;
	}
	
	public void setRole(List<Role> role) 
	{
		this.role = role; 
	}
	
	public long getUserId() 
	{
		return userId;
	}
	
	public void setUserId(long userId)
	{
		this.userId = userId;
	}
	
	public List<Wallet> getWallet() {
		return wallet;
	}
	public void setWallet(List<Wallet> wallet) {
		this.wallet = wallet;
	}
	public UserStatus getStatus() {
		return status;
	}
	public void setStatus(UserStatus status) {
		this.status = status;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
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
	public long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}	

}
