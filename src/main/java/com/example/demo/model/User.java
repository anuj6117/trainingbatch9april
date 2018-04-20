package com.example.demo.model;

import java.util.Collection;
import java.util.Date;
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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.example.demo.enums.Status;

@Entity
@Table(name="user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer userId;
	
	@Size(min=6, max=25, message="Fullname length must be between 6 to 25")
	@NotEmpty(message="Fullname should not left blank")
	@NotNull(message="Space is not allowed")
	private String userName;
	
	@NotEmpty(message="Email should not left blank")
	@NotNull(message="Space is not allowed")
    @Email
    @Column(unique=true)
	private String email;
	private String phoneNumber;
	private String password;
	private String country;
	private Date date;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "User_Role", 
	joinColumns = @JoinColumn(name = "userid", referencedColumnName = "userId"), 
	inverseJoinColumns = @JoinColumn(name = "roleid", referencedColumnName = "roleId"))
	private Set<Role> roles;
	
	@OneToMany(mappedBy="user")
	private Collection<Wallet> wallets;
	
	public User()
	{
		System.out.println("Default Constructor");

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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Collection<Wallet> getWallets() {
		return wallets;
	}

	public void setWallets(Collection<Wallet> wallets) {
		this.wallets = wallets;
	}

	
}
