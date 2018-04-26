package com.training.demo.model;

import java.util.Date;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.training.demo.enums.UserStatus;

@Entity
@Table(name = "UserTable")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer userId;
	private String userName;
	@NotEmpty(message = "Email must not be empty")
	@Email
	@NotBlank(message = "Space Not Accepted")
	private String email;
	@Size(min = 10, max = 10)
	private String phoneNo;
	@NotNull
	private String password;
	@NotNull
	private String country;
	private Date date;
	@Enumerated(EnumType.STRING)
	private UserStatus userStatus;

	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinTable(name = "User_Role", joinColumns = @JoinColumn(name = "userid", referencedColumnName = "userId"), inverseJoinColumns = @JoinColumn(name = "roleid", referencedColumnName = "roleId"))
	private Set<Role> roles;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Wallet> Wallet;
	
	
	
	

	public User() {
		super();
		System.out.println("Default Constructor");
	}

	public User(Integer userId, String userName, String email, String phoneNo, String password, String country) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.email = email;
		this.phoneNo = phoneNo;
		this.password = password;
		this.country = country;
	}

	public Set<Wallet> getWallet() {
		return Wallet;
	}

	public void setWallet(Set<Wallet> wallet) {
		Wallet = wallet;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getuserName() {
		return userName;
	}

	public void setuserName(String fullName) {
		this.userName = fullName;
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

	public String toString() {
		return userId + ", \t" + userName + ", \t" + email + ", \t" + country;
	}
}