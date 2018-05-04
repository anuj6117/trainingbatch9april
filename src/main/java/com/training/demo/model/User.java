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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.training.demo.enums.UserStatus;

@Entity
@Table(name="UserTable")
public class User
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer userId;
	
	@NotEmpty
	@Size(min=2, max=30)	
	@NotNull(message="Please enter your full name.")
	private String userName;
	
	@NotNull
	@Email
	@NotEmpty(message="Please enter your email address.")
	private String email;
	
	@NotNull(message = "Please enter your mobile number.")
	private String phoneNumber;
	
	@NotNull
	@NotEmpty(message = "Please enter your password.")
	@Size(min = 8, max = 32, message = "Your password must between 8 and 32 characters")
	private String password;
	
	@NotNull
	private String country;
	private Date date;
	@Enumerated(EnumType.STRING)
	private UserStatus userStatus;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", 
	joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "userId"), 
	inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "roleId"))
	private Set<Role> roles =new HashSet<Role>();
	

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Wallet> wallets = new HashSet<Wallet>();
		
	@OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
	@JsonIgnore
	private Set<OrderTable> orderTable = new HashSet<>();
	
	public User()
	{	super();
		System.out.println("Default Constructor");
	}
	
	public User(Integer userId, String userName, String email, String phoneNumber, String password, String country) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.password = password;
		this.country = country;
	}

	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
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
				return userId+", \t"+userName+", \t"+email+", \t"+country;
	}
	
}