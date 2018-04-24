package com.example.demo.model;

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
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.example.demo.enums.UserStatus;



@Entity
@Table(name="User")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="name")
	@NotNull
	private String userName;
	
	@Email
	@Column(name="Email", unique=true, nullable=false)
	private String email;
	
	@Column(name="phoneNumber", unique=true, nullable=false)
	private String phoneNumber;
	
	@NotNull
	private String country;
	
	@NotNull
	private String password;
	
	@Enumerated(EnumType.STRING)
	@Column(name="status")
	private UserStatus status;
	
	private String date;
	
	@ManyToMany(fetch = FetchType.EAGER,
			cascade = {
					CascadeType.PERSIST,
					CascadeType.MERGE
			})
	@JoinTable(name="user_role",
	joinColumns = {@JoinColumn(name = "user_id123", referencedColumnName="id")},
	inverseJoinColumns = { @JoinColumn(name = "role_id123", referencedColumnName="roleId") })
	private Set<Role> role = new HashSet<>();	
	
	@OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
	private Set<Wallet> wallet = new HashSet<>();
	
	@OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
	private Set<OrderDetails> orderDetails = new HashSet<>();
	
	@OneToMany(mappedBy="user")
	private List<Transaction> transaction = new ArrayList<>();
	
	public User() {}	

	public Set<Wallet> getWallet() {
		return wallet;
	}

	public void setWallet(Set<Wallet> wallet) {
		this.wallet = wallet;
	}

	public Integer getId() {
		return id;
	}	
		
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}	
	
	public UserStatus getStatus() {
		return status;
	}
	
	public void setStatus(UserStatus status) {
		this.status = status;
	}
	
	public User(UserStatus status , String date) {
		this.status=status;
		this.date=date;
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
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}


	public Set<Role> getRole() {
		return role;
	}


	public void setRole(Set<Role> role) {
		this.role = role;
	}
	
}
