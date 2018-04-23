package com.trainingproject.domain;

import java.util.Date;
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
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.trainingproject.enums.Status;

@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer userId;
	@Size(min = 4, max = 25, message = "Fullname length must be between 6 to 25")
	@NotEmpty(message = "Fullname should not left blank")
	@NotNull(message = "Space is not allowed")
	private String userName;
	
	@NotEmpty(message = "Email should not left blank")
	@NotNull(message = "Blank or Space is not allowed")
	@Email
	private String email;
	private String password;
	private String country;
	private Date createdOn;
	@Enumerated(EnumType.STRING)
	private Status status;
	private Long phoneNumber;
	
	@OneToMany(mappedBy = "user")
	private Set<Wallet> userWallet;
	@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinTable(name = "user_role",joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "userId"),
	inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "roleId"))
	private List<Role> roleType;
	
	public Set<Wallet> getUserWallet() {
		return userWallet;
	}

	public void setUserWallet(Set<Wallet> userWallet) {
		this.userWallet = userWallet;
	}

	public Integer getUserId() {
		return userId;
	}
	
	public List<Role> getRoleType() {
		return roleType;
	}
	public void setRoleType(List<Role> roleType) {
		this.roleType = roleType;
	}
	/*public List<Role> getRole() {
		return role;
	}
	public void setRole(List<Role> role) {
		this.role = role;
	}*/
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
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}

