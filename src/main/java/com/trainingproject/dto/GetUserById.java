package com.trainingproject.dto;

import java.util.Optional;

import com.trainingproject.domain.User;

public class GetUserById {

	Optional<User> opt;
	String message;
	public Optional<User> getOpt() {
		return opt;
	}
	public void setOpt(Optional<User> opt) {
		this.opt = opt;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
