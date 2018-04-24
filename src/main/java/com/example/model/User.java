package com.example.model;

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
import javax.validation.constraints.NotNull;

import com.example.enums.UserStatus;

@Entity	
@Table(name="UserTable")
public class User
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer userId;

	@NotNull
	private String fullName;
	
	private String email;
	private String phoneNo;
	private String password;
	private String country;
	private String date;
	
	@ManyToMany(cascade = CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinTable(name="user_role",
	joinColumns= @JoinColumn(name="userid",referencedColumnName="userId"),
	inverseJoinColumns= @JoinColumn(name="roleid",referencedColumnName="roleId"))
	private Set<Role> roles;
	
	public Set<Role> getRoles() {
		return roles;
	}


	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	@OneToMany(mappedBy="user")
	private Set<Wallet> wallet = new HashSet<>();
	
	
	@OneToMany(mappedBy="user")
	private Set<Order> order = new HashSet<>();
	
	public Set<Wallet> getWallet() {
		return wallet;
	}


	public void setWallet(Set<Wallet> wallet) {
		this.wallet = wallet;
	}

	@Enumerated(EnumType.STRING)
	private UserStatus status;

	public UserStatus getStatus() {
		return status;
	}


	public void setStatus(UserStatus status) {
		this.status = status;
	}


	public User()
	{
		System.out.println("Default Constrctor");
	}
	
	
	public User( String fullName, String email, String phoneNo, String password, String country ,UserStatus status,String date) 
	{
		this.fullName = fullName;
		this.email = email;
		this.phoneNo = phoneNo;
		this.password = password;
		this.country = country;
		this.status=status;
	
		
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
}