package com.training.demo.model;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "OtpVerification")
public class OtpVerification {
	@Id
	private int id;
	
	private String email;
	private Integer userId;
	private Integer otp;
	private Date date;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getOtp() {
		return otp;
	}
	
	public void setOtp(Integer otp) {
		this.otp=otp;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}	
}