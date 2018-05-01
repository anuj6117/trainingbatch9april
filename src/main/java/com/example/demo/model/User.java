package com.example.demo.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
import com.example.demo.enums.UserStatus;


@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer userId;

	private String userName;
	private String email;
	private String phoneNumber;
	private String password;
	private String country;
	private Date date;

	@Enumerated(EnumType.STRING)
	private UserStatus status;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "User_Role", joinColumns = @JoinColumn(name = "userid", referencedColumnName = "userId"), inverseJoinColumns = @JoinColumn(name = "roleid", referencedColumnName = "roleId"))
	private Set<Role> roles = new HashSet<Role>();
	
	@OneToMany(mappedBy = "user")
	private Set<Wallet> wallets = new HashSet<Wallet>();
	
	@OneToMany(mappedBy = "user")
	private List<Order> userOrders=new ArrayList<Order>();

	public User() {}
	
	public User(User user) 
	{
		this.userName = user.getUserName();
		this.email = user.getEmail();
		this.phoneNumber = user.getPhoneNumber();
		this.password = user.getPassword();
		this.country = user.getCountry();
		this.date = new Date();
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

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
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
		this.wallets = wallets;
	}

}
