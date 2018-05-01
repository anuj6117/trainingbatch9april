package io.oodles.springboot1.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.transaction.support.ResourceHolderSupport;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.oodles.springboot1.enums.Status;

@Entity
@Table(name = "user", 
uniqueConstraints = @UniqueConstraint(name = "email_user_uc"
                                      ,columnNames = {"email","phoneNumber"}))
//@XmlRootElement(name="users")
//@XmlAccessorType(XmlAccessType.FIELD)
public class Users  {
	//private static final long serialversionUID=1L;
	@Column(name="userId")
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public Integer userId;
	@NotNull(message="UserName can't be null")
	@javax.validation.constraints.NotEmpty
	@Size(max=25,message="Maximun characters allowed for this field is 25")
	public String userName;
	@javax.validation.constraints.NotEmpty
	@NotNull
	@Email(message="Please, enter a valid email address")
	public String email;
	@Size(min=10,max=10)
	@Pattern(regexp="(^$|[0-9]{10})")
	public String phoneNumber;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName.replaceAll("\\s","");
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
	@javax.validation.constraints.NotEmpty
	@NotNull
	@javax.validation.constraints.NotEmpty
	public String country;
	@Size(min=8,max=32)
	@Pattern(regexp="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",message="\"Please, enter password \n" + 
			"with minimum 8 characters.\n" + 
			"You password should \n" + 
			"have atleast 1 Upper \n" + 
			"Case, 1 Lower Case, 1 \n" + 
			"Digit & 1 Special \n" + 
			"Character.\"")
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
	
	@JsonIgnore
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
	
	
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
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
