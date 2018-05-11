package io.oodles.springboot1.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class StoreOTP {
	
	
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
	private Integer tokenOTP;
	private String email;
	private Date date;
	
	
	

	

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	

	public StoreOTP() {
	}

	
       
	
	

	

	

	public StoreOTP(Integer tokenOTP, String email, Date date) {
		super();
		this.tokenOTP = tokenOTP;
		this.email = email;
		this.date = date;
	}

	public Integer getTokenOTP() {
		return tokenOTP;
	}

	public void setTokenOTP(Integer tokenOTP) {
		this.tokenOTP = tokenOTP;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	

	
	

	
}
