package com.trading.domain;

import java.util.ArrayList;
import java.util.HashSet;
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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trading.Enum.UserStatus;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long userId;
  
	@NotBlank
	@NotNull
	private String userName;
	
	@NotBlank
	@NotNull

	@Column(unique = true)
	private String email;
	
	@NotBlank
	@NotNull
	private String password;

	
	@NotBlank
	@NotNull
	private String country;
	
	
	@NotNull
	@Column(unique = true)
	
	private long phoneNumber;

//	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss+05:30", timezone = "India/Delhi")
	private String date;

	@Enumerated(EnumType.STRING)
	private UserStatus status;



	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })

	@JoinTable(name = "user_role", joinColumns = {
			@JoinColumn(name = "user_Id", referencedColumnName = "userId") }, inverseJoinColumns = {
					@JoinColumn(name = "role_Id", referencedColumnName = "roleId") })

	private Set<Role> role = new HashSet<>();

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)

	private List<Wallet> wallet = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<UserOrder> userOrder = new ArrayList<>();

	public List<UserOrder> getUserOrder() {
		return userOrder;
	}

	public void setUserOrder(List<UserOrder> userOrder) {
		this.userOrder = userOrder;
	}

	public Set<Role> getRole() {
		return role;
	}

	public void setRole(Set<Role> role) {
		this.role = role;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
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

	public User()
	{
		
	}

	public User(User user) {
		this.userId = userId;
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.country = country;
		this.phoneNumber = phoneNumber;
		this.date = date;
		this.status = status;
	}
	
}
