package io.oodles.springboot1.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class StoreOTP {
	
	/*@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;*/
	
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
	private Integer userotp;
	private String UserEmail;
	private Date date;

	public StoreOTP() {
	}

	public StoreOTP(Integer userotp, String userEmail, Date date) {
		super();
		this.userotp = userotp;
		UserEmail = userEmail;
		this.date = date;
	}

	public Integer getUserotp() {
		return userotp;
	}

	public void setUserotp(Integer userotp) {
		this.userotp = userotp;
	}

	public String getUserEmail() {
		return UserEmail;
	}

	public void setUserEmail(String userEmail) {
		UserEmail = userEmail;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	

	
	

	
}
