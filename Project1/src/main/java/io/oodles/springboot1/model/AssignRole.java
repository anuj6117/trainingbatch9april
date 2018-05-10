package io.oodles.springboot1.model;

public class AssignRole {
	Integer userId;
    String roletype;
	
	AssignRole(){}
	public AssignRole(Integer userId, String roletype) {
		super();
		this.userId = userId;
		this.roletype = roletype;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getRoletype() {
		return roletype;
	}

	public void setRoletype(String roletype) {
		this.roletype = roletype;
	}
	
	
	

}
