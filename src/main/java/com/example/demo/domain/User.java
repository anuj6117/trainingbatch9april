package com.example.demo.domain;

import java.util.Date;
import java.util.List;
import java.util.Set;

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
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.example.demo.enumeration.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="User")
public class User {
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
@NotNull
private Integer userId;



private String userName;

@Email

private String email;

private String password;
//private String confirmPassword;

private String  country;

//@JsonIgnore
//@OneToMany(mappedBy="user")
//private List<UserOrder>userOrder;

//private Date createdOn;
@Enumerated(value=EnumType.STRING)
private UserStatus status;

@NotNull
private String phoneNumber;


@ManyToMany(cascade= { CascadeType.PERSIST,CascadeType.MERGE},fetch=FetchType.EAGER)
@JoinTable(name="user_role",joinColumns= 
{@JoinColumn(name="user_id",referencedColumnName="userId")},inverseJoinColumns= 
{@JoinColumn(name="role_id",referencedColumnName="id")})
@Column(nullable=true)
private List<Role> role;

public User()
{
 super();	
}
public User(User user)
{
	this.userId=user.userId;
	this.userName=user.userName;
	this.phoneNumber=user.phoneNumber;
	this.password=user.password;
	this.email=user.email;
	this.role=user.role;
	this.status=user.status;
	this.wallet=user.wallet;
	this.userorder=user.userorder;
	//this.createdOn=user.createdOn;
}
@OneToMany(mappedBy="user")
private Set<Wallet> wallet;
@JsonIgnore
@OneToMany(mappedBy="user")
private List<UserOrder>userorder;

public Set<Wallet> getWallet() {
	return wallet;
}
public void setWallet(Set<Wallet> wallet) {
	this.wallet = wallet;
}
public List<Role> getRole() {
	return role;
}
public void setRole(List<Role> role) {
	this.role = role;
}
public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}

public Integer getUserId() {
	return userId;
}
public void setUserId(Integer userId) {
	this.userId = userId;
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
/*public String getConfirmPassword() {
	return confirmPassword;
}
public void setConfirmPassword(String confirmPassword) {
	this.confirmPassword = confirmPassword;
}*/




//public List<UserOrder> getUserOrder() {
//	return userOrder;
//}
//public void setUserOrder(List<UserOrder> userOrder) {
//	this.userOrder = userOrder;
//}
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

public UserStatus getStatus() {
	return status;
}
public void setStatus(UserStatus status) {
	this.status = status;
}
public List<UserOrder> getUserorder() {
	return userorder;
}
public void setUserorder(List<UserOrder> userorder) {
	this.userorder = userorder;
}
//public Date getCreatedOn() {
//	return createdOn;
//}
//public void setCreatedOn(Date createdOn) {
//	this.createdOn = createdOn;
//}
}
