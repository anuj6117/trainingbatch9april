package io.oodles.springboot1.model;

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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Pattern;

import io.oodles.springboot1.enums.Status;

@Entity
@Table(name = "user", 
uniqueConstraints = @UniqueConstraint(name = "email_user_uc"
                                      ,columnNames = {"emailid","phoneNumber"}))
public class Users {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public Integer userid;
	public String userName;
	
	public String email;
	
	@Pattern(regexp="(^$|[0-9]{10})")
	public String phoneNumber;
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
	public String country;
	public String password;
	Date date;
	@Enumerated(EnumType.STRING)
	public Status  status;
	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinTable(name="user_role",
	joinColumns= {@JoinColumn(name="user_id",referencedColumnName="userid")},
	inverseJoinColumns= {@JoinColumn(name="role_id",referencedColumnName="roleid")})
	private List<Role> roles;
	
	@OneToMany(mappedBy="users")
	private Set<Wallet> wallet;
	
	@OneToMany(mappedBy="usersorder")
	private Set<UserOrder> userOrder;
	
	
	
	
	
    
    
	public Set<UserOrder> getUserOrder() {
		return userOrder;
	}
	public void setUserOrder(Set<UserOrder> userOrder) {
		this.userOrder = userOrder;
	}
	
	public Set<Wallet> getWallet() {
		return wallet;
	}
	public void setWallet(Set<Wallet> wallet) {
		this.wallet = wallet;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Users(){}
	
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
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
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
	
	
	

}
