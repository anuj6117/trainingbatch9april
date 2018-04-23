package com.trainingproject.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
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

@Entity
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer userId;
    
    //@NotNull(message="name cannot be null")
    //@Size(max=25,message="name cannot be greater than 25 length")
	private String userName;
    
   @Email
	private String email;
    private String country;
    
  // @Size(min=10,max=10,message="please enter a valid mobile numbers")
    private Long phoneNumber;
    private String password;
    private Date createdOn;
  //  private String userWallet;
    
    
    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(name="user_role",joinColumns = {@JoinColumn(name = "user_id123", referencedColumnName="userId")},
    inverseJoinColumns = { @JoinColumn(name = "role_id123", referencedColumnName="roleId") })
    private List<Role> roleType;
    
    
    @OneToMany(mappedBy="user")
    private List<Wallet> walletType;
    
    @OneToMany(mappedBy="user")
    private List<UserOrder> userOrder;
    

    public Date getCreatedOn() {
		return createdOn;
	}


	public List<Role> getRoleType() {
		return roleType;
	}


	public void setRoleType(List<Role> roleType) {
		this.roleType = roleType;
	}


	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
		

	public Integer getUserId() {
		return userId;
	}

	
	public List<Wallet> getWalletType() {
		return walletType;
	}



	public void setWalletType(List<Wallet> walletType) {
		this.walletType = walletType;
		
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
    
    