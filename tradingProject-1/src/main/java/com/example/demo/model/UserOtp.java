package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="usersotp")
public class UserOtp {

    @Id
    private Integer userId;

    private Integer tokenOTP;

    @Column(name="emailid")
    private String email;

    @Column(name="phoneNumber")
    private String phoneNumber;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userid) {
        this.userId = userid;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
