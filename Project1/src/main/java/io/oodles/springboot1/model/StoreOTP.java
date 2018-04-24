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
	private String emailid;
	private Date date;
	
	
	

	

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	

	public StoreOTP() {
	}

	

	public Integer getUserotp() {
		return userotp;
	}

	public void setUserotp(Integer userotp) {
		this.userotp = userotp;
	}

	

	public StoreOTP(Integer userotp, String emailid, Date date) {
		super();
		this.userotp = userotp;
		this.emailid = emailid;
		this.date = date;
	}

	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	

	
	

	
}
