package com.trainingproject.domain;

//import java.util.Date;

import java.util.List;

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
//import javax.validation.constraints.Email;

//import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trainingproject.enums.UserStatus;

@Entity
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer userId;
    
	private String userName;
    
	
	private String email;
    private String country;
    
    @Enumerated(EnumType.STRING)
    private UserStatus status;
 
    private Long phoneNumber;
    private String password;
    private String createdOn;

    
    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(name="user_role",joinColumns = {@JoinColumn(name = "user_id123", referencedColumnName="userId")},
    inverseJoinColumns = { @JoinColumn(name = "role_id123", referencedColumnName="roleId") })
    private List<Role> roleType;
    
    
    @OneToMany(mappedBy="user")
    private List<Wallet> userWallet;
    //@JsonIgnore
    @OneToMany(mappedBy="user")
    private List<UserOrder> userOrder;
    

    public User() {
		
   	}
    
    public User(User user) {
    	this.userId=user.getUserId();
		this.userName=user.getUserName();
		this.password=user.getPassword();
		this.userId=user.getUserId();
		this.roleType=user.getRoleType();
		this.country=user.getCountry();
		this.phoneNumber=user.getPhoneNumber();
		this.userWallet=user.getUserWallet();
		this.userOrder=user.getUserOrder();
		this.email=user.getEmail();
		this.createdOn=user.getCreatedOn();
	}


	public List<UserOrder> getUserOrder() {
		return userOrder;
	}


	public void setUserOrder(List<UserOrder> userOrder) {
		this.userOrder = userOrder;
	}


	public String getCreatedOn() {
		return createdOn;
	}


	public UserStatus getStatus() {
		return status;
	}


	public void setStatus(UserStatus status) {
		this.status = status;
	}


	public List<Role> getRoleType() {
		return roleType;
	}


	public void setRoleType(List<Role> roleType) {
		this.roleType = roleType;
	}


	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
		

	public Integer getUserId() {
		return userId;
	}

	
	public List<Wallet> getUserWallet() {
		return userWallet;
	}


	public void setUserWallet(List<Wallet> walletType) {
		this.userWallet = walletType;
		
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}


	public String getUserName() {
		return userName;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
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


	public Long getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


}
    
    