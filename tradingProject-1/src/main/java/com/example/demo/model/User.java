package com.example.demo.model;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.example.demo.enums.UserStatus;


@Entity
@Table(name="userstable")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@NotNull
	private String userName;
	
	@NotNull
	private String email;
	
	@NotNull
	private String password;
	
	@NotNull
	private String country;
	
	@NotNull
	private String phoneNumber;
	
	private UserStatus status;
	
	private String date;

	
	@ManyToMany(fetch = FetchType.EAGER,cascade = {
					CascadeType.PERSIST,
					CascadeType.MERGE
			})
	@JoinTable(name="user_role",
	joinColumns = {@JoinColumn(name = "user_id", referencedColumnName="id")},
	inverseJoinColumns = { @JoinColumn(name = "role_id", referencedColumnName="roleId") })
	private Set<Role> role = new HashSet<>();

	public Set<Wallet> getWallets() {
		return wallets;
	}

	public void setWallets(Set<Wallet> wallets) {
		this.wallets = wallets;
	}

	@OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
	private Set<Wallet> wallets=new HashSet<>();


	public Set<Role> getRole() {
		return role;
	}

	public void setRole(Set<Role> role) {
		this.role = role;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
