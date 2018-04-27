package com.example.model;

import java.util.HashSet;
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
import javax.validation.constraints.Size;

import com.example.enums.UserStatus;

@Entity	
@Table(name="UserTable")
public class User
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer userId;

	@NotNull
	@Size(min=1,max=25,message="name should be less than equal to 25")
	private String userName;
	@Email
	private String email;
	@Column(unique=true)
	@Size(min=10,max=10,message="phonenumber length should be 10 only")
	private String phoneNumber;
	@Size(min=8,max=32)
	private String password;
	@NotNull
	private String country;
	private String createdOn;
	
	public String getCreatedOn() {
		return createdOn;
	}


	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	@ManyToMany(cascade = CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinTable(name="user_role",
	joinColumns= @JoinColumn(name="userid",referencedColumnName="userId"),
	inverseJoinColumns= @JoinColumn(name="roleid",referencedColumnName="roleId"))
	private Set<Role> roles;

	@OneToMany(mappedBy="user")
	private Set<Wallet> wallet = new HashSet<>();
	
	
	@OneToMany(mappedBy="user")
	private Set<UserOrder> userorder = new HashSet<>();
	
	public Set<Role> getRoles() {
		return roles;
	}


	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	
	
	
	
	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


     


	public Set<UserOrder> getUserorder() {
		return userorder;
	}


	public void setUserorder(Set<UserOrder> userorder) {
		this.userorder = userorder;
	}


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
	
	
	
	
	public User(Integer userId, @NotNull String userName, String email, String phoneNumber, String password,
			String country, UserStatus status) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.password = password;
		this.country = country;
		this.status = status;
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
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
}