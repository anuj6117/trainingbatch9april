package com.training.demo.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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

import com.fasterxml.jackson.annotation.JsonIgnore;
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
	@Enumerated(EnumType.STRING)
	private UserStatus userStatus;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", 
	joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "userId"), 
	inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "roleId"))
	private Set<Role> roles;
	

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Wallet> wallets = new HashSet<Wallet>();
		
	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private Set<OrderTable> orderTable = new HashSet<>();
	
	public User()
	{	super();
		System.out.println("Default Constructor");
	}
	
	public User(Integer userId, String fullName, String email, String phoneNo, String password, String country) {
		super();
		this.userId = userId;
		this.fullName = fullName;
		this.email = email;
		this.phoneNo = phoneNo;
		this.password = password;
		this.country = country;
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
	public UserStatus getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}
	
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<Wallet> getWallets() {
		return wallets;
	}

	public void setWallets(Set<Wallet> wallets) {
		this.wallets= wallets;
	}

	public Set<OrderTable> getOrderTable() {
		return orderTable;
	}

	public void setOrderTable(Set<OrderTable> orderTable) {
		this.orderTable = orderTable;
	}	
	
	public String toString()
	{
				return userId+", \t"+fullName+", \t"+email+", \t"+country;
	}
	
}